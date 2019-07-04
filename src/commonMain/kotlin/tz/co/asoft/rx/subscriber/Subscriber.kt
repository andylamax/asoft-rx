package tz.co.asoft.rx.subscriber

open class Subscriber<T>(private val container: MutableList<Subscriber<T>> = mutableListOf()) {
    internal var callback: ((T) -> Unit)? = null
    internal var oldNewCallback: ((T, T) -> Unit)? = null

    operator fun invoke(o: T, n: T) {
        callback?.invoke(n)
        oldNewCallback?.invoke(o, n)
    }

    fun cancel() = container.remove(this)
}