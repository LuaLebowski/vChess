package vChess;

import java.util.ArrayList;

public class HandyUtil {
	/**
	 * Checks if a string array contains specified string
	 * @param array string array
	 * @param str the string to check if it is contained
	 * @return true if contains, otherwise false
	 */
	public static Boolean strContains(String[] array, String str) {
		for(String s : array) {
			if(s.equals(str)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks if an object is contained in <code>ArrayList</code> <b>exactly 2</b> times
	 * @param arr ArrayList to look for the object in
	 * @param obj the object to check if contained in <code>arr</code> twice
	 * @return true if contained twice, otherwise false
	 */
	public static Boolean containsTwice(@SuppressWarnings("rawtypes") ArrayList arr, Object obj) {
		int r = 0;
		for(Object cmp : arr) {
			if(obj.equals(cmp)) {
				r++;
			}
		}
		return r == 2;
	}
	/**
	 * Concatenates two arrays of <code>int</code>s
	 * @param a array no. 1
	 * @param b array no. 2
	 * @return array made of <code>a</code> and <code>b</code>
	 */
	public static int[] intArrConcat(int[] a, int[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   int[] c = new int[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}
}
