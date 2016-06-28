# Java RiveScript Interpreter

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

## RSBot Demo Script

The `RSBot.java` is a simple implementation of a Java RiveScript bot. You
can use it to quickly chat with the Eliza-based bot in the `Aiden/` folder.

These commands may be used at your input prompt in RSBot:

    /quit        - Quit the program
    /dump topics - Dump the internal topic/trigger/reply struct (debugging)
    /dump sorted - Dump the internal trigger sort buffers (debugging)
    /last        - Print the last trigger you matched.

## Makefile

I use a GNU Makefile to simplify builds and other commands (feel free to
send a pull request to switch to Ant or something). Useful commands:

* `make build`: compiles RSBot.java and, therefore, RiveScript.
* `make run`: compiles RSBot.java and immediately runs it.
* `make test`: run unit tests with JUnit.
* `make clean`: cleans up `.class` files.
* `make javadoc`: deletes the `doc/` folder and recreates it with the `javadoc`
  command, for generating library documentation.

## QUICK START

Compile RSBot.java using `make build`. If you're running on Windows and don't
have a Make program installed, you can compile the bot with the `javac` command
directly:

    Unix:   $ make build
    Win32:  > javac RSBot.java

Then execute `RSBot` to begin chatting with the demo Eliza-based bot that
tends to ship with RiveScript libraries.

    Unix:   $ java RSBot
    Win32:  > java RSBot

## Unit Testing

This library is tested using [JUnit](http://junit.org/). To run the test suite,
make sure that JUnit is available on your `$CLASSPATH`, for example by adding
the jar file:

```bash
export CLASSPATH="$CLASSPATH:/path/to/junit-4.10.jar"
```

And then type `make test` to run the unit tests.

## License

```
The MIT License (MIT)

Copyright (c) 2016 Noah Petherbridge

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
