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

    fun dispatch(oldValue: T = value, newValue: T = value) {
        observers.forEach { (lifeCycle, observer) ->
            if (lifeCycle.state == LifeCycle.State.RUNNING) {
                observer(oldValue, newValue)
            }
        }
    }
}