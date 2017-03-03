/*
 * Copyright (c) 2016 the original author or authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.rivescript.RiveScript;
import com.rivescript.cmd.Shell;
import com.rivescript.lang.Perl;
import java.io.File;

/**
 * @author Noah Petherbridge
 */
public class RSBot extends Shell {

	@Override
	protected void init(RiveScript bot) {
		// Create a handler for Perl as an object macro language.
		File rsp4jFile = new File(RSBot.class.getClassLoader().getResource("lang/rsp4j.pl").getFile());
		bot.setHandler("perl", new Perl(rsp4jFile.getAbsolutePath()));
		// Define an object macro in Java.
		bot.setSubroutine("javatest", new ExampleMacro());
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			String path = RSBot.class.getClassLoader().getResource("rivescript").getFile();
			args = new String[] { path };
		}
		new RSBot().run(args);
	}
}
