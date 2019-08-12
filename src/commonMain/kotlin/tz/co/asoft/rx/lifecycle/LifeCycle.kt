package tz.co.asoft.rx.lifecycle

interface LifeCycle {
    var state: State

    enum class State {
        STARTED, ACTIVE, INACTIVE, FINISHED
    }
}