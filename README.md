# RiveScript-Java

[![Gitter](https://badges.gitter.im/aichaos/rivescript-java.svg)](https://gitter.im/aichaos/rivescript-java?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Build Status](https://travis-ci.org/aichaos/rivescript-java.svg?branch=master)](https://travis-ci.org/aichaos/rivescript-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.rivescript/rivescript-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.rivescript/rivescript-core)
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

## Introduction

This is a RiveScript interpreter library written for the Java programming
language. RiveScript is a scripting language for chatterbots, making it easy
to write trigger/response pairs for building up a bot's intelligence.

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

* API Documentation: <http://www.javadoc.io/doc/com.rivescript/rivescript-core/>
* Working Draft: <http://www.rivescript.com/wd/RiveScript>

Also check out the [**RiveScript Community Wiki**](https://github.com/aichaos/rivescript/wiki)
for common design patterns and tips & tricks for RiveScript.

## Installation

Add the `rivescript-core` dependency to your project:

_Maven_:

```xml
<dependency>
  <groupId>com.rivescript</groupId>
  <artifactId>rivescript-core</artifactId>
  <version>0.10.0</version>
</dependency>
```

_Gradle_:

```groovy
dependencies {
    compile "com.rivescript:rivescript-core:0.10.0"
}
```

If you want to use RiveScript in a Spring Boot application see the 
Spring Boot Starter [section](#spring-boot-starter).

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
        .concat(ConcatMode.NONE)         // The concat mode
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

## Spring Boot Starter

Add the `rivescript-spring-boot-starter` dependency to your project:

_Maven_:

```xml
<dependency>
  <groupId>com.rivescript</groupId>
  <artifactId>rivescript-spring-boot-starter</artifactId>
  <version>0.10.0</version>
</dependency>
```

_Gradle_:

```groovy
dependencies {
    compile "com.rivescript:rivescript-spring-boot-starter:0.10.0"
}
```

The starter will automatically add the `rivescript-core` dependency to your 
project and trigger the auto configuration to create the `RiveScript` bot 
instance.

Although the auto configuration will use sensible defaults to create the bot 
instance, the following properties can be specified inside your 
`application.properties`/`application.yml` file (or as command line switches)
to customize the auto configuration behaviour:

```txt
rivescript:
  enabled: true # Enable RiveScript for the application.
  source-path: classpath:/rivescript/ # The comma-separated list of RiveScript source files and/or directories.
  file-extensions: .rive, .rs # The comma-separated list of RiveScript file extensions to load.
  throw-exceptions: false # Enable throw exceptions.
  strict: true # Enable strict syntax checking.
  utf8: false # Enable UTF-8 mode.
  unicode-punctuation: [.,!?;:] # The unicode punctuation pattern (only used when UTF-8 mode is enabled).
  force-case: false # Enable forcing triggers to lowercase.
  concat: none # The concat mode (none|newline|space).
  depth: 50 # The recursion depth limit.
  error-messages: # The custom error message overrides. For instance `rivescript.error-messages.deepRecursion=Custom Deep Recursion Detected Message`
  object-handlers: # The comma-separated list of object handler names to register (currently supported: `groovy`, `javascript`, `ruby`).
```

To automatically register custom Java subroutines and/or non-default supported
object handlers in the created `RiveScript` bot instance, define appropriate 
beans in your application context like:

```java
@Bean
public Map<String, Subroutine> subroutines() {
    // The key is the name of the Java object macro to register.
    Map<String, Subroutine> subroutines = new HashMap<>();
    subroutines.put("subroutine1", new Subroutine1());
    subroutines.put("subroutine2", new Subroutine2());
    return subroutines;
}

@Bean
public Map<String, ObjectHandler> objectHandlers() {
    // The key is the name of the programming language to register.
    Map<String, ObjectHandler> objectHandlers = new HashMap<>();
    objectHandlers.put("handler1", new ObjectHandler1());
    objectHandlers.put("handler2", new ObjectHandler2());
    return objectHandlers;
}
```

## Building

To compile, test, build all jars and docs run:

    ./gradlew build

To install all jars into your local Maven cache run:

    ./gradlew install

## Samples

The `/samples` folder contains various samples of Java RiveScript bot implementations.

* `rsbot` - The `RSBot.java` is a simple implementation using the `com.rivescript.cmd.Shell`.

  These commands may be used at your input prompt in RSBot:

      /quit        - Quit the program
      /dump topics - Dump the internal topic/trigger/reply struct (debugging)
      /dump sorted - Dump the internal trigger sort buffers (debugging)
      /last        - Print the last trigger you matched.

  To execute `RSBot` to begin chatting with the demo Eliza-based run:

      ./gradlew :rivescript-samples-rsbot:runBot --console plain

* `spring-boot-starter-rsbot` - This example uses the RiveScript Spring Boot Starter to 
  auto-configure the `RiveScript` bot instance. 
  
  To begin chatting with the demo bot run:
  
      ./gradlew :rivescript-samples-spring-boot-starter-rsbot:bootRun --console plain

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
