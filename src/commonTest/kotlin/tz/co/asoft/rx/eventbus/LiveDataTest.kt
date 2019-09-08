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
import kotlin.test.assertEquals

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

    @Test
    fun chaining_livedata() = asyncTest {
        val liveData1 = LiveData("Andy")

        val liveData2 = LiveData.observing(liveData1)

        liveData1.observeForever {
            println("Observing in live data 1: $it")
        }

        liveData2.observeForever {
            println("Observing in live data 2: $it")
        }

        delay(1000)

        liveData1.value = "Lamax"
        delay(2000)
    }

    @Test
    fun different_sources() = asyncTest {
        data class User(val name: String, val age: Int)

        val user1 = User("Andy", 20)
        val user2 = User("Lamax", 30)
        val users = listOf(user1, user2)

        val src1 = LiveData("Andy")
        val src2 = LiveData(20)

        val ldUnderTest = LiveData<User?>(null)
        ldUnderTest.observeForever { println(it) }
        delay(500)
        assertEquals(ldUnderTest.value, null)
        ldUnderTest.addSource(src1) { value -> users.first { it.name == value } }
        delay(500)
        assertEquals(ldUnderTest.value, user1)
        ldUnderTest.addSource(src2) { value -> users.first { it.age == value } }
        delay(500)
        assertEquals(ldUnderTest.value, user1)

        src1.value = "Lamax"
        delay(500)
        assertEquals(ldUnderTest.value, user2)

        src2.value = 20
        delay(500)
        assertEquals(ldUnderTest.value, user1)
    }
}