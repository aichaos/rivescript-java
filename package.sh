#!/bin/bash
if [ -d ./classes ]
then
    echo "Removing class files"
    rm -rf classes
fi

if [ -f rsbot.jar ]
then
    echo "Removing package"
    rm rsbot.jar
fi

echo "Creating classes directory"
mkdir classes

echo "Compiling"
javac -Xlint:deprecation -Xlint:unchecked -d classes RSBot.java

echo "Creating manifest"
echo "Main-Class: RSBot" > classes/manifest.txt
echo "" >> classes/manifest.txt

echo "Packaging"
cd classes

jar -cvmf manifest.txt ../rsbot.jar *
chmod +x ../rsbot.jar

cd -
