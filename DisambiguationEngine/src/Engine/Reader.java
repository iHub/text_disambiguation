package Engine;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public final class Reader {

    /**
     * Get List of English words from English lexicon
     *
     * @return List of English words
     */
    public final List<String> getEnglishLexicon() {
        List<String> eng_dict_words = new ArrayList<>();
        final InputStream is = getClass().getResourceAsStream("/English/EnglishLexicon.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNextLine()) {
                eng_dict_words.add(scan.nextLine());
            }
            scan.close();
        } catch (Exception ex) {
        }
        return eng_dict_words;
    }

    /**
     * Gets List of English morphemes
     *
     * @return List of English Morphemes
     */
    public final List<String> getEnglishMorphemes() {
        List<String> eng_morphemes = new ArrayList<>();
        final InputStream is = getClass().getResourceAsStream("/English/EnglishMorphemes.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNextLine()) {
                eng_morphemes.add(scan.nextLine());
            }
            scan.close();
        } catch (Exception ex) {
        }
        return eng_morphemes;
    }

    /**
     * Read and Tokenize the corpus into words / non-words
     *
     * @return List of words/non-words from the corpus
     */
    public final List<String> getCorpusWords() {
        List<String> corp_words = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream("/Corpus/Corpus.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNext()) {
                corp_words.add(scan.next()); //append each word into the arraylist
            }
            scan.close();
        } catch (Exception ex) {
        }
        return corp_words;
    }

    /**
     * Gets List of Swahili words from Swahili lexicon
     *
     *
     * @return List of Swahili Words
     */
    public final List<String> getSwahiliLexicon() {
        List<String> swa_dict_words = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream("/Swahili/SwahiliLexicon.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNextLine()) {
                swa_dict_words.add(scan.nextLine());
            }
            scan.close();
        } catch (Exception ex) {
        }
        return swa_dict_words;
    }

    /**
     * Gets List of Swahili verbs (Vitenzi)
     *
     *
     * @return List of Swahili Verbs
     */
    public final List<String> getSwahiliVerbs() {
        List<String> swa_verbs = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream("/Swahili/SwahiliVerbs.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNextLine()) {
                swa_verbs.add(scan.nextLine());
            }
            scan.close();
        } catch (Exception ex) {
        }
        return swa_verbs;
    }

    /**
     * Gets List of Swahili adjectives (Vielezi)
     *
     *
     * @return List of Swahili Adjectives
     */
    public final List<String> getSwahiliAdjectives() {
        List<String> swa_verbs = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream("/Swahili/SwahiliAdjectives.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNextLine()) {
                swa_verbs.add(scan.nextLine());
            }
            scan.close();
        } catch (Exception ex) {
        }
        return swa_verbs;
    }

    /**
     * Read unigrams
     *
     * @return Dictionary with Unigram Word and Count, generated from corpus
     */
    public final Map<String, Integer> getUnigram() {
        Map<String, Integer> gram_freq = new TreeMap<>();
        InputStream is = getClass().getResourceAsStream("/NGram/Unigram.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNextLine()) {
                String[] gram_count = scan.nextLine().split("-->");
                gram_freq.put(gram_count[0], Integer.parseInt(gram_count[1]));
            }
            scan.close();
        } catch (Exception ex) {
        }
        return gram_freq;
    }

    /**
     * Read bigrams
     *
     * @return Dictionary with Bigram Words and Count, generated from corpus
     */
    public final Map<String, Integer> getBigram() {
        Map<String, Integer> gram_freq = new TreeMap<>();
        InputStream is = getClass().getResourceAsStream("/NGram/Bigram.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNextLine()) {
                String[] gram_count = scan.nextLine().split("-->");
                gram_freq.put(gram_count[0], Integer.parseInt(gram_count[1]));
            }
            scan.close();
        } catch (Exception ex) {
        }
        return gram_freq;
    }

    /**
     * Read trigrams
     *
     * @return Dictionary with Trigram Words and Count, generated from corpus
     */
    public final Map<String, Integer> getTrigram() {
        Map<String, Integer> gram_freq = new TreeMap<>();
        InputStream is = getClass().getResourceAsStream("/NGram/Trigram.txt");
        try (Scanner scan = new Scanner(new InputStreamReader(is))) {
            while (scan.hasNextLine()) {
                String[] gram_count = scan.nextLine().split("-->");
                gram_freq.put(gram_count[0], Integer.parseInt(gram_count[1]));
            }
            scan.close();
        } catch (Exception ex) {
        }
        return gram_freq;
    }

}
