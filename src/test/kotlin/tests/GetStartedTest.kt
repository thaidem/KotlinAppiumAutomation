package tests

import lib.CoreTestCase
import lib.Platform
import org.junit.Test

class GetStartedTest : CoreTestCase()
{
    @Test
    fun testPassThroughWelcome()
    {
        if (Platform.getInstance().isAndroid()) {
            return
        }
        // TODO: 31.12.2020
    }
}