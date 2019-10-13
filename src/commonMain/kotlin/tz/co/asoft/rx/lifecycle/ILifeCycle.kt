package tz.co.asoft.rx.lifecycle

interface ILifeCycle {
    var lifeState: LifeState

    val observers: MutableSet<Observer<*>>

    enum class LifeState { CREATED, RUNNING, PAUSED, FINISHED }
    
    fun start() {
        lifeState = LifeState.RUNNING
        observers.forEach { it.awake() }
    }

    fun resume() {
        lifeState = LifeState.RUNNING
    }

    fun pause() {
        lifeState = LifeState.PAUSED
    }

    fun finish() {
        observers.forEach { it.cancel() }
        observers.clear()
        lifeState = LifeState.FINISHED
    }
}