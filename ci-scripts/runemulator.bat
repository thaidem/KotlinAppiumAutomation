emulator @and80 -no-boot-anim -no-snapshot-load -no-audio -no-window -gpu off -debug  -all &
cd C:\Develop\KotlinAppiumAutomation
mvn -Ddevice="Android" -Dtest=ArticleTests#testCompareArticleTitle test