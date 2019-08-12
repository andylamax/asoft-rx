package tz.co.asoft.rx.lifecycle

class Observer<T>(private val lifeCycle: LifeCycle, private val container: MutableMap<LifeCycle, Observer<T>>) {

    internal var liveData: LiveData<T>? = null

    internal var callback: ((T) -> Unit)? = null
    internal var oldNewCallback: ((T, T) -> Unit)? = null

    internal operator fun invoke(o: T, n: T) {
        when (lifeCycle.state) {
            LifeCycle.State.RUNNING -> {
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

    internal fun awake() {
        liveData?.value?.let { invoke(it, it) }
    }

    fun cancel() = container.remove(lifeCycle)
}