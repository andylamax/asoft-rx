package tz.co.asoft.rx.lifecycle

class LifeCycle : ILifeCycle {
    override var lifeState: ILifeCycle.LifeState = ILifeCycle.LifeState.CREATED
    override val observers = mutableSetOf<Observer<*>>()
}