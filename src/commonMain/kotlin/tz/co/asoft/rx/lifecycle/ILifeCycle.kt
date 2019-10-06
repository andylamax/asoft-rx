package tz.co.asoft.rx.lifecycle

interface ILifeCycle {
    var state: State

    val observers: MutableSet<Observer<*>>

    enum class State { CREATED, RUNNING, PAUSED, FINISHED }

    fun start() {
        state = State.RUNNING
        observers.forEach { it.awake() }
    }

    fun resume() {
        state = State.RUNNING
    }

    fun pause() {
        state = State.PAUSED
    }

    fun finish() {
        observers.forEach { it.cancel() }
        observers.clear()
        state = State.FINISHED
    }
}