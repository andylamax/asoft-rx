package com.asofttz.rx

open class Subscriber<T>(private val container: MutableList<Subscriber<T>> = mutableListOf()) {
    internal var callback: (T) -> Unit = { }

    operator fun invoke(t: T) {
        callback(t)
    }

    fun cancel() = container.remove(this)
}