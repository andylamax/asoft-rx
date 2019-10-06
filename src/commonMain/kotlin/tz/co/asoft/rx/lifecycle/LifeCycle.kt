package tz.co.asoft.rx.lifecycle

class LifeCycle : ILifeCycle {
    override var state: ILifeCycle.State = ILifeCycle.State.CREATED

    override val observers = mutableSetOf<Observer<*>>()

//    fun start() {
//        state = State.RUNNING
//        observers.forEach { it.awake() }
//    }
//
//    fun resume() {
//        state = State.RUNNING
//    }
//
//    fun pause() {
//        state = State.PAUSED
//    }
//
//    fun finish() {
//        observers.forEach { it.cancel() }
//        observers.clear()
//        state = State.FINISHED
//    }
}