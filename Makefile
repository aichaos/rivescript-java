all: build

# `make build` to build RSBot
build: clean
	javac RSBot.java

# `make test` to run unit tests
test: clean
	cd tests; \
	CLASSPATH="${CLASSPATH}:.." javac *.java; \
	CLASSPATH="${CLASSPATH}:.." java RunTests

# `make javadoc` to build documentation
javadoc:
	javadoc -d doc -sourcepath . -classpath . com.rivescript com.rivescript.lang

# `make clean` to clean up class files
clean:
	find . -name '*.class' -delete
