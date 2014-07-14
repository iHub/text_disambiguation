package Engine;

import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class NoisyChannelModel {

    private final ChannelModel chmd = new ChannelModel();

    /**
     * Calculate the Noisy Channel Model probability
     *
     * @param priori_probability
     * @param posteriori_probability
     * @return Noisy Channel Model probability
     */
    public double getNoisyChannelProbability(double priori_probability, double posteriori_probability) {
        double getNoisyChannelProbability = (posteriori_probability + priori_probability) / 2;
        return getNoisyChannelProbability;
    }

    /**
     * Calculate the Noisy Channel probabilities of candidate words, calculated
     * using the Language model and the Channel model. [0] - Candidate word. [1]
     * - Priori probability. [2] - Posteriori probability. [3] - Noisy Channel
     * probability, to 5 decimal places
     *
     * @param invalidWord
     * @return Noisy channel probability from the unigram channel model
     */
    public String[][] unigramNoisyChannelModel(String invalidWord) {
        invalidWord = invalidWord.toLowerCase();

        String[][] unigramChannelModel = chmd.unigramChannelModel(invalidWord);
        String NCM[][] = new String[unigramChannelModel.length][4]; //Word, Prior prob., Posterioir prob., NCM prob.

        for (int counter = 0; counter < NCM.length; counter++) {

            double priori_prob = Double.parseDouble(unigramChannelModel[counter][2]);
            double posteriori_prob = Double.parseDouble(unigramChannelModel[counter][5]);

            NCM[counter][0] = unigramChannelModel[counter][0]; //candidates
            NCM[counter][1] = unigramChannelModel[counter][2]; //priori prob.
            NCM[counter][2] = unigramChannelModel[counter][5]; //posteriori prob.
            NCM[counter][3] = String.valueOf(String.format("%.5f", getNoisyChannelProbability(priori_prob, posteriori_prob))); //NCM prob.
        }
        return NCM;
    }

    /**
     * Calculate the Noisy Channel probabilities of candidate words, calculated
     * using the Language model and the Channel model, limited to a maximum
     * number of candidate corrections with the highest probabilities. [0] -
     * Candidate word. [1] - Priori probability. [2] - Posteriori probability.
     * [3] - Noisy Channel probability, to 5 decimal places
     *
     * @param invalidWord
     * @param candidateGroupSize
     * @return Noisy channel probability from the unigram channel model
     */
    public String[][] unigramNoisyChannelModel(String invalidWord, int candidateGroupSize) {

        invalidWord = invalidWord.toLowerCase();

        String[][] unigramChannelModel = chmd.unigramChannelModel(invalidWord);
        String NCM[][] = new String[unigramChannelModel.length][4]; //Word, Priori prob., Posterioiri prob., NCM prob.

        for (int counter = 0; counter < NCM.length; counter++) {

            double priori_prob = Double.parseDouble(unigramChannelModel[counter][2]);
            double posteriori_prob = Double.parseDouble(unigramChannelModel[counter][5]);

            NCM[counter][0] = unigramChannelModel[counter][0]; //candidates
            NCM[counter][1] = unigramChannelModel[counter][2]; //prior prob.
            NCM[counter][2] = unigramChannelModel[counter][5]; //posterior prob.
            NCM[counter][3] = String.valueOf(String.format("%.5f", getNoisyChannelProbability(priori_prob, posteriori_prob))); //NCM prob.
        }

        //Sort NCM array based on probabilities of candidates
        Arrays.sort(NCM, new Comparator<String[]>() {
            @Override
            public int compare(final String[] probabilities, final String[] probabilitiez) {
                String probability1 = probabilities[3];
                String probability2 = probabilitiez[3];
                return probability2.compareTo(probability1);
            }
        });

        //Retrieve the top-N elements
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < NCM.length)) {

            String[][] top_NCM = new String[candidateGroupSize][4]; //Candidate word, Priori, Posteriori

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {
                top_NCM[counter][0] = NCM[counter][0];
                top_NCM[counter][1] = NCM[counter][1];
                top_NCM[counter][2] = NCM[counter][2];
                top_NCM[counter][3] = NCM[counter][3];
            }
            return top_NCM;
        } else {
            return NCM;
        }
    }

    /**
     * Calculate the Noisy Channel probabilities of candidate words, calculated
     * using the Language model and the Channel model. [0] - Candidate word. [1]
     * - Priori probability. [2] - Posteriori probability. [3] - Noisy Channel
     * probability, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @return Noisy channel probability from the bigram channel model
     */
    public String[][] bigramNoisyChannelModel(String invalidWord, String precedingWord) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();

        String[][] bigramChannelModel = chmd.bigramChannelModel(invalidWord, precedingWord);
        String NCM[][] = new String[bigramChannelModel.length][4]; //Word, Prior prob., Posterioir prob., NCM prob

        for (int counter = 0; counter < NCM.length; counter++) {

            double priori_prob = Double.parseDouble(bigramChannelModel[counter][2]);
            double posteriori_prob = Double.parseDouble(bigramChannelModel[counter][5]);

            NCM[counter][0] = bigramChannelModel[counter][0]; //candidates
            NCM[counter][1] = bigramChannelModel[counter][2]; //priori prob.
            NCM[counter][2] = bigramChannelModel[counter][5]; //posteriori prob.
            NCM[counter][3] = String.valueOf(String.format("%.5f", getNoisyChannelProbability(priori_prob, posteriori_prob))); //NCM prob.
        }
        return NCM;
    }

    /**
     * Calculate the Noisy Channel probabilities of candidate words, calculated
     * using the Language model and the Channel model, limited to a maximum
     * number of candidate corrections with the highest probabilities. [0] -
     * Candidate word. [1] - Priori probability. [2] - Posteriori probability.
     * [3] - Noisy Channel probability, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @param candidateGroupSize
     * @return Noisy channel probability from the bigram channel model
     */
    public String[][] bigramNoisyChannelModel(String invalidWord, String precedingWord, int candidateGroupSize) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();

        String[][] bigramChannelModel = chmd.bigramChannelModel(invalidWord, precedingWord);
        String NCM[][] = new String[bigramChannelModel.length][4]; //Word, Prior prob., Posterioir prob., NCM prob.

        for (int counter = 0; counter < NCM.length; counter++) {
            double priori_prob = Double.parseDouble(bigramChannelModel[counter][2]);
            double posteriori_prob = Double.parseDouble(bigramChannelModel[counter][5]);

            NCM[counter][0] = bigramChannelModel[counter][0]; //candidates
            NCM[counter][1] = bigramChannelModel[counter][2]; //prior prob.
            NCM[counter][2] = bigramChannelModel[counter][5]; //posterior prob.
            NCM[counter][3] = String.valueOf(String.format("%.5f", getNoisyChannelProbability(priori_prob, posteriori_prob))); //NCM prob.
        }

        //Sort NCM array based on probabilities of candidates
        Arrays.sort(NCM, new Comparator<String[]>() {
            @Override
            public int compare(final String[] probabilities, final String[] probabilitiez) {
                String probability1 = probabilities[3];
                String probability2 = probabilitiez[3];
                return probability2.compareTo(probability1);
            }
        });

        //Retrieve the top-N elements
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < NCM.length)) {

            String[][] top_NCM = new String[candidateGroupSize][4]; //Candidate word, Priori, Posteriori

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {

                top_NCM[counter][0] = NCM[counter][0];
                top_NCM[counter][1] = NCM[counter][1];
                top_NCM[counter][2] = NCM[counter][2];
                top_NCM[counter][3] = NCM[counter][3];
            }
            return top_NCM;
        } else {
            return NCM;
        }
    }

    /**
     * Calculate the Noisy Channel probabilities of candidate words, calculated
     * using the Language model and the Channel model. [0] - Candidate word. [1]
     * - Priori probability. [2] - Posteriori probability. [3] - Noisy Channel
     * probability, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @param precedingPrecedingWord
     * @return Noisy channel probability from the trigram channel model
     */
    public String[][] trigramNoisyChannelModel(String invalidWord, String precedingWord, String precedingPrecedingWord) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();
        precedingPrecedingWord = precedingPrecedingWord.toLowerCase();

        String[][] trigramChannelModel = chmd.trigramChannelModel(invalidWord, precedingWord, precedingPrecedingWord);
        String NCM[][] = new String[trigramChannelModel.length][4]; //Word, Prior prob., Posterioir prob., NCM prob.

        for (int counter = 0; counter < NCM.length; counter++) {

            double priori_prob = Double.parseDouble(trigramChannelModel[counter][2]);
            double posteriori_prob = Double.parseDouble(trigramChannelModel[counter][5]);

            NCM[counter][0] = trigramChannelModel[counter][0]; //candidates
            NCM[counter][1] = trigramChannelModel[counter][2]; //prior prob.
            NCM[counter][2] = trigramChannelModel[counter][5]; //posterior prob.
            NCM[counter][3] = String.valueOf(String.format("%.5f", getNoisyChannelProbability(priori_prob, posteriori_prob))); //NCM prob.
        }
        return NCM;
    }

    /**
     * Calculate the Noisy Channel probabilities of candidate words, calculated
     * using the Language model and the Channel model, limited to a maximum
     * number of candidate corrections with the highest probabilities. [0] -
     * Candidate word. [1] - Priori probability. [2] - Posteriori probability.
     * [3] - Noisy Channel probability, to 5 decimal places
     *
     * @param invalidWord
     * @param precedingWord
     * @param precedingPrecedingWord
     * @param candidateGroupSize
     * @return Noisy channel probability from the trigram channel model
     */
    public String[][] trigramNoisyChannelModel(String invalidWord, String precedingWord, String precedingPrecedingWord, int candidateGroupSize) {

        invalidWord = invalidWord.toLowerCase();
        precedingWord = precedingWord.toLowerCase();
        precedingPrecedingWord = precedingPrecedingWord.toLowerCase();

        String[][] trigramChannelModel = chmd.trigramChannelModel(invalidWord, precedingWord, precedingPrecedingWord);
        String NCM[][] = new String[trigramChannelModel.length][4]; //Word, Prior prob., Posterioir prob.

        for (int counter = 0; counter < NCM.length; counter++) {

            double priori_prob = Double.parseDouble(trigramChannelModel[counter][2]);
            double posteriori_prob = Double.parseDouble(trigramChannelModel[counter][5]);

            NCM[counter][0] = trigramChannelModel[counter][0]; //candidates
            NCM[counter][1] = trigramChannelModel[counter][2]; //prior prob.
            NCM[counter][2] = trigramChannelModel[counter][5]; //posterior prob.
            NCM[counter][3] = String.valueOf(String.format("%.5f", getNoisyChannelProbability(priori_prob, posteriori_prob))); //NCM prob.
        }

        //Sort NCM array based on probabilities of candidates
        Arrays.sort(NCM, new Comparator<String[]>() {
            @Override
            public int compare(final String[] probabilities, final String[] probabilitiez) {
                String probability1 = probabilities[3];
                String probability2 = probabilitiez[3];
                return probability2.compareTo(probability1);
            }
        });

        //Retrieve the top-N elements
        //Ensure that the number of elements > 0 < candidate group size (0 < n < group size)
        if ((candidateGroupSize > 0) && (candidateGroupSize < NCM.length)) {

            String[][] top_NCM = new String[candidateGroupSize][4]; //Candidate word, Priori, Posteriori

            //Assign top elements to selection size array
            for (int counter = 0; counter < candidateGroupSize; counter++) {

                top_NCM[counter][0] = NCM[counter][0];
                top_NCM[counter][1] = NCM[counter][1];
                top_NCM[counter][2] = NCM[counter][2];
                top_NCM[counter][3] = NCM[counter][3];
            }
            return top_NCM;
        } else {
            return NCM;
        }
    }

}
