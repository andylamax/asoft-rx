package com.asofttz.rx

class Subscriber<T>(private var container: MutableList<Subscriber<T>> = mutableListOf()) {
    internal var callback: (T) -> Unit = { }

    operator fun invoke(t: T) {
        callback(t)
    }

    fun cancel() = container.remove(this)
}