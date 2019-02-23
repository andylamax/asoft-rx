package com.asofttz.rx

class Subscriber<T>(private var container: MutableList<Subscriber<T>> = mutableListOf()) {
    internal var callback: (MutableList<T>) -> Unit = { }

    operator fun invoke(t: MutableList<T>) {
        callback(t)
    }

    fun cancel() = container.remove(this)
}