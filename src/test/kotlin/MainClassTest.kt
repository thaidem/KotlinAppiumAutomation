import org.junit.Test
import org.junit.Assert.*

class MainClassTest : MainClass() {

    @Test
    fun testGetLocalNumber() {

        val checkLocalNumber = 14
        val localNumber = super.getLocalNumber()

        assertTrue("$localNumber != $checkLocalNumber", super.getLocalNumber() == checkLocalNumber)
    }
}