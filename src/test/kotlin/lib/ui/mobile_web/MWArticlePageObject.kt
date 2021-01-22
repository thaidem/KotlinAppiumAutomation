package lib.ui.mobile_web

import lib.ui.ArticlePageObject
import org.openqa.selenium.remote.RemoteWebDriver

class MWArticlePageObject(driver: RemoteWebDriver?) : ArticlePageObject(driver) {
    init {
        TITLE = "id~org.wikipedia:id/view_page_title_text"
        FOOTER_ELEMENT = "xpath~//*[contains(@text, 'View page in browser')]"
        OPTIONS_BUTTON = "xpath~//android.widget.ImageView[@content-desc='More options']"
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath~//*[@text='Add to reading list']"
        ADD_TO_MY_LIST_OVERLAY = "id~org.wikipedia:id/onboarding_button"
        MY_LIST_NAME_INPUT = "id~org.wikipedia:id/text_input"
        MY_LIST_OK_BUTTON = "xpath~//*[@text='OK']"
        CLOSE_ARTICLE_BUTTON = "xpath~//android.widget.ImageButton[@content-desc='Navigate up']"
        USER_FOLDER_TPL = "xpath~//android.widget.TextView[@text='{FOLDER}']"
    }
}