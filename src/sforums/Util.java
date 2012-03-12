package sforums;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;

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
}
