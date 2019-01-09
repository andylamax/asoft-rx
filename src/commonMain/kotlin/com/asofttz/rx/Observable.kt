package com.asofttz.rx

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Observable<T : Any?>(initialValue: T) {

    var value: T by Delegates.observable(initialValue) { _, old, new ->
        observers.forEach {
            it(old, new)
        }
    }

    private val observers = mutableListOf<(T, T) -> Unit>()

    fun observe(onChange: (oldValue: T, newValue: T) -> Unit) = onChange.apply {
        observers.add(this)
    }

    fun unObserve(observer: (T, T) -> Unit) {
        try {
            observers.remove(observer)
        } catch (e: Exception) {
            throw IndexOutOfBoundsException("Can not unobserve an observer that was'nt ser")
        }
    }

    fun getValue(thisRef: Any?, property: KProperty<*>): T = value
    fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}