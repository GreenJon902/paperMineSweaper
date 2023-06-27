package com.greenjon902.paperminesweaper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Utils {
	public static boolean intArrayListContainsArray(ArrayList<int[]> a, int[] b) {
		for (int[] c : a) {
			if (Arrays.equals(b, c)) {
				return true;
			}
		}
		return false;
	}
}
