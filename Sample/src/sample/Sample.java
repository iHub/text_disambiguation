package sample;

import Engine.Graphemic;
import Engine.NoisyChannelModel;
import Engine.Phonetic;
import Engine.Tokenizer;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class Sample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //EXAMPLE TO TEST HOW THE SYSTEM WOULD PROCESS THESE STRINGS

        final Graphemic graphemic = new Graphemic();
        final Phonetic phonetic = new Phonetic();
        final NoisyChannelModel ncmd = new NoisyChannelModel();

        final String pure_words = "[a-zA-Z]{2,}"; //words of length > 1
        final String word_numbers = "[a-zA-Z]+[0-9]{1}[a-zA-Z]*"; //word number combinations e.g. wa2, any1
        final String number_words = "[a-zA-Z]*[0-9]{1}[a-zA-Z]+"; //word number combinations e.g. 2dy

        String sms = "hey, wntd 2 wish yu hapy nw year!!";

        //Step 1: Tokenize input strings
        String[] tokens = Tokenizer.getTokens(sms);
        String[] correct = new String[tokens.length];

        //Traverse all the tokens
        for (int counter = 0; counter < tokens.length; counter++) {

            //Step 2: Determine the kind of words that will be processed (pure words, word_numbers, number_words)
            if (tokens[counter].matches(pure_words) || tokens[counter].matches(word_numbers) || tokens[counter].matches(number_words)) {

                //Step 3: If word(English/Swahili) is valid, then skip over
                if (graphemic.isDictionaryWord(tokens[counter]) || graphemic.isValidSwahili(tokens[counter])) {
                    correct[counter] = tokens[counter];
                } else {
                    //Step 4: Preprocess words
                    //a) process Swahili Phonetics
                    String preprocessed = phonetic.processSwahiliPhonetics(tokens[counter]);
                    //b) process any word mixing
                    String preprocessMix = graphemic.processWordMixing(preprocessed);
                    //c) process typos
                    String preprocessTypos = graphemic.processSwahiliTypos(preprocessed);

                    //Step 5: Set fixes
                    if (!preprocessMix.equals(preprocessed)) {
                        correct[counter] = preprocessMix;
                    } else if (!preprocessTypos.equals(preprocessed)) {
                        correct[counter] = preprocessTypos;
                    } else {
                        //Using the NCM, determine the best possible candidate correction
                        if (ncmd.unigramNoisyChannelModel(tokens[counter], 1).length == 0) {
                            correct[counter] = tokens[counter]; //no match
                        } else {
                            correct[counter] = ncmd.unigramNoisyChannelModel(tokens[counter], 1)[0][0]; //best match
                        }
                    }
                }
            } else {
                correct[counter] = tokens[counter];
            }
        }

        //Display corrected string
        for (String str : correct) {
            System.out.printf("%s", str);
        }
        System.out.println();
    }

}
