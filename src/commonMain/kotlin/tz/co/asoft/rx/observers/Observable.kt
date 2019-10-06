package tz.co.asoft.rx.observers

import tz.co.asoft.rx.subscriber.Subscriber
import kotlin.properties.Delegates

@Deprecated("Please, Use LiveData instead")
open class Observable<T : Any?>(initialValue: T) {
    var value: T by Delegates.observable(initialValue) { _, old, new ->
        dispatch(old, new)
    }

    private val subscribers = mutableListOf<Subscriber<T>>()

    fun subscribe(onChange: (value: T) -> Unit) = Subscriber(subscribers).apply {
        callback = onChange
        onChange(value)
        subscribers.add(this)
    }

    fun subscribe(onChange: (old: T, new: T) -> Unit) = Subscriber(subscribers).apply {
        oldNewCallback = onChange
        onChange(value, value)
        subscribers.add(this)
    }

    fun dispatch(oldValue: T = value, newValue: T = value) {
        subscribers.forEach {
            it(oldValue, newValue)
        }
    }
}