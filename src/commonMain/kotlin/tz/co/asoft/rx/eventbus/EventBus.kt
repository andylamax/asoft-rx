package tz.co.asoft.rx.eventbus

import kotlin.reflect.KClass

open class EventBus {
    @PublishedApi
    internal val eventHandlers = mutableMapOf<KClass<*>, MutableSet<Handler<*>>>()

    inline fun <reified T : Event> onEvent(noinline action: (T) -> Unit): Handler<T> {
        val handlers = eventHandlers.getOrPut(T::class) { mutableSetOf() }
        val handler = Handler<T>(handlers)
        handler.callback = action
        handlers.add(handler)
        return handler
    }

    inline fun <reified T : Event> post(event: T) {
        eventHandlers[T::class]?.forEach {
            val handler = it as Handler<T>
            handler(event)
        }
    }
}