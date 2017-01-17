# Revision History for RiveScript-Java

* 0.7.2  January 17 2017
  * Fix to make the `RiveScript` thread safe using a a `ThreadLocal` to store
    the current user.  (#18).
  * Add support for escaping `_` (underscores) in triggers.

* 0.7.1  January 9 2017
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

* 0.7.0  December 22 2016
  * Refactored project setup to allow separate module.
  * Switched to Gradle as build tool.

* 0.6.0  June 28 2016
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

* 0.03  Nov 26 2014
  * Initial official beta release.
