mkdir target

javac -d target src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/Logic.java

mkdir -p target/resources

cp src/resources/* target/resources/

cd target

jar cvfe images-to-chars-printer.jar ImagesToChar.src.java.edu.school21.printer.app.Program ImagesToChar/src/java/edu/school21/printer

java -jar images-to-chars-printer.jar . 0 resources/it.bmp