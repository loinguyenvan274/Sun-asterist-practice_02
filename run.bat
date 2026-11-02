@echo off
set "JAVA_HOME=D:\IntelliJ IDEA Community Edition 2024.3.1.1\jbr"
set "MAVEN_CMD=D:\IntelliJ IDEA Community Edition 2024.3.1.1\plugins\maven\lib\maven3\bin\mvn.cmd"

call "%MAVEN_CMD%" clean compile exec:java

pause

