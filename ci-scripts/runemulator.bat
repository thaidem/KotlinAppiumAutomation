emulator @API29 -no-boot-anim -no-snapshot-save -no-audio -no-window -gpu off -debug  -all &
cd C:\Develop\KotlinAppiumAutomation
export PLATFORM = android
mvn -Dtest=ArticleTests#testCompareArticleTitle test