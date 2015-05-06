#!/bin/bash
if [ -d ./classes ]
then
    echo "Removing class files"
    rm -rf classes
fi

echo "Creating classes directory"
mkdir classes

echo "Compiling"
javac -Xlint:deprecation -Xlint:unchecked -d classes RSBot.java
