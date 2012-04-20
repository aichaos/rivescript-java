Java RiveScript Interpreter
===========================

SYNOPSIS
--------

	This is a Java implementation of a RiveScript interpreter, per the
	Working Draft at http://www.rivescript.com/wd/RiveScript.html

	This is *BETA QUALITY SOFTWARE*

	It is feature complete syntax-wise: it supports all of the RiveScript
	directives and tags, but it DOES NOT support OBJECT MACROS yet!

	If you find a way to crash this library, please tell me how you did it
	so I can improve this library! You can join the forums at
	www.rivescript.com

DOCUMENTATION
-------------

	See the javadocs at doc/index.html

RSBOT DEMO SCRIPT
-----------------

	The RSBot.java is a simple implementation of a Java RiveScript bot. You
	can use it to quickly chat with the Eliza-based bot in the "Aiden/" folder.

	These commands may be used at your input prompt in RSBot:

	/quit        - Quit the program
	/dump topics - Dump the internal topic/trigger/reply struct (debugging)
	/dump sorted - Dump the internal trigger sort buffers (debugging)

SHELL SCRIPTS
-------------

	The shell scripts were there to aid me in development. What they do is:

	build.sh
		Deletes all the *.class files in the current directory and its
		subdirectories, and then uses `javac` to compile RSBot and all
		of the library files.

	javadoc.sh
		Deletes the doc/ folder and recreates it with the `javadoc` command,
		for generating documentation for the library.

QUICK START
-----------

	Compile RSBot.java using `javac`. The preferred way is to run build.sh
	if you have a bash shell.

		Unix:   $ ./build.sh
		        $ javac RSBot.java
		Win32:  > javac RSBot.java

	Then execute RSBot to begin chatting with the demo Eliza-based bot that
	tends to ship with RiveScript libraries.

		Unix:   $ java RSBot
		Win32:  > java RSBot

LICENSING
---------

	The Java RiveScript library is currently released under the
	GNU General Public License (see LICENSE). This means you can't use
	it in a closed source commercial application.

	A dual licensing scheme may be decided on in the future for those
	who wish to use this library in a closed source application.

AUTHOR
------

	Noah Petherbridge
	www.kirsle.net
