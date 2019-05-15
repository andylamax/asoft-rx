package com.asofttz.rx

import kotlin.reflect.KProperty

open class ObservableList<T : Any?>(vararg initialValues: T) {
    var value = initialValues.asList().toMutableList()
        set(value) {
            dispatch(field, value)
            field = value
        }

    private val observers = mutableListOf<ListObserver<T>>()

    private val subscribers = mutableListOf<Subscriber<MutableList<T>>>()

    @Deprecated("use subscribe instead")
    fun observe(onChange: (oldValue: MutableList<T>, newValue: MutableList<T>) -> Unit): ListObserver<T> = onChange.apply {
        observers.add(this)
        onChange(value, value)
    }

    fun subscribe(onChange: (MutableList<T>) -> Unit) = Subscriber(subscribers).apply {
        callback = onChange
        onChange(value)
        subscribers.add(this)
    }

    @Deprecated("use subscribe instead")
    fun observe(onChange: (value: MutableList<T>) -> Unit): ListObserver<T> {
        val lobserver = { oldValue: MutableList<T>, newValue: MutableList<T> ->
            onChange(newValue)
        }
        onChange(value)
        observers.add(lobserver)
        return lobserver
    }

    @Deprecated("use subscriber.cancel() instead")
    fun unObserve(observer: ListObserver<T>) {
        try {
            observers.remove(observer)
        } catch (e: Exception) {
            throw Exception("Can not unobserve an observer that was'nt set")
        }
    }

    fun dispatch(oldValue: MutableList<T> = value, newValue: MutableList<T> = value) {
        subscribers.forEach {
            it(value)
        }
        observers.forEach {
            it(oldValue, newValue)
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): MutableList<T> = value

    operator fun get(index: Int) = value[index]

    val size: Int get() = value.size

    fun add(element: T) = value.add(element).also { dispatch() }

    fun remove(element: T) = value.remove(element).also { dispatch() }

    fun addAll(elements: Collection<T>) = value.addAll(elements).also { dispatch() }

    fun addAll(index: Int, elements: Collection<T>) = value.addAll(index, elements).also { dispatch() }

    fun removeAll(elements: Collection<T>) = value.removeAll(elements).also { dispatch() }

    fun retainAll(elements: Collection<T>) = value.retainAll(elements).also { dispatch() }

    fun clear() = value.clear().also { dispatch() }

    operator fun set(index: Int, element: T) = value.set(index, element).also { dispatch() }

    fun add(index: Int, element: T) = value.add(index, element).also { dispatch() }

    fun removeAt(index: Int) = value.removeAt(index).also { dispatch() }
}