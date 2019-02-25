package com.asofttz.rx

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Observable<T : Any?>(initialValue: T) {
    var value: T by Delegates.observable(initialValue) { _, old, new ->
        dispatch(old, new)
    }

    private val observers = mutableListOf<Observer<T>>()

    private val subscribers = mutableListOf<Subscriber<T>>()

    @Deprecated("use subscribe instead")
    fun observe(onChange: (oldValue: T, newValue: T) -> Unit): Observer<T> = onChange.apply {
        observers.add(this)
        onChange(value, value)
    }

    fun subscribe(onChange: (value: T) -> Unit) = Subscriber(subscribers).apply {
        callback = onChange
        onChange(value)
        subscribers.add(this)
    }

    @Deprecated("use subscribe instead")
    fun unObserve(observer: Observer<T>) {
        try {
            observers.remove(observer)
        } catch (e: Exception) {
            throw Exception("Can not unobserve an observer that was'nt set")
        }
    }

    fun dispatch(oldValue: T = value, newValue: T = value) {
        subscribers.forEach {
            it.callback(value)
        }
        observers.forEach {
            it(oldValue, newValue)
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}