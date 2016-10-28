# Contributing

Interested in contributing to RiveScript? Great!

First, check the general contributing guidelines for RiveScript and its primary
implementations found at <http://www.rivescript.com/contributing>.

Users should simply import the Eclipse 
[RiveScript Java Style](https://github.com/aichaos/rivescript-java/blob/master/src/eclipse/rivescript-java-style.xml) 
file in their IDE. The same code formatter file should also be imported in IntelliJ IDEA.

# Quick Start

Fork, then clone the repo:

    git clone git@github.com:your-username/rivescript-java.git

Make your code changes and test them by using the included `RSBot` sample:

    ./gradlew :rivescript-samples-rsbot:runApp --console plain

Push to your fork and [submit a pull request](https://github.com/kirsle/rivescript-java/compare/).

At this point you're waiting on me. I'm usually pretty quick to comment on pull
requests (within a few days) and I may suggest some changes or improvements
or alternatives.

Some things that will increase the chance that your pull request is accepted:

* Follow the style guide at <http://www.rivescript.com/contributing>
* Write a [good commit message](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html).
