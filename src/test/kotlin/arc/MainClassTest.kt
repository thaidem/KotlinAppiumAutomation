package arc

import org.junit.Assert.assertTrue
import org.junit.Test

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

    @Test
    fun  testGetClassString() {

        val classString = super.getClassString()
        val checkClassString1 = "hello"
        val checkClassString2 = "Hello"

        assertTrue("$classString not include $checkClassString1 and $checkClassString2",
            classString.contains(checkClassString1) || classString.contains(checkClassString2))
    }
}