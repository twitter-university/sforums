package sforums;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Util {

	private static final String HEX_ALPHABET[] = { "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * Encode byte array in hexadecimal notation.
	 * 
	 * @param in
	 *            the byte array to encode
	 * @return hex-encoded string representation of the byte array.
	 */
	public static String hexEncode(byte in[]) {
		if (in == null || in.length == 0) {
			return null;
		}
		StringBuilder out = new StringBuilder(in.length * 2);
		for (int i = 0; i < in.length; i++) {
			byte b = (byte) (in[i] & 0xF0);
			b = (byte) (b >>> 4);
			b = (byte) (b & 0x0F);
			out.append(HEX_ALPHABET[b]);
			b = (byte) (in[i] & 0x0F);
			out.append(HEX_ALPHABET[b]);
		}
		return new String(out);
	}

	/**
	 * Generate MD5 digest of a string.
	 * 
	 * @param in
	 *            the string to digest.
	 * @return hexadecimal-encoded MD5-digest of the input string.
	 */
	public static String md5Digest(String in) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] digest = messageDigest.digest(in.getBytes());
			return hexEncode(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to get MD5 digest", e);
		}
	}

	/**
	 * Check if a string is empty (blank)
	 * 
	 * @param s
	 *            the string to check
	 * @return true if s is null or its trimmed length is 0; false otherwise.
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0 || s.trim().length() == 0;
	}

	/**
	 * Join elements of a collection into a string.
	 * 
	 * @param collection
	 *            the collection to join
	 * @param delimiter
	 *            the delimiter used between the elements of the collection
	 * @return a joined (merged) string of elements from the collection with the
	 *         delimiter between each one.
	 */
	public static String join(Collection<?> collection, String delimiter) {
		StringBuilder out = new StringBuilder();
		for (Iterator<?> i = collection.iterator(); i.hasNext();) {
			out.append(i.next());
			if (i.hasNext()) {
				out.append(delimiter);
			}
		}
		return out.toString();
	}

	/**
	 * Check if two objects are equal
	 * 
	 * @param o1
	 *            the first object
	 * @param o2
	 *            the second object
	 * @return true if o1 and o2 are both null or are equal
	 */
	public static boolean equal(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	/**
	 * Get the hash code of an object
	 * 
	 * @param r
	 *            initial result
	 * @param o
	 *            the object
	 * @return result plus the hash code of the object or just result if object
	 *         is null
	 */
	public static int hashCode(int r, Object o) {
		return o == null ? r : r + 31 * o.hashCode();
	}

	/**
	 * Get var args as an array.
	 * 
	 * @param ts
	 *            the var args
	 * @return return ts
	 */
	public static <T> T[] array(T... ts) {
		return ts;
	}

	/**
	 * Get the unique element in a list.
	 * 
	 * @param list
	 *            the list
	 * @return the first element of the list or null if the list is null or
	 *         empty
	 * @throws IllegalArgumentException
	 *             if the list has more than one element
	 */
	public static <T> T uniqueResult(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			throw new IllegalArgumentException(
					"Expecting only a single element in list: " + list);
		}
	}
}
