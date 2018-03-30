# Change History

This documents the history of significant changes to `rivescript-java`.

## v0.11.0 - TBD, 2018

This update focuses on Python macro support and bug fixes.

* **Changes:**
  * Fix for data loss occuring when variable contains `=` (#55).
  * Upgraded build environment to latest Gradle `4.6` version.

## v0.10.0 - August 30, 2017

This update focuses on new features and improvements.

* **Changes:**
  * Huge performance improvement in `StringUtils.quoteMetacharacters(str)` (#52).
  * Add method `getVariables()` to get all variables (#51).
  * Add method `getTopics()` to return all topics (#47).
  * Add ability to set a default (brain-wide) `concat` flag (#46).

## v0.9.2 - March 19, 2017

This update focuses on bug fixes.

* **Changes:**
  * Fix for not able to get JavaScript `ScriptEngine` in Google App Engine 
    local devserver using `new ScriptEngineManager().getEngineByName(engineName)`.
    Fallback mechanism added to use  
    `new ScriptEngineManager(null).getEngineByName(engineName)` (#44).

## v0.9.1 - March 7, 2017

This update focuses on bug fixes.

* **API Breaking Changes:**
  * Remove `java.nio.file.Path` reference and subsequently also the 
    `RiveScript#loadDirectory(Path path, String... extensions)` and
    `RiveScript#loadFile(Path path)` methods as they caused issues
    on Adroid. (#43).

* **Changes:**
  * Fix for object caller regexp processing internal `{__call__}`
    tags. The closing curly brackets were not escaped (#43).

## v0.9.0 - March 3, 2017

This update focuses on new features and bug fixes.

* **API Breaking Changes:**
  * The `load` and `call` methods of the `ObjectHandler` interface now 
    require the `RiveScript` instance as an additional (first) argument.

* **Changes:**
  * Add RiveScript Spring Boot Starter module (#34).
    A dedicated RiveScript Spring Boot Starter sample is available under
    `/samples/spring-bootstarter-rsbot` for reference.
  * Add JSR-223 Scripting `ObjectHandler` to support object macros written in 
    any JSR-223 compliant scripting language. The `rivescript-core` distro
    comes bundled with out-of-the-box support for JavaScript, Groovy and Ruby
    object macros using the general `Jsr223ScriptingHandler`.
    This `Jsr223ScriptingHandler` can also be used to easily implement other
    programming languages which are supported by the JSR-223 Scripting API.
    Developers should manually add the appropriate Groovy or Ruby dependencies
    to their projects if they want to use the `GroovyHandler` or `RubyHandler`.
    For the `JavaScriptHandler` no external dependency is needed. (#32, #36)
  * Fix for `removeHandler()` causing a `ConcurrentModificationException`.
  * Fix for making wildcards in optionals not matchable (#42).
  * Add static constants for custom error message keys.
  * Add static constant for `"undefined"` string (#41).
  * Reorganize tests in proper separation of unit and integration tests (#38).

## v0.8.1 - February 20, 2017

This update focuses on bug fixes.

* **Changes:**
  * Fix for `String#split()` returning trailing empty string on Google AppEngine 
    (https://code.google.com/p/googleappengine/issues/detail?id=13565).
  * Fix for `NullPointerException` being thrown in case `Subroutine` or
    `ObjectHandler` returned `null` (#40).
  * Fix for object macros to support quoted strings come in as one arg
    regardless of spaces (#40). Typical use case:
    * `<call>object_name "a b"</call>` will call the macro with a single arg
      `["a b"]`.
    * `<call>object_name "a b" c d</call>` will call the macro with 3 args
      `["a b", "c", "d"]`.
    * `<call>object_name "<get name>"</call>` will call the macro with a single
      arg containing the value from the user var.
      Note `<call>object_name <get name></call>` would call the macro with 
      multiple args in case the user var value would contain spaces.

## v0.8.0 - February 14, 2017

This update focuses on new features and huge code refactoring / reorganization.
The `rivescript-java` implementations has been aligned with the `rivescript-go`
implementation.

* **API Breaking Changes:**
  * Removed `RiveScript(boolean debug)` constructor in favour of 
    `RiveScript(Config config)` constructor .
  * The `RiveScript` instance has no notion of `debug` mode anymore.
    Debug logging is now based on the SLF4J configuration.
  * Refactored `com.rivescript.ObjectMacro` to `com.rivescript.macro.Subroutine`.
  * Renamed `ObjectHandler` methods from `onLoad`, `onCall` to respectively 
    `load` and `call`. Also the `setClass` method has been removed.
  * The `org.json` dependency is now a optional dependency.
    Developers using Perl object macros must include this `org.json` dependency 
    manually to their projects.

* **Changes:**
  * Replace `System.out` logging by using the SLF4J API.
  * Add Unicode support (#30, #31).
  * Add new forceCase config option, which will force-lowercase your triggers
    during parse time, enabling authors to use uppercase letters in triggers 
    without it being a syntax error. Do note however that Unicode case folding 
    can become an issue with certain symbols.
  * Add customizable error messages config option.
  * Add throw exceptions config option, which will make the bot throw (runtime)
    exceptions (e.g. `DeepRecursionException`, `ReplyNotFoundException`)  
    in case of an error instead of just replying with an error message. 
    When enabled, developers should catch these exceptions and take the 
    appropriate actions.
  * The `RiveScript` constructor now accepts a `Config` object to configure
    the RiveScript instance. Also a developer `Config.Builder` is available.  
  * Add support for pluggable session stores for user variables. The default
    one still keeps user variables in memory, but you can specify your own
    implementation instead (#33).
  * Add RiveScript shell to quickly demo and test a RiveScript bot.
    See `com.rivescript.cmd.Shell`.
  * Separated the `Parser` which can now be used independently.
    The parser returns a Abstract Syntax Tree (AST) identical to the 
    `rivescript-go` parser. It enables third party developers to write 
    applications that simply parse RiveScript code and getting an AST from it.
  * Add option to load RiveScript source code from a `java.io.File` or 
    `java.nio.file.Path`.
  * Fix for sorting %Previous triggers (#9).

## v0.7.2 - January 17, 2017

* **Changes:**
  * Fix to make the `RiveScript` thread safe using a a `ThreadLocal` to store
    the current user.  (#18).
  * Add support for escaping `_` (underscores) in triggers.

## v0.7.1 - January 9, 2017

* **Changes:**
  * Fix for arrays in replies (e.g. `(@greek)`) not being converted to
    randomized sets (bug #26).
  * Fix trigger regexp processing so that if a `{weight}` tag contains a
    space before or after it (or: a space between `{weight}` and the rest
    of the trigger text), the spaces are also stripped so that matching
    isn't broken for that trigger (bug #29).
  * Fix to support embedded tags in replies (bug #28).
  * Fix for list of stars which were growing incorrectly by multiple
    calls to `processTags()` (#19).
  * Fix to avoid adding `null` stars while harvesting the stars (#20).
  * Fix for moving the no-{inherits} triggers to the bottom of the stack
    when sorting the triggers (#8).

## v0.7.0 - December 22, 2016

* **Changes:**
  * Refactored project setup to allow separate module.
  * Switched to Gradle as build tool.

## v0.6.0 - June 28, 2016

* **Changes:**
  * Switched to semantic versioning; `com.rivescript.RiveScript.VERSION` is now
    a string `"0.6.0"` instead of a floating point number.
  * Add support for Java object macros (compile-time) via the new API
    function `setSubroutine()`
  * Add API function `currentUser()` which returns the current user's ID,
    accessible from a Java object macro.
  * Add API function `lastMatch()` which returns the text of the most recently
    matched trigger for a user's message.
  * Add support for `! local concat` option to override concatenation mode
    (file scoped)
  * Add unit tests and fix various bugs discovered in the process:
    * Make the BEGIN block work if it doesn't give an `{ok}` response.
    * Only crawl the topic inheritance tree if the topic inherits or includes
      other topics.
    * Process tags on `@Redirect` before redirecting.
    * Implement the fix from RiveScript-JS [#48](https://github.com/aichaos/rivescript-js/issues/48)
      with regards to matching optionals.
    * Fix escaping regular expression metacharacters in some places.
    * Implement the ability to *set* `<bot>` and `<env>` variables with the
      syntax like `<bot name=value>`.
    * Implement the fix from RiveScript-JS [#92](https://github.com/aichaos/rivescript-js/issues/92)
      with regards to the `<@>` tag failing if `<star1>` is undefined.
    * Fix a couple off-by-one errors with `<formal>` and `<sentence>` formatting
      where the second character was being made uppercase instead of the first.
    * Fix an egregious typo in `getTopicTree` where RiveScript would crash if a
      topic inherited other topics, but didn't include other topics. The code
      was checking for inheritance but looping over includes.

## v0.03-beta - Nov 26, 2014

* Initial official beta release.
