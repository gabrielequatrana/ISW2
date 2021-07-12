PATH_JACOCO_CLI_JAR="./lib"
PATH_JCS_SRC="./lib/jcs-1.3"
PATH_JCS_JAR="./lib"

mkdir -p target/jacoco-gen/jcs-coverage/
java -jar ${PATH_JACOCO_CLI_JAR}/jacococli.jar report target/jacoco.exec --classfiles ${PATH_JCS_JAR}/jcs-1.3.jar --sourcefiles ${PATH_JCS_SRC} --html target/jacoco-gen/jcs-coverage/ --xml target/jacoco-gen/jcs-coverage/file.xml --csv target/jacoco-gen/jcs-coverage/file.csv
