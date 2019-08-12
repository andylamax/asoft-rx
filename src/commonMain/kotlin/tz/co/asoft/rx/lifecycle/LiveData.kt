package tz.co.asoft.rx.lifecycle

import kotlin.properties.Delegates

class LiveData<T : Any?>(initialValue: T) {
    var value: T by Delegates.observable(initialValue) { _, old, new ->
        dispatch(old, new)
    }

    private val observers = mutableMapOf<LifeCycle, Observer<T>>()

    fun observe(lifeCycle: LifeCycle, onChange: (value: T) -> Unit) = Observer(lifeCycle, observers).apply {
        callback = onChange
        onChange(value)
        observers[lifeCycle] = this
    }

    fun subscribeForOldAndNew(lifeCycle: LifeCycle, onChange: (old: T, new: T) -> Unit) = Observer(lifeCycle, observers).apply {
        oldNewCallback = onChange
        onChange(value, value)
        observers[lifeCycle] = this
    }

    fun dispatch(oldValue: T = value, newValue: T = value) {
        observers.values.forEach {
            it(oldValue, newValue)
        }
    }
}