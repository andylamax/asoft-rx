package tz.co.asoft.rx.lifecycle

class Observer<T>(private val lifeCycle: ILifeCycle, private val container: MutableMap<ILifeCycle, Observer<T>>) {

    internal var liveData: LiveData<T>? = null

    internal var callback: ((T) -> Unit)? = null
    internal var oldNewCallback: ((T, T) -> Unit)? = null

    internal operator fun invoke(o: T, n: T) {
        when (lifeCycle.lifeState) {
            ILifeCycle.LifeState.RUNNING -> {
                callback?.invoke(n)
                oldNewCallback?.invoke(o, n)
            }
            ILifeCycle.LifeState.FINISHED -> {
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