package tz.co.asoft.rx.eventbus

open class Handler<T : Event>(private val pool : MutableSet<Handler<*>> = mutableSetOf()) {
    var callback: (T) -> Unit = {}

    operator fun invoke(event: T) = callback(event)

    fun cancel() {
        pool.remove(this)
    }
}