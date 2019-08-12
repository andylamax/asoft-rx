package tz.co.asoft.rx.lifecycle

class LifeCycle {
    var state: State = State.CREATED
        get
        private set

    internal val observers = mutableSetOf<Observer<*>>()

    enum class State { CREATED, RUNNING, STOPPED, FINISHED }

    fun start() {
        state = State.RUNNING
        observers.forEach { it.awake() }
    }

    fun stop() {
        state = State.STOPPED
    }

    fun finish() {
        observers.forEach { it.cancel() }
        state = State.FINISHED
    }
}