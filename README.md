# RiveScript-Java

[![Gitter](https://badges.gitter.im/aichaos/rivescript-java.svg)](https://gitter.im/aichaos/rivescript-java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/aichaos/rivescript-java.svg?branch=master)](https://travis-ci.org/aichaos/rivescript-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.rivescript/rivescript-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.rivescript/rivescript-core)
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

## Introduction

This is a RiveScript interpreter library written for the Java programming
language. RiveScript is a scripting language for chatterbots, making it easy
to write trigger/response pairs for building up a bot's intelligence.

**This project is currently in Beta status.** The API should be mostly stable
but things might move around on you.

## About RiveScript

RiveScript is a scripting language for authoring chatbots. It has a very
simple syntax and is designed to be easy to read and fast to write.

A simple example of what RiveScript looks like:

```
+ hello bot
- Hello human.
```

This matches a user's message of "hello bot" and would reply "Hello human."
Or for a slightly more complicated example:

```
+ my name is *
* <formal> == <bot name> => <set name=<formal>>Wow, we have the same name!
* <get name> != undefined => <set name=<formal>>Did you change your name?
- <set name=<formal>>Nice to meet you, <get name>!
```

The official website for RiveScript is https://www.rivescript.com/

To test drive RiveScript in your web browser, try the
[RiveScript Playground](https://play.rivescript.com/).

## Documentation

API Documentation is available at <http://www.javadoc.io/doc/com.rivescript/rivescript-core/>

Also check out the [**RiveScript Community Wiki**](https://github.com/aichaos/rivescript/wiki)
for common design patterns and tips & tricks for RiveScript.

## Installation

Add the `rivescript-core` dependency to your project:

_Maven_:

```xml
<dependency>
  <groupId>com.rivescript</groupId>
  <artifactId>rivescript-core</artifactId>
  <version>0.8.0</version>
</dependency>
```

_Gradle_:

```groovy
dependencies {
    compile "com.rivescript:rivescript-core:0.8.0"
}
```

## Usage

When used as a library for writing your own chatbot, the synopsis is as follows:

```java
import com.rivescript.Config;
import com.rivescript.RiveScript;

// Create a new bot with the default settings.
RiveScript bot = new RiveScript();

// To enable UTF-8 mode, you'd have initialized the bot like:
RiveScript bot = new RiveScript(Config.utf8());

// Load a directory full of RiveScript documents (.rive files)
bot.loadDirectory("./replies");

// Load an individual file.
bot.LoadFile("./testsuite.rive");

// Sort the replies after loading them!
bot.sortReplies();

// Get a reply.
String reply = bot.reply("user", "Hello bot!");
```

The `rivescript-core` distribution also includes an interactive shell for testing your
RiveScript bot. Run it with the path to a folder on disk that contains your
RiveScript documents. Example:

    java com.rivescript.cmd.Shell [options] </path/to/documents>

## Configuration

The `com.rivescript.RiveScript` constructor takes an optional `Config` instance. 
Here is a full example with all the supported options. You only need to provide 
values for configuration options that are different to the defaults.

```java
RiveScript bot = new RiveScript(Config.newBuilder()
        .throwExceptions(false)          // Whether exception throwing is enabled
        .strict(true)                    // Whether strict syntax checking is enabled
        .utf8(false)                     // Whether UTF-8 mode is enabled
        .unicodePunctuation("[.,!?;:]")  // The unicode punctuation pattern
        .forceCase(false)                // Whether forcing triggers to lowercase is enabled
        .depth(50)                       // The recursion depth limit 
        .sessionManager(sessionManager)  // The session manager for user variables
        .errorMessages(errors)           // Map of custom error messages
        .build());
```

For convenience, you can use shortcuts:

```java
// The default constructor uses a basic configuration.
RiveScript bot = new RiveScript();

// This is similar as:
RiveScript bot = new RiveScript(Config.basic());

// To use the basic configuration with UTF-8 mode enabled use: 
RiveScript bot = new RiveScript(Config.utf8());
```

## UTF-8 Support

UTF-8 support in RiveScript is considered an experimental feature. It is
disabled by default.

By default (without UTF-8 mode on), triggers may only contain basic ASCII
characters (no foreign characters), and the user's message is stripped of all
characters except letters, numbers and spaces. This means that, for example,
you can't capture a user's e-mail address in a RiveScript reply, because of
the @ and . characters.

When UTF-8 mode is enabled, these restrictions are lifted. Triggers are only
limited to not contain certain metacharacters like the backslash, and the
user's message is only stripped of backslashes and HTML angled brackets
(to protect from obvious XSS if you use RiveScript in a web application).
Additionally, common punctuation characters are stripped out, with the default
set being `[.,!?;:]`. This can be overridden by providing a new regexp
string to the `Config.Builder#unicodePunctuation()` method. Example:

```java
// Make a new bot with UTF-8 mode enabled and override the punctuation
characters that get stripped from the user's message.
RiveScript bot = new RiveScript(Config.Builder
        .utf8()
        .unicodePunctuation("[.,!?;:]")
        .build());
```

The `<star>` tags in RiveScript will capture the user's "raw" input, so you can
write replies to get the user's e-mail address or store foreign characters in
their name.

## Building

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

To execute `RSBot` to begin chatting with the demo Eliza-based bot that
tends to ship with RiveScript libraries run:

    ./gradlew :rivescript-samples-rsbot:runBot --console plain

## Authors

* Noah Petherbridge, https://www.kirsle.net/
* Marcel Overdijk, https://twitter.com/marceloverdijk

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
