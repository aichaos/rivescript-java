#!/bin/bash
./build.sh

if [ -f rsbot.jar ]
then
    echo "Removing package"
    rm rsbot.jar
fi

echo "Creating manifest"
echo "Main-Class: RSBot" > classes/manifest.txt
echo "" >> classes/manifest.txt

echo "Packaging"
cd classes

jar -cvmf manifest.txt ../rsbot.jar *
chmod +x ../rsbot.jar

cd -
