package tz.co.asoft.rx.lifecycle

import kotlin.properties.Delegates

class LiveData<T : Any?>(initialValue: T) {
    var value: T by Delegates.observable(initialValue) { _, old, new ->
        dispatch(old, new)
    }

    private val observers = mutableMapOf<LifeCycle, Observer<T>>()

    fun observe(lifeCycle: LifeCycle, onChange: (value: T) -> Unit) = Observer(lifeCycle, observers).apply {
        if (lifeCycle.state != LifeCycle.State.FINISHED) {
            callback = onChange
            if (lifeCycle.state == LifeCycle.State.RUNNING) {
                onChange(value)
            }
            liveData = this@LiveData
            observers[lifeCycle] = this
            lifeCycle.observers.add(this)
        }
    }

    fun observeForever(onChange: (value: T) -> Unit): Observer<T> {
        val lc = LifeCycle().apply {
            start()
        }
        return observe(lc, onChange)
    }

    fun dispatch(oldValue: T = value, newValue: T = value) {
        observers.forEach { (lifeCycle, observer) ->
            if (lifeCycle.state == LifeCycle.State.RUNNING) {
                observer(oldValue, newValue)
            }
        }
    }

    fun <S : Any?> map(onChange: (value: T) -> S): LiveData<S> = LiveData(onChange(value)).also { ld ->
        observeForever { ld.value = onChange(it) }
    }

    fun <S : Any?> addSource(src: LiveData<S>, transform: (S) -> T) = src.observeForever {
        value = transform(it)
    }

    companion object {
        fun <T : Any?> observing(liveData: LiveData<T>) = LiveData(liveData.value).apply {
            liveData.observeForever { value = it }
        }
    }
}