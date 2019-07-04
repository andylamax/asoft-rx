package tz.co.asoft.rx.eventbus

import kotlin.test.Test

class EventBusTest {

    class Test1Event : Event {
        val name = "Test 1 event"
    }

    class Test2Event : Event {
        val person = "Test 2 Event"
    }

    @Test
    fun testApi() {
        val bus = EventBus()

        bus.post(Test2Event())
        bus.onEvent { e: Test2Event ->
            println(e.person)
        }
        bus.post(Test1Event())
        bus.onEvent { e: Test1Event ->
            println(e.name)
        }
    }
}