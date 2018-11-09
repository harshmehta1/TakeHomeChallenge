# TakeHomeChallenge

Prerequisites:
1. IDE that has Spring Boot and Tomcat support.
2. Tomcat server is already set up in IDE

--

How to run:

1. Download project source code
2. Open project in IDE (IntelliJ, Sts and Eclipse preferred)
3. To import all maven dependencies, right click on pom.xml file and select import/download sources
4. Once all dependencies are imported, run the TakeHomeChallengeApplication.java file under com.validity.TakeHomeChallenge package. 
5. After running, open a web browser and go to http://localhost:8080

By default, the webpage will show results for "normal.csv". However, the application processes "advanced.csv" accurately as well. To find duplicates in "advanced.csv", please change the fileToRead field from "normal.csv" to "advanced.csv" in CsvReaderService.