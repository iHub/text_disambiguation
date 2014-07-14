package Engine;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class Tokenizer {

    /**
     * Segment sentences according to these delimiters: punctuation marks,
     * white-spaces, ampersands, mathematical symbols, word boundaries and line
     * breaks.
     *
     * @param sentence
     * @return Words/non-words from a sentence
     */
    public static String[] getTokens(String sentence) {
        BreakIterator iterator = BreakIterator.getWordInstance();
        iterator.setText(sentence);

        int begin = iterator.first(); //first boundary
        int end; //next boundary

        //Parse sentence, analyse word boundaries & return word/non-word tokens
        List<String> segments = new ArrayList<>();
        for (end = iterator.next(); end != BreakIterator.DONE; end = iterator.next()) {
            String t = sentence.substring(begin, end);
            if (t.length() > 0) {
                segments.add(sentence.substring(begin, end)); //word / non-word boundaries substrings
            }
            begin = end;
        }
        if (end != -1) {
            segments.add(sentence.substring(end)); //last element
        }
        String[] tokens = segments.toArray(new String[segments.size()]);
        return tokens;
    }
}
