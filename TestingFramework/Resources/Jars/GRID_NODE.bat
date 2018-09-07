cd /d %~dp0
java -Dwebdriver.chrome.driver="D:\Users\charanjeet.singh\git\testingFramework\TestingFramework\Resources\Browsers\chromedriver.exe" -Dwebdriver.ie.driver="D:\Users\charanjeet.singh\git\testingFramework\TestingFramework\Resources\Browsers\IEDriverServer.exe" -Dwebdriver.gecko.driver="D:\Users\charanjeet.singh\git\testingFramework\TestingFramework\Resources\Browsers\geckodriver.exe" -jar selenium-server-standalone-3.13.0.jar -role node -nodeConfig GRID_Config.json
pause
