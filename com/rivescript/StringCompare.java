/*
    com.rivescript.RiveScript - The Official Java RiveScript Interpreter
    Copyright (C) 2010  Noah Petherbridge

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

package com.rivescript;

import java.util.Comparator;

/**
 * A comparator class to sort strings by length, longest to shortest.
 */

public class StringCompare implements Comparator<String> {
	@Override
	public int compare (String o1, String o2) {
		if (o1.length() < o2.length()) {
			return 1;
		}
		else if (o1.length() > o2.length()) {
			return -1;
		}
		return 0;
	}
}
