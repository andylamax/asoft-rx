package tz.co.asoft.rx.eventbus

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData
import tz.co.asoft.rx.observers.Observable
import tz.co.asoft.test.asyncTest
import kotlin.test.Test

class LiveDataTest {
    val lifeCycle = LifeCycle()
    @Test
    fun testLiveObserver() = asyncTest {
        val livedata = LiveData("One")
        livedata.value = "one again"
        lifeCycle.start()
        delay(1000)
        livedata.observe(lifeCycle) {
            println("It Changed : $it")
        }
        delay(1000)
        lifeCycle.finish()
        livedata.value = "New Value [After start]"
        delay(1000)
        lifeCycle.stop()
        livedata.value = "Another value after stop"
        delay(2000)
    }

    @Test
    fun normalObserver() = asyncTest {
        val observer = Observable<String>("Original")
        delay(1000)
        observer.subscribe { v ->
            println("Value changed to: $v")
        }
        observer.value = "New One"
        delay(1000)
        observer.value = "Eminem"
        delay(1000)
        GlobalScope.launch(Dispatchers.Unconfined) {
            observer.value = "Another context, Wengine"
        }
        delay(1000)
    }
}