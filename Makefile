default:
	javac *java
	java -cp . Main.java
clean:
	rm *class

test:
	javac *java
	java -cp . -ea Tests.java
