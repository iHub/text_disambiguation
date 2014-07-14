package Engine;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class LanguageModel {

    /* Language Model = Source Model
     * Calculate probability that someone typed w from all the dictionary words in D
     * Calculate its frequency within the corpus
     * P(w)=freq(w)+0.5 / M+V*0.5 (M = dictionary size) (V = number of zero frequency words)
     */
    double numerator;
    double denominator;

    private final Reader r = new Reader();

    final private Map<String, Integer> unigram_freq = r.getUnigram(); //Word unigrams with their respective frequencies
    final private Map<String, Integer> bigram_freq = r.getBigram(); //Word bigrams with their respective frequencies
    final private Map<String, Integer> trigram_freq = r.getTrigram(); //Word trigrams with their respective frequencies

    final int unigram_freq_size = unigram_freq.size(); //size of unigram counts

    private final Candidates cnds = new Candidates();

    /*
     * UNIGRAM LANGUAGE MODEL
     Prior probability P(w) of a candidate word was calculated on the basis of its frequency in a large corpus
     *
     * P(w)=freq(w)+0.5/M+V*0.5

     * SMOOTHING
     * freq(w) is the number of times the word w appeared in corpus of size M.
     * 0.5 is added to the frequency count to cater the prior probability of those correct dictionary words whose frequency in the corpus was 0.
     * To normalize the effect of this addition (V*0.5) is added to the denominator
     * M is the word size of the corpus
     * V is the total number of zero frequency words

     Numerator => Frequency of candidate word within the courpus + 0.5
     Denominator => Word size of the corpus + (Total number of zero frequency words * 0.5)
     */
    /**
     * Determine the likelihood of candidate words, calculated on the basis of
     * the input word's frequency in the corpus. [0] - Candidate word. [1] -
     * Frequency of the candidate word. [2] - Priori probability of the
     * candidate word, to 5 decimal places
     *
     * @param invalidWord
     * @return Priori probability from the unigram language model
     */
    public String[][] unigramLanguageModel(String invalidWord) {
        invalidWord = invalidWord.toLowerCase();

        String[] candidates = cnds.getCandidateCorrections(invalidWord); //Candidate corrections for invalid word input
        int[] priori_freq = new int[candidates.length]; //n-gram frequencies of respective candidate corrections
        double[] priori_prob = new double[candidates.length]; //priori probabilities of respective candidate corrections

        String priori[][] = new String[candidates.length][3]; //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence

        for (int counter = 0; counter < candidates.length; counter++) {
            int gram_count; //Unigram frequency of the candidate
            int zero_freq_words = candidates.length;
            if (unigram_freq.get(candidates[counter]) == null) {
                gram_count = 0; //Zero occurences of the candidate word(s) from the lexicon
            } else {
                gram_count = unigram_freq.get(candidates[counter]); //Unigram frequency of the candidate word
                zero_freq_words -= 1; //For every non-zero frequency word, decrement the zero_frequecy_word variable.
            }
            priori_freq[counter] = (gram_count); //n-gram frequency of the candidate word

            if (gram_count == 0) {
                numerator = gram_count + 0.5;
                denominator = unigram_freq_size + (zero_freq_words * 0.5);
            } else {
                numerator = 1;
                denominator = unigram_freq_size + (zero_freq_words);
            }

            priori_prob[counter] = numerator / denominator;  //priori prob. of the candidate word

            priori[counter][0] = candidates[counter]; //candidate word
            priori[counter][1] = String.valueOf(priori_freq[counter]); //n-gram frequency of the candidate word
            priori[counter][2] = String.valueOf(String.format("%.5f", priori_prob[counter])); //priori prob. of the candidate word, to 5 decimal places
        }

        //Sort candidates array in alphabetic order
        Arrays.sort(priori, new Comparator<String[]>() {
            @Override
            public int compare(final String[] candidates, final String[] candidatez) {
                String candidate1 = candidates[0];
                String candidate2 = candidatez[0];
                return candidate1.compareTo(candidate2);
            }

        });

        return priori;
    }

    /**
     * Determine the likelihood of candidate words, calculated on the basis of
     * the input word's frequency in the corpus, limited to a maximum number of
     * candidate corrections with the highest probabilities. [0] - Candidate
     * word. [1] - Frequency of the candidate word. [2] - Priori probability of
     * the candidate word, to 5 decimal places
     *
     * @param invalidWord
     * @param candidateGroupSize
     * @return Priori probability from the unigram language model
     */
    public String[][] unigramLanguageModel(String invalidWord, int candidateGroupSize) {
        invalidWord = invalidWord.toLowerCase();

        String[] candidates = cnds.getCandidateCorrections(invalidWord); //Candidate corrections for invalid word input
        int[] priori_freq = new int[candidates.length]; //n-gram frequencies of respective candidate corrections
        double[] priori_prob = new double[candidates.length]; //priori probabilities of respective candidate corrections

        String priori[][] = new String[candidates.length][3]; //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence

        for (int counter = 0; counter < candidates.length; counter++) {
            int gram_count; //Unigram frequency of the candidate
            int zero_freq_words = candidates.length;
            if (unigram_freq.get(candidates[counter]) == null) {
                gram_count = 0; //Zero occurences of the candidate word(s) from the lexicon
            } else {
                gram_count = unigram_freq.get(candidates[counter]); //Unigram frequency of the candidate word
                zero_freq_words -= 1; //For every non-zero frequency word, decrement the zero_frequecy_word variable.
            }
            priori_freq[counter] = (gram_count); //n-gram frequency of the candidate word

            if (gram_count == 0) {
                numerator = gram_count + 0.5;
                denominator = unigram_freq_size + (zero_freq_words * 0.5);
            } else {
                numerator = 1;
                denominator = unigram_freq_size + (zero_freq_words);
            }

            priori_prob[counter] = numerator / denominator;  //priori prob. of the candidate word

            priori[counter][0] = candidates[counter]; //candidate word
            priori[counter][1] = String.valueOf(priori_freq[counter]); //n-gram frequency of the candidate word
            priori[counter][2] = String.valueOf(String.format("%.5f", priori_prob[counter])); //priori prob. of the candidate word, to 5 decimal places
        }//end for

        //Sort candidates array in alphabetic order
        Arrays.sort(priori, new Comparator<String[]>() {
            @Override
            public int compare(final String[] candidates, final String[] candidatez) {
                String candidate1 = candidates[0];
                String candidate2 = candidatez[0];
                return candidate1.compareTo(candidate2);
            }

        });

        //Retrieve the top-N elements with the highest probabilities
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < candidates.length)) {

            String[][] top_priori = new String[candidateGroupSize][3]; //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence

            //Sort Priori array based on probabilities of candidates
            Arrays.sort(priori, new Comparator<String[]>() {
                @Override
                public int compare(final String[] probabilities, final String[] probabilitiez) {
                    String probability1 = probabilities[2];
                    String probability2 = probabilitiez[2];
                    return probability2.compareTo(probability1);
                }

            });

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {
                top_priori[counter][0] = priori[counter][0]; //candidate word
                top_priori[counter][1] = priori[counter][1]; //n-gram frequency of the candidate word
                top_priori[counter][2] = priori[counter][2]; //priori prob. of the candidate word, to 5 decimal places
            }

            return top_priori;
        } else {
            return priori;
        }
    }

    /*
     * (Before) Bigram Language Model Prior probability P(w) of a bigram was calculated on the basis of its frequency in a large corpus
     *
     * P(w)= count(bigram) / word size of corpus
     * SMOOTHING
     * count(bigram) is the number of times the bigram appeared in corpus of size M.
     * 0.5 is added to the frequency count to cater the prior probability of zero bigram frequencies
     * To normalize the effect of this addition (V*0.5) is added to the denominator
     * M is the word size of the corpus
     * V is the total number of zero frequency bigrams

     Numerator => Frequency of bigram within the courpus + 0.5
     Denominator => Word size of the corpus + (Total number of zero frequency bigrams * 0.5)
     */
    /**
     * Determine the likelihood of candidate words, calculated on the basis of a
     * bigram (the preceding word to the input together with the input word
     * itself) frequency in the corpus. [0] - Candidate word. [1] - Bigram
     * comprising the previous word and current one. [2] - Frequency of the
     * bigram. [3] - Priori probability of the candidate word, to 5 decimal
     * places
     *
     * @param invalidWord
     * @param precedingWord
     * @return Priori probability from the bigram language model
     */
    public String[][] bigramLanguageModel(String invalidWord, String precedingWord) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();

        String[] candidates = cnds.getCandidateCorrections(invalidWord); //Candidate corrections for invalid word input
        String priori[][] = new String[candidates.length][4]; //Candidate word, Bigram ,Frequency of Occurrence, Likelihood probability of Occurence

        int[] priori_freq = new int[candidates.length]; //n-gram frequencies of respective bigrams
        double[] priori_prob = new double[candidates.length]; //priori probabilities of respective bigrams

        for (int counter = 0; counter < candidates.length; counter++) {

            String bigram = precedingWord.concat(" ").concat(candidates[counter]); //Form bigram by appending preceding word to current one

            int bigram_count, unigram_count;
            //Bigram counts
            int zero_freq_bigrams = candidates.length;
            if (bigram_freq.get(bigram) == null) {
                bigram_count = 0;
            } else {
                bigram_count = bigram_freq.get(bigram);
                zero_freq_bigrams -= 1; //For every non-zero frequency word, decrement the zero_frequecy_word variable.
            }
            priori_freq[counter] = bigram_count; //n-gram frequency of the bigram

            //Unigram counts
            if (unigram_freq.get(precedingWord) == null) {
                unigram_count = 0;
            } else {
                unigram_count = unigram_freq.get(precedingWord);
            }

            if (bigram_count == 0) {
                numerator = bigram_count + 0.5;
                denominator = unigram_count + (zero_freq_bigrams * 0.5);
            } else {
                numerator = 1;
                denominator = unigram_count + (zero_freq_bigrams);
            }

            priori_prob[counter] = numerator / denominator; //priori prob. of the candidate word

            priori[counter][0] = candidates[counter]; //candidate word
            priori[counter][1] = bigram; //bigram comprising previous word and current one
            priori[counter][2] = String.valueOf(priori_freq[counter]); //n-gram frequency of the bigram
            priori[counter][3] = String.valueOf(String.format("%.5f", priori_prob[counter])); //priori prob. of the candidate word, to 5 decimal places
        }

        //Sort candidates array in alphabetic order
        Arrays.sort(priori, new Comparator<String[]>() {
            @Override
            public int compare(final String[] candidates, final String[] candidatez) {
                String candidate1 = candidates[0];
                String candidate2 = candidatez[0];
                return candidate1.compareTo(candidate2);
            }

        });

        return priori;

    }//end method

    /**
     * Determine the likelihood of candidate words, calculated on the basis of a
     * bigram (the preceding word to the input together with the input word
     * itself) frequency in the corpus, limited to a maximum number of candidate
     * corrections with the highest probabilities. [0] - Candidate word. [1] -
     * Bigram comprising the previous word and current one. [2] - Frequency of
     * the bigram. [3] - Priori probability of the candidate word, to 5 decimal
     * places
     *
     * @param invalidWord
     * @param precedingWord
     * @param candidateGroupSize
     * @return Priori probability from the bigram language model
     */
    public String[][] bigramLanguageModel(String invalidWord, String precedingWord, int candidateGroupSize) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();

        String[] candidates = cnds.getCandidateCorrections(invalidWord); //Candidate corrections for invalid word input
        String priori[][] = new String[candidates.length][4]; //Candidate word, Bigram ,Frequency of Occurrence, Likelihood probability of Occurence

        int[] priori_freq = new int[candidates.length]; //n-gram frequencies of respective bigrams
        double[] priori_prob = new double[candidates.length]; //priori probabilities of respective bigrams

        for (int counter = 0; counter < candidates.length; counter++) {

            String bigram = precedingWord.concat(" ").concat(candidates[counter]); //Form bigram by appending preceding word to current one

            int bigram_count, unigram_count;
            //Bigram counts
            int zero_freq_bigrams = candidates.length;
            if (bigram_freq.get(bigram) == null) {
                bigram_count = 0;
            } else {
                bigram_count = bigram_freq.get(bigram);
                zero_freq_bigrams -= 1; //For every non-zero frequency word, decrement the zero_frequecy_word variable.
            }
            priori_freq[counter] = bigram_count; //n-gram frequency of the bigram

            //Unigram counts
            if (unigram_freq.get(precedingWord) == null) {
                unigram_count = 0;
            } else {
                unigram_count = unigram_freq.get(precedingWord);
            }

            if (bigram_count == 0) {
                numerator = bigram_count + 0.5;
                denominator = unigram_count + (zero_freq_bigrams * 0.5);
            } else {
                numerator = 1;
                denominator = unigram_count + (zero_freq_bigrams);
            }

            priori_prob[counter] = numerator / denominator; //priori prob. of the candidate word

            priori[counter][0] = candidates[counter]; //candidate word
            priori[counter][1] = bigram; //bigram comprising previous word and current one
            priori[counter][2] = String.valueOf(priori_freq[counter]); //n-gram frequency of the bigram
            priori[counter][3] = String.valueOf(String.format("%.5f", priori_prob[counter])); //priori prob. of the candidate word, to 5 decimal places
        }

        //Sort candidates array in alphabetic order
        Arrays.sort(priori, new Comparator<String[]>() {
            @Override
            public int compare(final String[] candidates, final String[] candidatez) {
                String candidate1 = candidates[0];
                String candidate2 = candidatez[0];
                return candidate1.compareTo(candidate2);
            }
        });

        //Retrieve the top-N elements with the highest probabilities
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < candidates.length)) {

            String[][] top_priori = new String[candidateGroupSize][4]; //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence

            //Sort Priori array based on probabilities of candidates
            Arrays.sort(priori, new Comparator<String[]>() {
                @Override
                public int compare(final String[] probabilities, final String[] probabilitiez) {
                    String probability1 = probabilities[3];
                    String probability2 = probabilitiez[3];
                    return probability2.compareTo(probability1);
                }

            });

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {
                top_priori[counter][0] = priori[counter][0]; //candidate word
                top_priori[counter][1] = priori[counter][1]; //n-gram frequency of the bigram
                top_priori[counter][2] = priori[counter][2]; //frequency occurrence of the bigram
                top_priori[counter][3] = priori[counter][3]; //priori prob. of the candidate word, to 5 decimal places
            }
            return top_priori;
        } else {
            return priori;
        }
    }

    /*
     *  (Before) Trigram Language Model
     Prior probability P(w) of a trigram was calculated on the basis of its frequency in a large corpus
     *
     * P(w)= count(trigram) / word size of corpus

     * SMOOTHING
     * count(trigram) is the number of times the trigram appeared in corpus of size M.
     * 0.5 is added to the frequency count to cater the prior probability of zero trigram frequencies
     * To normalize the effect of this addition (V*0.5) is added to the denominator
     * M is the word size of the corpus
     * V is the total number of zero frequency trigrams

     Numerator => Frequency of trigram within the corpus + 0.5
     Denominator => Word size of the corpus + (Total number of zero frequency trigrams * 0.5)
     */
    /**
     * Determine the likelihood of candidate words, calculated on the basis of a
     * trigram (a preceding word, the preceding word to the input and the input
     * word itself) frequency in the corpus. [0] - Candidate word. [1] - Trigram
     * comprising a preceding word, the previous word to the input and the input
     * itself. [2] - Bigram (the preceding word to the input together with the
     * input word itself. [3] - Frequency of the trigram. [3] - Priori
     * probability of the candidate word, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @param precedingPrecedingWord
     * @return Priori probability from the trigram language model
     */
    public String[][] trigramLanguageModel(String invalidWord, String precedingWord, String precedingPrecedingWord) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();
        precedingPrecedingWord = precedingPrecedingWord.toLowerCase();

        String[] candidates = cnds.getCandidateCorrections(invalidWord); //Candidate corrections for invalid word input
        String priori[][] = new String[candidates.length][5]; //Candidate word, Trigram ,Bigram, Frequency of Occurrence, Likelihood probability of Occurence

        int[] priori_freq = new int[candidates.length]; //n-gram frequencies of respective trigrams
        double[] priori_prob = new double[candidates.length]; //priori probabilities of respective trigrams

        for (int counter = 0; counter < candidates.length; counter++) {

            String trigram = precedingPrecedingWord.concat(" ").concat(precedingWord).concat(" ").concat(candidates[counter]); //Form trigram by appending preceding-preceding word to preceding word to current one
            String bigram = precedingPrecedingWord.concat(" ").concat(precedingWord); //Form bigram by appending preceding-preceding word to preceding word

            int trigram_count, bigram_count;
            //Bigram counts
            int zero_freq_trigrams = candidates.length;
            if (trigram_freq.get(trigram) == null) {
                trigram_count = 0;
            } else {
                trigram_count = trigram_freq.get(trigram);
                zero_freq_trigrams -= 1; //For every non-zero frequency word, decrement the zero_frequecy_word variable.
            }
            priori_freq[counter] = trigram_count; //n-gram frequency of the trigram

            //Unigram counts
            if (bigram_freq.get(bigram) == null) {
                bigram_count = 0;
            } else {
                bigram_count = bigram_freq.get(bigram);
            }

            if (trigram_count == 0) {
                numerator = trigram_count + 0.5;
                denominator = bigram_count + (zero_freq_trigrams * 0.5);
            } else {
                numerator = 1;
                denominator = bigram_count + (zero_freq_trigrams);
            }

            priori_prob[counter] = numerator / denominator; //priori prob. of the candidate word

            priori[counter][0] = candidates[counter]; //candidate word
            priori[counter][1] = trigram; //trigram comprising previous-previous word, previous word and current one
            priori[counter][2] = bigram; //bigram comprising preceding-preceding word and preceding word
            priori[counter][3] = String.valueOf(priori_freq[counter]); //n-gram frequency of the trigram
            priori[counter][4] = String.valueOf(String.format("%.5f", priori_prob[counter])); //priori prob. of the candidate word, to 5 decimal places
        }//end for

        //Sort candidates array in alphabetic order
        Arrays.sort(priori, new Comparator<String[]>() {
            @Override
            public int compare(final String[] candidates, final String[] candidatez) {
                String candidate1 = candidates[0];
                String candidate2 = candidatez[0];
                return candidate1.compareTo(candidate2);
            }

        });

        return priori;

    }

    /**
     * Determine the likelihood of candidate words, calculated on the basis of a
     * trigram (a preceding word, the preceding word to the input and the input
     * word itself) frequency in the corpus, limited to a maximum number of
     * candidate corrections with the highest probabilities. [0] - Candidate
     * word. [1] - Trigram comprising a preceding word, the previous word to the
     * input and the input itself. [2] - Bigram (the preceding word to the input
     * together with the input word itself. [3] - Frequency of the trigram. [3]
     * - Priori probability of the candidate word, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @param precedingPrecedingWord
     * @param candidateGroupSize
     * @return Priori probability from the trigram language model
     */
    public String[][] trigramLanguageModel(String invalidWord, String precedingWord, String precedingPrecedingWord, int candidateGroupSize) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();
        precedingPrecedingWord = precedingPrecedingWord.toLowerCase();

        String[] candidates = cnds.getCandidateCorrections(invalidWord); //Candidate corrections for invalid word input
        String priori[][] = new String[candidates.length][5]; //Candidate word, Trigram, Bigram,Frequency of Occurrence, Likelihood probability of Occurence

        int[] priori_freq = new int[candidates.length]; //n-gram frequencies of respective trigrams
        double[] priori_prob = new double[candidates.length]; //priori probabilities of respective trigrams

        for (int counter = 0; counter < candidates.length; counter++) {

            String trigram = precedingPrecedingWord.concat(" ").concat(precedingWord).concat(" ").concat(candidates[counter]); //Form trigram by appending preceding-preceding word to preceding word to current one
            String bigram = precedingPrecedingWord.concat(" ").concat(precedingWord); //Form bigram by appending preceding-preceding word to preceding word

            int trigram_count, bigram_count;
            //Bigram counts
            int zero_freq_trigrams = candidates.length;
            if (trigram_freq.get(trigram) == null) {
                trigram_count = 0;
            } else {
                trigram_count = trigram_freq.get(trigram);
                zero_freq_trigrams -= 1; //For every non-zero frequency word, decrement the zero_frequecy_word variable.
            }
            priori_freq[counter] = trigram_count; //n-gram frequency of the trigram

            //Unigram counts
            if (bigram_freq.get(bigram) == null) {
                bigram_count = 0;
            } else {
                bigram_count = bigram_freq.get(bigram);
            }

            if (trigram_count == 0) {
                numerator = trigram_count + 0.5;
                denominator = bigram_count + (zero_freq_trigrams * 0.5);
            } else {
                numerator = 1;
                denominator = bigram_count + (zero_freq_trigrams);
            }

            priori_prob[counter] = numerator / denominator; //priori prob. of the candidate word

            priori[counter][0] = candidates[counter]; //candidate word
            priori[counter][1] = trigram; //trigram comprising previous-previous word, previous word and current one
            priori[counter][2] = bigram; //bigram comprising preceding-preceding word and preceding word
            priori[counter][3] = String.valueOf(priori_freq[counter]); //n-gram frequency of the trigram
            priori[counter][4] = String.valueOf(String.format("%.5f", priori_prob[counter])); //priori prob. of the candidate word, to 5 decimal places
        }

        //Sort candidates array in alphabetic order
        Arrays.sort(priori, new Comparator<String[]>() {
            @Override
            public int compare(final String[] candidates, final String[] candidatez) {
                String candidate1 = candidates[0];
                String candidate2 = candidatez[0];
                return candidate1.compareTo(candidate2);
            }

        });

        //Retrieve the top-N elements with the highest probabilities
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < candidates.length)) {

            String[][] top_priori = new String[candidateGroupSize][5]; //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence

            //Sort Priori array based on probabilities of candidates
            Arrays.sort(priori, new Comparator<String[]>() {
                @Override
                public int compare(final String[] probabilities, final String[] probabilitiez) {
                    String probability1 = probabilities[4];
                    String probability2 = probabilitiez[4];
                    return probability2.compareTo(probability1);
                }

            });

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {
                top_priori[counter][0] = priori[counter][0]; //candidate word
                top_priori[counter][1] = priori[counter][1]; //trigram
                top_priori[counter][2] = priori[counter][2]; //bigram
                top_priori[counter][3] = priori[counter][3]; //n-gram frequency of the trigram
                top_priori[counter][4] = priori[counter][4]; //priori prob. of the candidate word, to 5 decimal places
            }
            return top_priori;
        } else {
            return priori;
        }

    }

}
