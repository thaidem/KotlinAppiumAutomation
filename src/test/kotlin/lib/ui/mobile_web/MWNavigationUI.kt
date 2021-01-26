package lib.ui.mobile_web

import lib.ui.NavigationUI
import org.openqa.selenium.remote.RemoteWebDriver

class MWNavigationUI(driver: RemoteWebDriver?) : NavigationUI(driver) {
    init {
        MY_LISTS_LINK = "xpath~//a[@data-event-name='menu.unStar']/span[contains(text(),'Watchlist')]"
        OPEN_NAVIGATION = "css~#mw-mf-main-menu-button"
    }
}