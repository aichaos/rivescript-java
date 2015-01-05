# Java RiveScript Interpreter

## SYNOPSIS

This is a Java implementation of a RiveScript interpreter, per the Working Draft
at http://www.rivescript.com/wd/RiveScript.html

This is *BETA QUALITY SOFTWARE* and hasn't been field tested as much as some of
the other RiveScript libraries in other languages.

It is feature complete syntax-wise: it supports all of the RiveScript directives
and tags, but it doesn't natively support any object macros yet (there is
example code included for using Perl object macros though). Long term plans
include adding native JavaScript support.

If you find a way to crash this library, please tell me how you did it so I can
improve this library!

## DOCUMENTATION

See the javadocs at doc/index.html

## RSBOT DEMO SCRIPT

The `RSBot.java` is a simple implementation of a Java RiveScript bot. You
can use it to quickly chat with the Eliza-based bot in the `Aiden/` folder.

These commands may be used at your input prompt in RSBot:

    /quit        - Quit the program
    /dump topics - Dump the internal topic/trigger/reply struct (debugging)
    /dump sorted - Dump the internal trigger sort buffers (debugging)

## SHELL SCRIPTS

The shell scripts were there to aid me in development. What they do is:

* build.sh

  Deletes all the `*.class` files in the current directory and its
  subdirectories, and then uses `javac` to compile RSBot and all of the
  library files.

* javadoc.sh

  Deletes the `doc/` folder and recreates it with the `javadoc` command,
  for generating documentation for the library.

## QUICK START

Compile RSBot.java using `javac`. The preferred way is to run `build.sh` if you
have a bash shell.

    Unix:   $ ./build.sh
            $ javac RSBot.java
    Win32:  > javac RSBot.java

Then execute `RSBot` to begin chatting with the demo Eliza-based bot that
tends to ship with RiveScript libraries.

    Unix:   $ java RSBot
    Win32:  > java RSBot

## LICENSE

```
The MIT License (MIT)

Copyright (c) 2015 Noah Petherbridge

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
