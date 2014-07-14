package Engine;

import java.util.Arrays;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class StringCompare {

    /** Levenshtien edit distance ed(t, p) between two strings p (pattern) and t
     * (text) as the minimum number of insertions, deletions and replacements to
     * make p equal to t.
     *
     * @param originalString
     * @param anotherString
     * @return Levenshtien edit distance between two strings
     */
    public static int getLevenshtienDistance(String originalString, String anotherString) {
        /* Levenshtien Edit distance string matching...
         * The edit distance ed(t, p) between two strings p (pattern) and t (text) is the minimum number of insertions, deletions and replacements to make p equal to t.
         * Dynamic programming algorithm must fill the matrix in such a way that the upper, left, and upper-left neighbors of a cell are computed prior to computing that cell.
         */

        int[][] array; // arrays to store the minimum number of operations needed to match one string to another

        // create an array of length+1 of both strings to store elements rep. the cost of operations needed to convert a char to another
        array = new int[originalString.length() + 1][anotherString.length() + 1];

        // d=0 whenever the chars at position p in both strings match else d=1, i.e. d(a,b)=0 if a=b else d(a,b)=1
        int d;

        //Populate first row & column with numbers (0...n)
        for (int first_row = 0; first_row <= originalString.length(); first_row++) {
            array[first_row][0] = first_row; //populate the first row
        }
        for (int first_column = 0; first_column <= anotherString.length(); first_column++) {
            array[0][first_column] = first_column; //populate the first column
        }

        /*
         * To calculate the ED between Strings we use the formula: Ci,j = min { C(i-1),(j-1) + d(Xi,Yj) rep. top-left element (substitution errors)
         C(i-1),(j) +1 rep. top element (deletion errors)
         C(i),(j-1) +1 rep. left element (insertion errors)
         }
         * DYNAMIC PROGRAMMING:
         * The corresponding element in focus within the array is calculated as the minimum number generated from the the surrounding elements based on the formula above
         * From these 3 elements we return the minimum element amongst them as the element at position Array[row][column]
         */
        for (int row = 1; row < array.length; row++) //start from 1 since the first row & column have already been populated with numbers
        {
            for (int column = 1; column < array[row].length; column++) //start from 1 since the first row & column have already been populated with numbers
            {
                if (originalString.charAt(row - 1) == anotherString.charAt(column - 1)) { // compare the characters in the strings linearly (compare them at -1 because we are counting from 1 but strings start from 0)
                    d = 0; //d=0 wherever the corresponding characters at position p in both strings are similar
                } else {
                    d = 1; //d=1 wherever the corresponding characters at position p in both strings are dissimilar
                }
                array[row][column] = Math.min((Math.min(array[row - 1][column - 1] + d, array[row - 1][column] + 1)), array[row][column - 1] + 1); // stores min value between the top-left, top & left element relative to the element in focus, repspectively.
            }
        }

        return array[array.length - 1][array[array.length - 1].length - 1]; //returns the last element in the last row and last column within that row as the edit distance between the two strings
    }

    /**
     * Measure of similarity between two strings using the Jaro-Winkler distance
     * algorithm
     *
     * @param originalString
     * @param anotherString
     * @return Jaro-Winkler edit distance between two strings
     */
    public static double getJaroWinklerDistance(String originalString, String anotherString) {
        /*
         * Jaro-Winkler distance = measure of similarity between two strings
         *
         *   For two strings, originalString & anotherString;
         *      length_originalString = number of characters constituting originalString
         *      length_anotherString = number of characters constituting anotherString
         *      matching_chars = number of matching characters between them
         *      transpositions = number of transpositional characters between them

         * Jaro-Winkler distance = (1/3) ([matching_chars/length_originalString]+[matching_chars/length_anotherString]+[{matching_chars-transpositions}/matching_chars])
         * such that: 0 equates to no similarity and 1 is an exact match
         */

        int s1_length = originalString.length(); //Length of string originalString
        int s2_length = anotherString.length(); //Length of string anotherString

        int transpositions = getTranspositions(originalString, anotherString); //Number of character transpositions between the two strings

        int matches = getMatches(originalString, anotherString); //Number of matching characters between the two strings

        final double fract1 = (double) 1 / 3;
        double fract2 = (double) matches / s1_length;
        double fract3 = (double) matches / s2_length;

        double fract4num = (double) matches - transpositions;
        double fract4 = (double) fract4num / matches;

        double sum = fract2 + fract3 + fract4;
        double ans = fract1 * sum;
        return ans;
    }

    /**
     * Half the number of transpositions between two strings
     *
     * @param originalString
     * @param anotherString
     * @return Half the number of transpositions between two strings
     */
    private static int getTranspositions(String originalString, String anotherString) {
        int transpositions = 0;
        if (originalString.length() < anotherString.length()) {
            for (int counter = 0; counter < originalString.length(); counter++) {
                if (originalString.charAt(counter) != anotherString.charAt(counter)) {
                    transpositions++;
                }
            }
        } else {
            for (int counter = 0; counter < anotherString.length(); counter++) {
                if (anotherString.charAt(counter) != originalString.charAt(counter)) {
                    transpositions++;
                }
            }
        }
        transpositions /= 2; //calculate half the number of transpositions
        return transpositions;
    }

    /**
     * Number of matching characters between two strings
     *
     * @param originalString
     * @param anotherString
     * @return Number of matching characters between two strings
     */
    private static int getMatches(String originalString, String anotherString) {
        //Calculates the number of matching characters betweent the two strings
        int matches = 0;
        if (originalString.length() > anotherString.length()) {
            for (int counter = 0; counter < anotherString.length(); counter++) {
                if (originalString.indexOf(anotherString.charAt(counter)) >= 0) {
                    matches++;
                }
            }
        } else if (originalString.length() < anotherString.length()) {
            for (int counter = 0; counter < originalString.length(); counter++) {
                if (anotherString.indexOf(originalString.charAt(counter)) >= 0) {
                    matches++;
                }
            }
        } else if (originalString.length() == anotherString.length()) {
            char[] originalArray = originalString.toCharArray();
            char[] anotherArray = anotherString.toCharArray();

            Arrays.sort(originalArray);
            Arrays.sort(anotherArray);

            for (int counter = 0; counter < originalArray.length; counter++) {
                if (originalArray[counter] == anotherArray[counter]) {
                    matches++;
                }
            }
        }
        return matches;
    }

    /**
     * Longest Common Subsequence between two strings
     *
     * @param originalString
     * @param anotherString
     * @return Longest Common Subsequence between two strings
     */
    public static String getLCS(String originalString, String anotherString) {

        int[][] opt; // opt[i][j] = length of LCS of x[i..x_length] and y[j..x_length]
        int s1_length = originalString.length();
        int s2_length = anotherString.length();

        StringBuilder sb = new StringBuilder();

        opt = new int[s1_length + 1][s2_length + 1];

        // compute length of LCS and all subproblems via dynamic programming
        for (int i = s1_length - 1; i >= 0; i--) {
            for (int j = s2_length - 1; j >= 0; j--) {
                if (originalString.charAt(i) == anotherString.charAt(j)) {
                    opt[i][j] = opt[i + 1][j + 1] + 1;
                } else {
                    opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
                }
            }
        }

        // recover LCS itself and return the longest subsequence common to all sequences in the strings
        int i = 0, j = 0;
        while (i < s1_length && j < s2_length) {
            if (originalString.charAt(i) == anotherString.charAt(j)) {
                sb.append(originalString.charAt(i));
                i++;
                j++;
            } else if (opt[i + 1][j] >= opt[i][j + 1]) {
                i++;
            } else {
                j++;
            }
        }
        return sb.toString();
    }

    /**
     * Length of the Longest Common Subsequence between two strings
     *
     * @param originalString
     * @param anotherString
     * @return Length of the Longest Common Subsequence between two strings
     */
    public static int getLengthLCS(String originalString, String anotherString) {

        int[][] opt; // opt[i][j] = length of LCS of x[i..x_length] and y[j..x_length]
        int s1_length = originalString.length();
        int s2_length = anotherString.length();

        opt = new int[s1_length + 1][s2_length + 1];

        // compute length of LCS and all subproblems via dynamic programming
        for (int i = s1_length - 1; i >= 0; i--) {
            for (int j = s2_length - 1; j >= 0; j--) {
                if (originalString.charAt(i) == anotherString.charAt(j)) {
                    opt[i][j] = opt[i + 1][j + 1] + 1;
                } else {
                    opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
                }
            }
        }
        return opt[0][0]; //return length of the longest subsequence common to all sequences in the strings
    }

}
