$env:JAVA_HOME = "D:\IntelliJ IDEA Community Edition 2024.3.1.1\jbr"
$mavenCmd = "D:\IntelliJ IDEA Community Edition 2024.3.1.1\plugins\maven\lib\maven3\bin\mvn.cmd"

& $mavenCmd clean compile exec:java
