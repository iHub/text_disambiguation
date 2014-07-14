package Engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class Candidates {

    //Phonetic coding scheme
    private final Phonetic fonetic = new Phonetic();

    //Read words from Lexicons
    private final Reader r = new Reader();
    //English
    private final List<String> eng_dict_words = r.getEnglishLexicon(); //English words
    private final List<String> eng_morphemes = r.getEnglishMorphemes(); //English morphemes
    //Swahili
    private final List<String> swa_dict_words = r.getSwahiliLexicon(); //Swahili words
    private final List<String> swa_verbs = r.getSwahiliVerbs(); //Swahili verbs
    private final List<String> swa_adj = r.getSwahiliAdjectives(); //Swahili adjectives

    /**
     * Gets an invalid word's candidate corrections, from the Swahili lexicon
     *
     * @param invalidWord
     * @return candidate corrections for a given invalid word
     */
    public String[] getSwahiliCandidates(String invalidWord) {
        invalidWord = invalidWord.toLowerCase();

        //All of Swahili
        swa_dict_words.addAll(swa_verbs);
        swa_dict_words.addAll(swa_adj);

        //Phonemic rep. of lexicon  words
        List<String> phonemes = new ArrayList<>(); //stores phonemic representation of words
        for (String ss : swa_dict_words) {
            phonemes.add(fonetic.getSwahiliPhoneme(ss));
        }

        //Process graphemic & phonemic Edit-Distance similarities
        //Insertions, Deletions, Substitutions, Transposition, Phonetic errors
        Set<String> candidates = new HashSet<>();
        for (String ss : phonemes) {
            if ((StringCompare.getLevenshtienDistance(invalidWord, ss) <= 1) && (Graphemic.getConsonantWriting(invalidWord).equalsIgnoreCase(Graphemic.getConsonantWriting(ss)))) {
                candidates.add(ss);
            }
        }

        String[] array = candidates.toArray(new String[candidates.size()]); //Store the set elements in an array
        return array;
    }

    /**
     * Gets an invalid word's candidate corrections, from the English lexicon
     *
     * @param invalidWord
     * @return Candidate corrections for a given invalid word
     */
    public String[] getEnglishCandidates(String invalidWord) {
        invalidWord = invalidWord.toLowerCase();

        //1. Process Numeric homophones
        String preprocess = fonetic.processEnglishPhonetics(invalidWord);
        //2. Perform Grapheme-to-Phoneme conversion
        String input_phoneme = fonetic.getEnglishPhoneme(preprocess);
        //3. Perform phonetic hashing
        String input_phone_code = fonetic.getPhoneticCode(preprocess);

        // Process Phonemes & Phonetic codes
        // Grapheme-to-Phoneme conversion
        List<String> phonemes = new ArrayList<>(); //stores phonemic representation of words
        for (String ss : eng_dict_words) {
            phonemes.add(fonetic.getEnglishPhoneme(ss));
        }

        // Phoneme Phonetic hashing
        List<String> codes = new ArrayList<>(); //stores the phonetic codes of all the words from the lexicon
        for (String ss : phonemes) {
            codes.add(fonetic.getPhoneticCode(ss));
        }

        Set<String> candidates = new HashSet<>();
        //Process Edit-Distance similarities
        for (String ss : eng_dict_words) {
            int lcs_length = StringCompare.getLengthLCS(preprocess, ss); //Length of the Longest Common Subsequence between the input invalid word & dictionary word

            //Consonant Writing , some Abbreviations & Deletions e.g. thrsdy(thursday), tmrw(tomorrow), ntwk(network), melo(mellow)
            if ((lcs_length == preprocess.length())) {
                candidates.add(ss);
            }

            //Single character substitution, insertion & deletions typos e.g. jurt(just), hello-(helro), hello(hellow), hello(helo)
            if ((StringCompare.getLevenshtienDistance(preprocess, ss) <= 1) && (preprocess.charAt(0) == ss.charAt(0))) {
                candidates.add(ss);
            }

            //Special Abbreviations e.g. net(internet)
            if ((preprocess.length() > 2) && (ss.contains(preprocess))) {
                candidates.add(ss);
            }
        }

        //Morphemes
        for (String ss : eng_morphemes) {
            int lcs_morpheme_length = StringCompare.getLengthLCS(preprocess, ss); //Length of the Longest Common Subsequence between the input invalid word & morpheme
            //Process English Morphemes
            if ((lcs_morpheme_length == preprocess.length())) {
                candidates.add(ss);
            }
        }

        //Process phonemes & Phonetic codes
        for (int counter = 0; counter < codes.size(); counter++) {
            int lcs_phoneme_length = StringCompare.getLengthLCS(preprocess, phonemes.get(counter));
            int lcs_phone_code_length = StringCompare.getLengthLCS(input_phone_code, codes.get(counter));

            //option 1: if(((LCS.length()==input_phone_code.length())||(LCSphoneme.length()==preprocess.length()))&&(codes.get(counter).charAt(0)==input_phone_code.charAt(0)))
            //option 2: if((LCS.length()==input_phone_code.length())||(LCSphoneme.length()==preprocess.length()))
            //option 3: if(((LCS.length()==input_phone_code.length())||(LCSphoneme.length()==preprocess.length()))&&(phonemes.get(counter).charAt(0)==invalid_word.charAt(0)))
            //option 4: if(((LCS.length()==input_phone_code.length())||(LCSphoneme.length()==preprocess.length()))&&(codes.get(counter).charAt(0)==input_phone_code.charAt(0)))
            if (lcs_phoneme_length == preprocess.length()) {
                candidates.add(eng_dict_words.get(counter));
            }

            if ((StringCompare.getLevenshtienDistance(preprocess, phonemes.get(counter)) <= 1) && (phonemes.get(counter).charAt(0) == input_phoneme.charAt(0))) {
                candidates.add(eng_dict_words.get(counter));
            }

            //Jaro-Winkler distance metric best suited for short strings over Levenshtien
            if (eng_dict_words.get(counter).length() > 3) {
                if ((StringCompare.getJaroWinklerDistance(input_phone_code, codes.get(counter)) >= 0.8) && (phonemes.get(counter).charAt(0) == input_phoneme.charAt(0))) {
                    candidates.add(eng_dict_words.get(counter));
                }
            } else {
                if ((StringCompare.getJaroWinklerDistance(input_phone_code, codes.get(counter)) >= 0.9) && (phonemes.get(counter).charAt(0) == input_phoneme.charAt(0))) {
                    candidates.add(eng_dict_words.get(counter));
                }
            }
        }

        String[] array = candidates.toArray(new String[candidates.size()]); //Store the set elements in an array
        return array;
    }

    /**
     * Gets an invalid word's candidate corrections, from all the lexicons
     *
     * @param invalidWord
     * @return Candidate corrections for a given invalid word
     */
    public String[] getCandidateCorrections(String invalidWord) {

        invalidWord = invalidWord.toLowerCase();

        String[] swa_candidates = getSwahiliCandidates(invalidWord);
        String[] eng_candidates = getEnglishCandidates(invalidWord);
        String[] candidates = new String[swa_candidates.length + eng_candidates.length];
        System.arraycopy(swa_candidates, 0, candidates, 0, swa_candidates.length);
        System.arraycopy(eng_candidates, 0, candidates, swa_candidates.length, eng_candidates.length);
        return candidates;
    }

}
