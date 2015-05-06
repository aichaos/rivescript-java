#!/bin/bash
echo "Removing class files"
rm `find . -name '*.class'`
echo "Compiling"
javac -Xlint:deprecation -Xlint:unchecked RSBot.java
