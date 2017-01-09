# Java RiveScript Interpreter

[![Gitter](https://badges.gitter.im/aichaos/rivescript-java.svg)](https://gitter.im/aichaos/rivescript-java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/aichaos/rivescript-java.svg?branch=master)](https://travis-ci.org/aichaos/rivescript-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.rivescript/rivescript-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.rivescript/rivescript-core)
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

This is a Java implementation of a RiveScript interpreter, per the Working Draft
at http://www.rivescript.com/wd/RiveScript.html

This is *BETA QUALITY SOFTWARE* and hasn't been field tested as much as some of
the other RiveScript libraries in other languages.

It is feature complete syntax-wise: it supports all of the RiveScript directives
and tags, but it doesn't natively support any dynamic language for object macros.
You can define object macros in Java at compile-time, and there's an example
that bridges Perl code; long-term plans include building in native support for
JavaScript.

This repository is a little bit light on examples; most of the examples for how
to do things are found in the source of `RSBot.java` and the RiveScript files
in the `Aiden/` directory. These demonstrate things like Java object macros,
using the Perl macro bridge, etc.

If you find a way to crash this library, please tell me how you did it so I can
improve this library!

Contributions are welcome. See `CONTRIBUTING.md` for more information.

## Documentation

Documentation is available at <https://www.rivescript.com/docs/java/>

Also check out the [**RiveScript Community Wiki**](https://github.com/aichaos/rivescript/wiki)
for common design patterns and tips & tricks for RiveScript.

## Building from Source

To compile, test, build all jars and docs run:

    ./gradlew build

To install all jars into your local Maven cache run:

    ./gradlew install

## RSBot Demo Script

The `RSBot.java` is a simple implementation of a Java RiveScript bot. You
can use it to quickly chat with the Eliza-based bot in the `Aiden/` folder.

These commands may be used at your input prompt in RSBot:

    /quit        - Quit the program
    /dump topics - Dump the internal topic/trigger/reply struct (debugging)
    /dump sorted - Dump the internal trigger sort buffers (debugging)
    /last        - Print the last trigger you matched.

## Quickstart

To execute `RSBot` to begin chatting with the demo Eliza-based bot that
tends to ship with RiveScript libraries run:

    ./gradlew :rivescript-samples-rsbot:runApp --console plain

## Unit Testing

This library is tested using [JUnit](http://junit.org/). To execute the test suite run:

    ./gradlew clean test

## License

```
The MIT License (MIT)

Copyright (c) 2016 the original author or authors.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## AUTHOR

Noah Petherbridge, https://www.kirsle.net/
