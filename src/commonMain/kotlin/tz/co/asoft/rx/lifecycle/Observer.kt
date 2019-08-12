package tz.co.asoft.rx.lifecycle

class Observer<T>(private val lifeCycle: LifeCycle, private val container: MutableMap<LifeCycle, Observer<T>>) {

    internal var callback: ((T) -> Unit)? = null
    internal var oldNewCallback: ((T, T) -> Unit)? = null

    operator fun invoke(o: T, n: T) {
        when (lifeCycle.state) {
            LifeCycle.State.ACTIVE -> {
                callback?.invoke(n)
                oldNewCallback?.invoke(o, n)
            }
            LifeCycle.State.FINISHED -> {
                cancel()
            }
            else -> {
            }
        }
    }

    fun cancel() = container.remove(lifeCycle)
}