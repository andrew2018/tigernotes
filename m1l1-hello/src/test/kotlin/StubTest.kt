import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class StubTest {

    @Test
    fun sum() {
        assertEquals("Hello World", common("Hello", "World"))
        assertNotEquals("HelloWorld", common("Hello", "World"))
    }
}