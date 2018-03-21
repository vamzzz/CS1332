import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Vamshi Adimulam
 * @version 1.0
 */
public class StringSearching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text) {
    	if (pattern == null || pattern.length() == 0) {
    		throw new IllegalArgumentException("Pattern is null or empty.");
    	}
    	if (text == null) {
    		throw new IllegalArgumentException("Text is null.");
    	}
    	
    	int i = text.length();
    	int j = pattern.length();

    	if (j > i) {
    		return new ArrayList<>();
    	}

    	int[] failureTable = buildFailureTable(pattern);
    	int x = 0;
    	int y = 0;

    	ArrayList<Integer> result = new ArrayList<>();

    	while (j - y <= i - x) {
    		if (text.charAt(x) == pattern.charAt(y)) {
    			if (y == j - 1) {
    				result.add(x - j + 1);
    				y = failureTable[y];
    			} else {
    				y++;
    			}
    			x++;
    		} else if (y > 0) {
    			y = failureTable[y - 1];
    		} else {
    			x++;
    		}
    	}
    	return result;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern) {
    	if (pattern == null) {
    		throw new IllegalArgumentException("Pattern is null.");
    	}
    	int[] table = new int[pattern.length()];
    	table[0] = 0;
    	int x = 1;
    	int y = 0;

    	while (x < pattern.length()) {
    		if (pattern.charAt(x) == pattern.charAt(y)) {
    			table[x] = y + 1;
    			x++;
    			y++;
    		} else if (y > 0){
    			y = table[y - 1];
    		} else {
    			x++;
    		}
    	}
    	return table;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
            CharSequence text) {
    	if (pattern == null || pattern.length() == 0) {
    		throw new IllegalArgumentException("Pattern is null or empty.");
    	}
    	if (text == null) {
    		throw new IllegalArgumentException("Text is null.");
    	}
    	ArrayList<Integer> result = new ArrayList<>();
    	if (pattern.length() > text.length()) {
    		return result;
    	}
    	Map<Character, Integer> table = buildLastTable(pattern);
    	int x = text.length();
    	int y = pattern.length();
    	int a = y - 1;
    	int b = y - 1;

    	char current;
    	while (a < x) {
    		current = text.charAt(a);
    		int t;
    		if (table.get(current) != null) {
    			t = table.get(current);
    		} else {
    			t = -1;
    		}

    		if (current == pattern.charAt(b)) {
    			if (b == 0) {
    				result.add(a);
    				a += y - Math.min(b, 1 + t);
    				b = y - 1;
    			} else {
    				a--;
    				b--;
    			}
    		} else {
    			a += y - Math.min(b, 1 + t);
    			b = y - 1;
    		}
    	}
    	return result;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
    	if (pattern == null) {
    		throw new IllegalArgumentException("Pattern is null.");
    	}

    	HashMap<Character, Integer> table = new HashMap<>();
    	for (int i = 0; i < pattern.length(); i++) {
    		table.put(pattern.charAt(i), i);
    	}
    	return table;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 599;

    /**
     * Runs Rabin-Karp algorithm. Generate the pattern hash, and compare it with
     * the hash from a substring of text that's the same length as the pattern.
     * If the two hashes match, compare their individual characters, else update
     * the text hash and continue.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
            CharSequence text) {
    	if (pattern == null || pattern.length() == 0) {
    		throw new IllegalArgumentException("Pattern is null or empty.");
    	}
    	if (text == null) {
    		throw new IllegalArgumentException("Text is null.");
    	}

    	if (pattern.length() > text.length()) {
    		return new ArrayList<>();
    	}

    	int patternHash = generateHash(pattern, pattern.length());
    	int textHash = generateHash(text, pattern.length());

    	ArrayList<Integer> result = new ArrayList<>();

    	
    	for (int i = 0; i <= text.length() - pattern.length(); i++) {
    		if (textHash == patternHash) {
    			if (equals(pattern, text, i)) {
    				result.add(i);
    			}
    		}
    		if (i != text.length() - pattern.length()) {
    			textHash = updateHash(textHash, pattern.length(),
    				text.charAt(i), text.charAt(i + pattern.length()));
    		}
    	}
    	return result;
    }

    /**
     * Helper function that looks at Character Sequence objects and sees
     * if they are equal to each other. It looks at all of the chars in 
     * the pattern and text from the start.
     *
     * @param pattern a string that you are searching for
     * @param text the body of text that you are trying to find the pattern
     * @param start the index where you start comparing
     * @return boolean true or false based on whether they are equal
     */
    private static boolean equals(CharSequence pattern, CharSequence text,
    													int start) {
    	for (int i = 0; i < pattern.length(); i++) {
    		if (pattern.charAt(i) != text.charAt(i + start)) {
    			return false;
    		}
    	}
    	return true;
    }

    /**
     * Hash function used for Rabin-Karp. The formula for hashing a string is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 599 hash
     * = b * 599 ^ 3 + u * 599 ^ 2 + n * 599 ^ 1 + n * 599 ^ 0 = 98 * 599 ^ 3 +
     * 117 * 599 ^ 2 + 110 * 599 ^ 1 + 110 * 599 ^ 0 = 21104382019
     *
     * However, note that that will roll over to -370454461, because the largest
     * number that can be represented by an int is 2147483647.
     *
     * Do NOT use {@code Math.pow()} in this method.
     *
     * @throws IllegalArgumentException if current is null
     * @throws IllegalArgumentException if length is negative, 0, or greater
     *     than the length of current
     * @param current substring you are generating hash function for
     * @param length the length of the string you want to generate the hash for,
     * starting from index 0. For example, if length is 4 but current's length
     * is 6, then you include indices 0-3 in your hash (and pretend the actual
     * length is 4)
     * @return hash of the substring
     */
    public static int generateHash(CharSequence current, int length) {
    	if (current == null) {
    		throw new IllegalArgumentException("Current sequence is null.");
    	}
    	if (length <= 0 || length > current.length()) {
    		throw new IllegalArgumentException("Length is out of bounds.");
    	}
    	int result = 0;
    	for (int i = length - 1, x = 0; i >= 0; i--, x++) {
    		result += current.charAt(x) * pow(BASE, i);
    	}
    	return result;
    }

    /**
     * Updates a hash in constant time to avoid constantly recalculating
     * entire hash. To update the hash:
     *
     *  remove the oldChar times BASE raised to the length - 1, multiply by
     *  BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 599
     * hash("unny") = (hash("bunn") - b * 599 ^ 3) * 599 + y * 599 ^ 0 =
     * (-370454461 - 98 * 599 ^ 3) * 599 + 121 * 599 ^ 0 = -12838241666916
     *
     * However, the number will roll over to 1904016410.
     *
     * The computation of BASE raised to length - 1 may require O(log n) time,
     * but the method should otherwise run in O(1).
     *
     * Do NOT use {@code Math.pow()} in this method. We have provided a pow()
     * method for you to use.
     *
     * @throws IllegalArgumentException if length is negative or 0
     * @param oldHash hash generated by generateHash
     * @param length length of pattern/substring of text
     * @param oldChar character we want to remove from hashed substring
     * @param newChar character we want to add to hashed substring
     * @return updated hash of this substring
     */
    public static int updateHash(int oldHash, int length, char oldChar,
            char newChar) {
    	if (length <= 0) {
    		throw new IllegalArgumentException("Length is below 0.");
    	}

    	return (oldHash - oldChar * pow(BASE, length - 1)) *
    		BASE + newChar * pow(BASE, 0);
    }

    /**
     * Calculate the result of a number raised to a power.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
