mkdir target

cd target

jar xf ../lib/JCDP-2.0.1.jar
jar xf ../lib/jcommander-1.82.jar

rm -rf META-INF
cd ..

javac -cp "lib/jcommander-1.82.jar:lib/JCDP-2.0.1.jar" -d ./target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/Logic.java


mkdir target/resources
cp -r src/resources/* target/resources/

jar cfm target/images-to-chars-printer.jar manifest.txt -C target .


java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN --filepath=target/resources/it.bmp
