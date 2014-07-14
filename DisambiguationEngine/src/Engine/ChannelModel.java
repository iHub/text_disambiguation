package Engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class ChannelModel {

    /* Channel Model
     * Language Model probability & Graphemic probability & Phonemic probability & Channel probability.
     * Channel probability = (Graphemic probability + Phonemic probability) / 2
     */
    //Phonetic coding scheme
    private final Phonetic fonetic = new Phonetic();
    private final LanguageModel lmd = new LanguageModel();
    private final Candidates cnds = new Candidates();

    List<String> swahili_candidates;

    /**
     * Calculate the Channel Model probability
     *
     * @param grapheme_probability
     * @param phoneme_probability
     * @return Channel Model probability
     */
    private double getChannelProbability(double grapheme_probability, double phoneme_probability) {
        return (grapheme_probability + phoneme_probability) / 2;
    }

    /**
     * Determine the likelihood of candidate words, calculated on the basis of
     * the Unigram Language model, the Graphemic & Phonemic probability of the
     * input word and the Channel probability. [0] - Candidate word. [1] -
     * Frequency of the candidate word. [2] - Priori probability of the
     * candidate word, to 5 decimal places. [3] - Graphemic probability, to 5
     * decimal places. [4] - Phonemic probability, to 5 decimal places. [5] -
     * Posteriori probability, to 5 decimal places
     *
     * @param invalidWord
     * @return Posteriori probability from the unigram channel model
     */
    public String[][] unigramChannelModel(String invalidWord) {
        invalidWord = invalidWord.toLowerCase();

        String[] swa_cand = cnds.getSwahiliCandidates(invalidWord);
        swahili_candidates = new ArrayList<>(Arrays.asList(swa_cand));

        String priori[][] = lmd.unigramLanguageModel(invalidWord); //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence
        String posteriori[][] = new String[priori.length][6]; //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence, Graphemic prob., Phonemic prob., Channel prob.

        double grapheme_prob, phoneme_prob;

        for (int counter = 0; counter < priori.length; counter++) {
            if (!swahili_candidates.contains(priori[counter][0])) {

                grapheme_prob = Graphemic.getProbabilityEnglishMatches(fonetic.processEnglishPhonetics(invalidWord), priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(fonetic.processEnglishPhonetics(invalidWord), fonetic.getEnglishPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //n-gram frequency of the candidate word
                posteriori[counter][2] = priori[counter][2]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][3] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][4] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][5] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.

            } else {

                grapheme_prob = Graphemic.getProbabilitySwahiliMatches(invalidWord, priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(invalidWord, fonetic.getSwahiliPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //n-gram frequency of the candidate word
                posteriori[counter][2] = priori[counter][2]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][3] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][4] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][5] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.
            }
        }
        return posteriori;
    }

    /**
     * Determine the likelihood of candidate words, calculated on the basis of
     * the Unigram Language model, the Graphemic & Phonemic probability of the
     * input word and the Channel probability, limited to a maximum number of
     * candidate corrections with the highest probabilities. [0] - Candidate
     * word. [1] - Frequency of the candidate word. [2] - Priori probability of
     * the candidate word, to 5 decimal places. [3] - Graphemic probability, to
     * 5 decimal places. [4] - Phonemic probability, to 5 decimal places. [5] -
     * Posteriori probability, to 5 decimal places
     *
     * @param invalidWord
     * @param candidateGroupSize
     * @return Posteriori probability from the unigram channel model
     */
    public String[][] unigramChannelModel(String invalidWord, int candidateGroupSize) {

        invalidWord = invalidWord.toLowerCase();

        String[] swa_cand = cnds.getSwahiliCandidates(invalidWord);
        swahili_candidates = new ArrayList<>(Arrays.asList(swa_cand));

        String priori[][] = lmd.unigramLanguageModel(invalidWord); //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence
        String posteriori[][] = new String[priori.length][6]; //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence, Graphemic prob., Phonemic prob., Channel prob.

        double grapheme_prob, phoneme_prob;

        //Process English & Swahili words separately
        for (int counter = 0; counter < priori.length; counter++) {
            if (!swahili_candidates.contains(priori[counter][0])) {

                grapheme_prob = Graphemic.getProbabilityEnglishMatches(fonetic.processEnglishPhonetics(invalidWord), priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(fonetic.processEnglishPhonetics(invalidWord), fonetic.getEnglishPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //n-gram frequency of the candidate word
                posteriori[counter][2] = priori[counter][2]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][3] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][4] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][5] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.

            } else {

                grapheme_prob = Graphemic.getProbabilitySwahiliMatches(invalidWord, priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(invalidWord, fonetic.getSwahiliPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //n-gram frequency of the candidate word
                posteriori[counter][2] = priori[counter][2]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][3] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][4] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][5] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.
            }
        }

        //Retrieve the top-N elements
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < posteriori.length)) {

            String[][] top_posteriori = new String[candidateGroupSize][6]; //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence

            //Sort Posteriori array based on probabilities of candidates
            Arrays.sort(posteriori, new Comparator<String[]>() {
                @Override
                public int compare(final String[] probabilities, final String[] probabilitiez) {
                    String probability1 = probabilities[5];
                    String probability2 = probabilitiez[5];
                    return probability2.compareTo(probability1);
                }

            });

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {
                top_posteriori[counter][0] = posteriori[counter][0]; //candidate word
                top_posteriori[counter][1] = posteriori[counter][1]; //n-gram frequency of the candidate word
                top_posteriori[counter][2] = posteriori[counter][2]; //priori prob. of the candidate word, to 5 decimal places
                top_posteriori[counter][3] = posteriori[counter][3]; //graphemic prob
                top_posteriori[counter][4] = posteriori[counter][4]; //phonemic prob.
                top_posteriori[counter][5] = posteriori[counter][5]; //posteriori prob.
            }

            return top_posteriori;
        } else {
            return posteriori;
        }
    }

    /**
     * Determine the likelihood of candidate words, calculated on the basis of
     * the Bigram Language model, the Graphemic & Phonemic probability of the
     * input word and the Channel probability. [0] - Candidate word. [1] -
     * Bigram comprising the previous word and current one. [2] - Frequency of
     * the candidate word. [3] - Priori probability of the candidate word, to 5
     * decimal places. [4] - Graphemic probability, to 5 decimal places. [5] -
     * Phonemic probability, to 5 decimal places. [6] - Posteriori probability,
     * to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @return Posteriori probability from the bigram channel model
     */
    public String[][] bigramChannelModel(String invalidWord, String precedingWord) {
        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();

        String[] swa_cand = cnds.getSwahiliCandidates(invalidWord);
        swahili_candidates = new ArrayList<>(Arrays.asList(swa_cand));

        String priori[][] = lmd.bigramLanguageModel(invalidWord, precedingWord); //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence
        String posteriori[][] = new String[priori.length][7]; //Candidate word, Bigram ,Frequency of Occurrence, Likelihood probability of Occurence, Graphemic prob., Phonemic prob., Channel prob.

        double grapheme_prob, phoneme_prob;

        for (int counter = 0; counter < priori.length; counter++) {
            if (!swahili_candidates.contains(priori[counter][0])) {

                grapheme_prob = Graphemic.getProbabilityEnglishMatches(fonetic.processEnglishPhonetics(invalidWord), priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(fonetic.processEnglishPhonetics(invalidWord), fonetic.getEnglishPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //bigram
                posteriori[counter][2] = priori[counter][2]; //n-gram frequency of the candidate word
                posteriori[counter][3] = priori[counter][3]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][4] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][5] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][6] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.

            } else {

                grapheme_prob = Graphemic.getProbabilitySwahiliMatches(invalidWord, priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(invalidWord, fonetic.getSwahiliPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //bigram
                posteriori[counter][2] = priori[counter][2]; //n-gram frequency of the candidate word
                posteriori[counter][3] = priori[counter][3]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][4] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][5] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][6] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.
            }
        }

        return posteriori;
    }

    /**
     * Determine the likelihood of candidate words, calculated on the basis of
     * the Bigram Language model, the Graphemic & Phonemic probability of the
     * input word and the Channel probability, limited to a maximum number of
     * candidate corrections with the highest probabilities. [0] - Candidate
     * word. [1] - Bigram comprising the previous word and current one. [2] -
     * Frequency of the candidate word. [3] - Priori probability of the
     * candidate word, to 5 decimal places. [4] - Graphemic probability, to 5
     * decimal places. [5] - Phonemic probability, to 5 decimal places. [6] -
     * Posteriori probability, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @param candidateGroupSize
     * @return Posteriori probability from the bigram channel model
     */
    public String[][] bigramChannelModel(String invalidWord, String precedingWord, int candidateGroupSize) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();

        String[] swa_cand = cnds.getSwahiliCandidates(invalidWord);
        swahili_candidates = new ArrayList<>(Arrays.asList(swa_cand));

        String priori[][] = lmd.bigramLanguageModel(invalidWord, precedingWord); //Candidate word, Frequency of Occurrence, Likelihood probability of Occurence
        String posteriori[][] = new String[priori.length][7]; //Candidate word, Bigram,Frequency of Occurrence, Likelihood probability of Occurence, Graphemic prob., Phonemic prob.

        double grapheme_prob, phoneme_prob;

        for (int counter = 0; counter < priori.length; counter++) {
            if (!swahili_candidates.contains(priori[counter][0])) {

                grapheme_prob = Graphemic.getProbabilityEnglishMatches(fonetic.processEnglishPhonetics(invalidWord), priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(fonetic.processEnglishPhonetics(invalidWord), fonetic.getEnglishPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //bigram
                posteriori[counter][2] = priori[counter][2]; //n-gram frequency of the candidate word
                posteriori[counter][3] = priori[counter][3]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][4] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][5] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][6] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.

            } else {

                grapheme_prob = Graphemic.getProbabilitySwahiliMatches(invalidWord, priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(invalidWord, fonetic.getSwahiliPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //bigram
                posteriori[counter][2] = priori[counter][2]; //n-gram frequency of the candidate word
                posteriori[counter][3] = priori[counter][3]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][4] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][5] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][6] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.
            }
        }

        //Retrieve the top-N elements
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < posteriori.length)) {

            String[][] top_posteriori = new String[candidateGroupSize][7]; //Candidate word, Bigram,Frequency of Occurrence, Likelihood probability of Occurence, Graphemic prob., Phonemic prob.

            ///Sort Posterori array based on probabilities of candidates
            Arrays.sort(posteriori, new Comparator<String[]>() {
                @Override
                public int compare(final String[] probabilities, final String[] probabilitiez) {
                    String probability1 = probabilities[6];
                    String probability2 = probabilitiez[6];
                    return probability2.compareTo(probability1);
                }

            });

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {
                top_posteriori[counter][0] = posteriori[counter][0]; //candidate word
                top_posteriori[counter][1] = posteriori[counter][1]; //bigram
                top_posteriori[counter][2] = posteriori[counter][2]; //n-gram frequency of the candidate word
                top_posteriori[counter][3] = posteriori[counter][3]; //priori prob. of the candidate word, to 5 decimal places
                top_posteriori[counter][4] = posteriori[counter][4]; //graphemic prob
                top_posteriori[counter][5] = posteriori[counter][5]; //phonemic prob.
                top_posteriori[counter][6] = posteriori[counter][6]; //posteriori prob.
            }

            return top_posteriori;
        } else {
            return posteriori;
        }
    }

    /**
     * Determine the likelihood of candidate words, calculated on the basis of
     * the Trigram Language model, the Graphemic & Phonemic probability of the
     * input word and the Channel probability. [0] - Candidate word. [1] -
     * Trigram comprising a preceding word, the previous word to the input and
     * the input itself. [2] - Bigram comprising the previous word and current
     * one. [3] - Frequency of the candidate word. [4] - Priori probability of
     * the candidate word, to 5 decimal places. [5] - Graphemic probability, to
     * 5 decimal places. [6] - Phonemic probability, to 5 decimal places. [7] -
     * Posteriori probability, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @param precedingPrecedingWord
     * @return Posteriori probability from the trigram channel model
     */
    public String[][] trigramChannelModel(String invalidWord, String precedingWord, String precedingPrecedingWord) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();
        precedingPrecedingWord = precedingPrecedingWord.toLowerCase();

        String[] swa_cand = cnds.getSwahiliCandidates(invalidWord);
        swahili_candidates = new ArrayList<>(Arrays.asList(swa_cand));

        String priori[][] = lmd.trigramLanguageModel(invalidWord, precedingWord, precedingPrecedingWord); //Candidate word, Trigram , Bigram, Frequency of Occurrence, Likelihood probability of Occurence
        String posteriori[][] = new String[priori.length][8]; //Candidate word, Trigram , Bigram, Frequency of Occurrence, Likelihood probability of Occurence, Graphemic prob., Phonemic prob., Channel prob.

        double grapheme_prob, phoneme_prob;

        for (int counter = 0; counter < priori.length; counter++) {
            if (!swahili_candidates.contains(priori[counter][0])) {

                grapheme_prob = Graphemic.getProbabilityEnglishMatches(fonetic.processEnglishPhonetics(invalidWord), priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(fonetic.processEnglishPhonetics(invalidWord), fonetic.getEnglishPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //trigram
                posteriori[counter][2] = priori[counter][2]; //bigram
                posteriori[counter][3] = priori[counter][3]; //n-gram frequency of the candidate word
                posteriori[counter][4] = priori[counter][4]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][5] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][6] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][7] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.

            } else {

                grapheme_prob = Graphemic.getProbabilitySwahiliMatches(invalidWord, priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(invalidWord, fonetic.getSwahiliPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //trigram
                posteriori[counter][2] = priori[counter][2]; //bigram
                posteriori[counter][3] = priori[counter][3]; //n-gram frequency of the candidate word
                posteriori[counter][4] = priori[counter][4]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][5] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][6] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][7] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.
            }
        }

        return posteriori;
    }

    /**
     * Determine the likelihood of candidate words, calculated on the basis of
     * the Trigram Language model, the Graphemic & Phonemic probability of the
     * input word and the Channel probability, limited to a maximum number of
     * candidate corrections with the highest probabilities. [0] - Candidate
     * word. [1] - Trigram comprising a preceding word, the previous word to the
     * input and the input itself. [2] - Bigram comprising the previous word and
     * current one. [3] - Frequency of the candidate word. [4] - Priori
     * probability of the candidate word, to 5 decimal places. [5] - Graphemic
     * probability, to 5 decimal places. [6] - Phonemic probability, to 5
     * decimal places. [7] - Posteriori probability, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @param precedingPrecedingWord
     * @param candidateGroupSize
     * @return Posteriori probability from the trigram channel model
     */
    public String[][] trigramChannelModel(String invalidWord, String precedingWord, String precedingPrecedingWord, int candidateGroupSize) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();
        precedingPrecedingWord = precedingPrecedingWord.toLowerCase();

        String[] swa_cand = cnds.getSwahiliCandidates(invalidWord);
        swahili_candidates = new ArrayList<>(Arrays.asList(swa_cand));

        String priori[][] = lmd.trigramLanguageModel(invalidWord, precedingWord, precedingPrecedingWord); //Candidate word, Trigram , Bigram, Frequency of Occurrence, Likelihood probability of Occurence
        String posteriori[][] = new String[priori.length][8]; //Candidate word, Trigram , Bigram, Frequency of Occurrence, Likelihood probability of Occurence, Graphemic prob., Phonemic prob., Channel prob.

        double grapheme_prob, phoneme_prob;

        for (int counter = 0; counter < priori.length; counter++) {
            if (!swahili_candidates.contains(priori[counter][0])) {

                grapheme_prob = Graphemic.getProbabilityEnglishMatches(fonetic.processEnglishPhonetics(invalidWord), priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(fonetic.processEnglishPhonetics(invalidWord), fonetic.getEnglishPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //trigram
                posteriori[counter][2] = priori[counter][2]; //bigram
                posteriori[counter][3] = priori[counter][3]; //n-gram frequency of the candidate word
                posteriori[counter][4] = priori[counter][4]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][5] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][6] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][7] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.

            } else {

                grapheme_prob = Graphemic.getProbabilitySwahiliMatches(invalidWord, priori[counter][0]);
                phoneme_prob = Phonetic.getProbabilityMatches(invalidWord, fonetic.getSwahiliPhoneme(priori[counter][0]));

                posteriori[counter][0] = priori[counter][0]; //candidate word
                posteriori[counter][1] = priori[counter][1]; //trigram
                posteriori[counter][2] = priori[counter][2]; //bigram
                posteriori[counter][3] = priori[counter][3]; //n-gram frequency of the candidate word
                posteriori[counter][4] = priori[counter][4]; //priori prob. of the candidate word, to 5 decimal places
                posteriori[counter][5] = String.valueOf(String.format("%.5f", grapheme_prob)); //graphemic prob
                posteriori[counter][6] = String.valueOf(String.format("%.5f", phoneme_prob)); //phonemic prob.
                posteriori[counter][7] = String.valueOf(String.format("%.5f", getChannelProbability(grapheme_prob, phoneme_prob))); //posteriori prob.
            }
        }

        //Retrieve the top-N elements
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < posteriori.length)) {

            String[][] top_posteriori = new String[candidateGroupSize][8]; //Candidate word,Trigram, Bigram,Frequency of Occurrence, Likelihood probability of Occurence, Graphemic prob., Phonemic prob.

            ///Sort Posterori array based on probabilities of candidates
            Arrays.sort(posteriori, new Comparator<String[]>() {
                @Override
                public int compare(final String[] probabilities, final String[] probabilitiez) {
                    String probability1 = probabilities[7];
                    String probability2 = probabilitiez[7];
                    return probability2.compareTo(probability1);
                }

            });

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {
                top_posteriori[counter][0] = posteriori[counter][0]; //candidate word
                top_posteriori[counter][1] = posteriori[counter][1]; //trigram
                top_posteriori[counter][2] = posteriori[counter][2]; //bigram
                top_posteriori[counter][3] = posteriori[counter][3]; //n-gram frequency of the candidate word
                top_posteriori[counter][4] = posteriori[counter][4]; //priori prob. of the candidate word, to 5 decimal places
                top_posteriori[counter][5] = posteriori[counter][5]; //graphemic prob
                top_posteriori[counter][6] = posteriori[counter][6]; //phonemic prob.
                top_posteriori[counter][7] = posteriori[counter][7]; //posteriori prob.
            }

            return top_posteriori;
        } else {
            return posteriori;
        }
    }

}
