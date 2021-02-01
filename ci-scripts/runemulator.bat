call start c:\npm\appium &
start C:\Users\dmitriy.k\AppData\Local\Android\Sdk/emulator/emulator.exe -avd and80 -no-window
SET PLATFORM=mobile_web
cd C:\Develop\KotlinAppiumAutomation
mvn -Dtest=ArticleTests test
