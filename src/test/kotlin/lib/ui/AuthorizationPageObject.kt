package lib.ui

import org.openqa.selenium.Keys
import org.openqa.selenium.remote.RemoteWebDriver

class AuthorizationPageObject(driver: RemoteWebDriver?) : MainPageObject(driver) {
    protected companion object
    {
        var LOGIN_BUTTON = "xpath~//a[contains(@class,'mw-ui-progressive') and contains(text(),'Log in')]"
        var LOGIN_INPUT = "xpath~//div//input[@id='wpName1']"
        var PASSWORD_INPUT = "css~input[name='wpPassword']"
        var SUBMIT_BUTTON = "css~button#wpLoginAttempt"
    }

    fun clickButton() {
        Thread.sleep(2000)
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", 10)
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button", 10)
    }

    fun enterLoginData(login: String, password: String) {
        Thread.sleep(2000)
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input", 10)
        this.waitForElementAndTAB(LOGIN_INPUT, "Cannot do TAB", 10)
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find and put a password to the password input", 10)
    }

    fun submitForm() {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button", 5)

    }
}