package lib.ui.android

import lib.ui.MyListsPageObject
import org.openqa.selenium.remote.RemoteWebDriver

open class AndroidMyListsPageObject(driver: RemoteWebDriver?) : MyListsPageObject(driver)
{
    init
    {
        FOLDER_BY_NAME_TPL = "xpath~//*[@text='{FOLDER_NAME}']"
        ARTICLE_BY_TITLE_TPL = "xpath~//*[@text='{TITLE}']"
    }
}