import org.junit.Test
import org.junit.Assert.*

class MainClassTest : MainClass() {

    @Test
    fun testGetLocalNumber() {

        val localNumber = super.getLocalNumber()
        val checkLocalNumber = 14

        assertTrue("$localNumber != $checkLocalNumber", localNumber == checkLocalNumber)
    }

    @Test
    fun testGetClassNumber() {

        val classNumber = super.getClassNumber()
        val checkClassNumber = 45

        assertTrue("$classNumber not more $checkClassNumber", classNumber > checkClassNumber)
    }
}