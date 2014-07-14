package Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class Graphemic {

    private final Reader r = new Reader();

    //Swahili
    final List<String> swa_words = r.getSwahiliLexicon();
    final List<String> swa_verbs = r.getSwahiliVerbs();
    final List<String> swa_adjectives = r.getSwahiliAdjectives();

    //English
    final List<String> eng_words = r.getEnglishLexicon();
    final List<String> eng_morphemes = r.getEnglishMorphemes();
    final List<String> all_words = new ArrayList<>();

    final String swa_morph = "(ni|wa|ki|vi|i|zi|pa|mwa|mwe|a|u)(ki|li|l|me|na|ta)(ni|ji|tu|m|wa|ki|vi|i|zi|po|ku|pa)?(\\w+)";
    final String nt_swa_morph = "(ha|si)(m|wa|li|ya|ki|vi|i|zi|u|pa)?(ku|ja|ta|i)(m|wa|ki|vi|i|zi|u|ku|tu)?(\\w+)"; //vikanushi

    //ROOM FOR EXPANSION!!
    final private String[][] dict = {{"show", "onyesha", "onyeshea", "onyeshee", "onyeshwa", "onyesheana", "onyeshei", "showed"},
    {"try", "jaribu", "jaribia", "jaribie", "jaribishwa", "jaribiana", "jaribii", "tried"},
    {"cook", "pika", "pikia", "pikie", "pikiwa", "pikiana", "pikii", "cooked"},
    {"come", "kuja", "kujia", "kujie", "kujiwa", "kujiana", "kujii", "came"},
    {"read", "soma", "somea", "somee", "somewa", "someana", "somei", "read"},
    {"do", "fanya", "fanyia", "fanyie", "fanyiwa", "fanyiana", "fanyii", "did"},
    {"feel", "hisi", "hisia", "hisie", "hisiwa", "hisiana", "hisii", "felt"},
    {"spoil", "haribu", "haribia", "haribie", "haribiwa", "haribiana", "haribii", "spoilt"},
    {"wait", "ngoja", "ngojea", "ngojee", "ngojiwa", "ngojiana", "ngojii", "waited"},
    {"write", "andika", "andikia", "andikie", "andikiwa", "andikiana", "andikii", "wrote"},
    {"present", "onyesha", "onyeshea", "onyeshee", "onyeshewa", "onyesheana", "onyeshei", "presented"},
    {"sleep", "lala", "lalia", "lalie", "laliwa", "laliana", "lalii", "slept"},
    {"drink", "kunywa", "kunyia", "kunyie", "kunyiwa", "kunyiana", "kunyii", "drank"},
    {"move", "songa", "songea", "songee", "songiwa", "songiana", "songii", "moved"},
    {"smell", "nuka", "nukia", "nukie", "nukiwa", "nukiana", "nukii", "smelt"},
    {"call", "piga", "pigia", "pigie", "pigiwa", "pigiana", "pigii", "called"},
    {"open", "fungua", "fungua", "fungulie", "funguliwa", "funguliana", "fungulii", "opened"},
    {"close", "funga", "fungia", "fungie", "fungiwa", "fungiana", "fungii", "closed"},
    {"finish", "maliza", "malizia", "malizie", "maliziwa", "maliziana", "malizii", "finished"},
    {"loose", "poteza", "potezea", "potezee", "potezewa", "potezeana", "potezei", "lost"},
    {"go", "enda", "endea", "endee", "endewa", "endeana", "endei", "went"},
    {"look", "ona", "onea", "onee", "onewa", "oneana", "onei", "looked"},
    {"come", "kuja", "kujia", "kujie", "kujiwa", "kujiana", "kujii", "came"},
    {"ask", "uliza", "ulizia", "ulizie", "uliziwa", "uliziana", "ulizii", "asked"},
    {"tell", "ambia", "ambia", "ambie", "ambiwa", "ambiana", "ambii", "told"},
    {"help", "saidia", "saidia", "saidie", "saidiwa", "saidiana", "saidii", "helped"},
    {"miss", "kosa", "kosea", "kosee", "kosewa", "koseana", "kosei", "missed"},
    {"wait", "ngoja", "ngojea", "ngojee", "ngojiwa", "ngojiana", "ngojii", "waited"},
    {"text", "andika", "andikia", "andikie", "andikiwa", "andikiana", "andikii", "texted"},
    {"get", "pata", "patia", "patie", "patiwa", "patiana", "patii", "got"},
    {"send", "tuma", "tumia", "tumie", "tumiwa", "tumiana", "tumii", "sent"},
    {"remove", "ondoa", "ondolea", "ondolee", "ondolewa", "ondoleana", "ondolei", "removed"},
    {"delete", "ondoa", "ondolea", "ondolee", "ondolewa", "ondoleana", "ondolei", "deleted"},
    {"drive", "endesha", "endeshea", "endeshee", "endeshewa", "endesheana", "endeshii", "drove"},
    {"bring", "leta", "letea", "letee", "letewa", "leteana", "letii", "brought"},
    {"forget", "sahau", "sahaulia", "sahaulie", "sahauliwa", "sahauliana", "sahaulii", "forgot"},
    {"type", "andika", "andikia", "andikie", "andikiwa", "andikiana", "andikii", "typed"},
    {"look", "angalia", "angalilia", "angalilie", "angaliliwa", "angaliana", "angalii", "looked"},
    {"look", "angalia", "angalilia", "angalilie", "angaliliwa", "angaliana", "angalii", "looked"},
    {"run", "kimbia", "kimbilia", "kimbilie", "kimbiliwa", "kimbiliana", "kimbilii", "ran"},
    {"work", "fanya", "fanyia", "fanyie", "fanyiwa", "fanyiana", "fanyii", "worked"}};

    /**
     * Check if word is contained in lexicon (English & Swahili)
     *
     * @param word
     * @return If the word is contained in the dictionary, then return true
     */
    public boolean isDictionaryWord(String word) {
        word = word.toLowerCase();
        //Swahili
        all_words.addAll(swa_words);
        all_words.addAll(swa_adjectives);
        all_words.addAll(swa_verbs);

        //English
        all_words.addAll(eng_words);
        all_words.addAll(eng_morphemes);

        return all_words.contains(word);
    }

    /**
     * Check validity of Swahili word as a result of morphological inflections &
     * derivations
     *
     * @param candidate
     * @return If the word is a valid Swahili word, then return true
     */
    public boolean isValidSwahili(String candidate) {
        candidate = candidate.toLowerCase();
        StringBuilder sb;

        //Swahili Vitenzi (First perform morphological analyses then perform whole word matching)
        for (String ss : swa_verbs) {
            Matcher swa_morph_m = Pattern.compile(swa_morph).matcher(candidate);
            Matcher nt_swa_morph_m = Pattern.compile(nt_swa_morph).matcher(candidate);

            //1.    Morphological analysis
            if (swa_morph_m.matches()) {
                if (swa_morph_m.group(4).equals(ss)) {
                    return true;
                } else if ((swa_morph_m.group(4).endsWith("ia")) && (ss.endsWith("a"))) {
                    sb = new StringBuilder(ss);
                    sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "ia"); //e.g. fanyia,angukia
                    if (swa_morph_m.group(4).equalsIgnoreCase(sb.toString())) {
                        return true;
                    }
                } else if ((swa_morph_m.group(4).endsWith("iwa")) && (ss.endsWith("a"))) {
                    sb = new StringBuilder(ss);
                    sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "iwa"); //e.g. fanyiwa,angukiwa
                    if (swa_morph_m.group(4).equalsIgnoreCase(sb.toString())) {
                        return true;
                    }
                } else if ((swa_morph_m.group(4).endsWith("isha")) && (ss.endsWith("a"))) {
                    sb = new StringBuilder(ss);
                    sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "isha"); //e.g. fanyisha
                    if (swa_morph_m.group(4).equalsIgnoreCase(sb.toString())) {
                        return true;
                    }
                } else if ((swa_morph_m.group(4).endsWith("iza")) && (ss.endsWith("a"))) {
                    sb = new StringBuilder(ss);
                    sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "iza"); //e.g. fanyiza
                    if (swa_morph_m.group(4).equalsIgnoreCase(sb.toString())) {
                        return true;
                    }
                }
            } else if (nt_swa_morph_m.matches()) { //Vikanushi
                if (nt_swa_morph_m.group(5).equals(ss)) {
                    return true;
                } else if ((nt_swa_morph_m.group(5).endsWith("ia")) && (ss.endsWith("a"))) {
                    sb = new StringBuilder(ss);
                    sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "ia"); //e.g. fanyia,angukia
                    if (nt_swa_morph_m.group(5).equalsIgnoreCase(sb.toString())) {
                        return true;
                    }
                } else if ((nt_swa_morph_m.group(5).endsWith("iwa")) && (ss.endsWith("a"))) {
                    sb = new StringBuilder(ss);
                    sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "iwa"); //e.g. fanyiwa,angukiwa
                    if (nt_swa_morph_m.group(5).equalsIgnoreCase(sb.toString())) {
                        return true;
                    }
                }
            } //2.  Whole word matching
            //Ending with -a e.g. fanya,soma,laza
            else if ((candidate.endsWith("ia")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "ia"); //e.g. fanyia,angukia
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("e")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "e"); //e.g. fanye
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("iwa")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "iwa"); //e.g. fanyiwa
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("ana")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "ana"); //e.g. fanyana
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("anga")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "anga"); //e.g. fanyanga
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("ua")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "ua"); //e.g. fanyua
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("ika")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "ika"); //e.g. fanyika
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("esha")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "esha"); //e.g. somesha
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("isha")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "isha"); //e.g. fanyisha
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("iza")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "iza"); //e.g. fanyiza
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("zwa")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "zwa"); //e.g. lazwa
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("ea")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "ea"); //e.g. somea
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("eshwa")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "eshwa"); //e.g. someshwa
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("ishwa")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "ishwa"); //e.g. fanyishwa
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("eni")) && (ss.endsWith("a"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("a"), sb.lastIndexOf("a") + 1, "eni"); //e.g. fanyeni
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            }
            //Ending with -i e.g. rudi,zirai
            if ((candidate.endsWith("ia")) && (ss.endsWith("i"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("i"), sb.lastIndexOf("i") + 1, "ia"); //e.g. rudia
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("iwa")) && (ss.endsWith("i"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("i"), sb.lastIndexOf("i") + 1, "iwa"); //e.g. rudiwa
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("ana")) && (ss.endsWith("i"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("i"), sb.lastIndexOf("i") + 1, "iana"); //e.g. rudiana
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("anga")) && (ss.endsWith("i"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("i"), sb.lastIndexOf("i") + 1, "ianga"); //e.g. rudianga
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("ishwa")) && (ss.endsWith("i"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("i"), sb.lastIndexOf("i") + 1, "ishwa"); //e.g. rudishwa
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("eni")) && (ss.endsWith("i"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("i"), sb.lastIndexOf("i") + 1, "ieni"); //e.g. rudieni
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("ika")) && (ss.endsWith("i"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("i"), sb.lastIndexOf("i") + 1, "ika"); //e.g. rudika
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            } else if ((candidate.endsWith("isha")) && (ss.endsWith("i"))) {
                sb = new StringBuilder(ss);
                sb.replace(sb.lastIndexOf("i"), sb.lastIndexOf("i") + 1, "isha"); //e.g. rudisha
                if (candidate.equalsIgnoreCase(sb.toString())) {
                    return true;
                }
            }
        }
        //Swahili Vielezi
        for (String ss : swa_adjectives) {
            if (candidate.contains(ss)) {
                if ((candidate.equalsIgnoreCase("m".concat(ss))) && (!ss.startsWith("e"))) {          //ngeli M-WA
                    return true;
                } else if ((candidate.equalsIgnoreCase("wa".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli M-WA
                    return true;
                } else if ((candidate.equalsIgnoreCase("ki".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli KI-VI
                    return true;
                } else if ((candidate.equalsIgnoreCase("vi".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli KI-VI
                    return true;
                } else if ((candidate.equalsIgnoreCase("mi".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli M-MI
                    return true;
                } else if ((candidate.equalsIgnoreCase("ma".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli JI-MA
                    return true;
                } else if ((candidate.equalsIgnoreCase("pa".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli PA-PA
                    return true;
                } else if ((candidate.equalsIgnoreCase("ku".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli KU-KU
                    return true;
                } else if ((candidate.equalsIgnoreCase("ku".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli KU-KU
                    return true;
                } else if ((candidate.equalsIgnoreCase("z".concat(ss))) && (!ss.startsWith("e"))) {  //ngeli I-ZI
                    return true;
                } else if ((ss.startsWith("e")) && (candidate.equalsIgnoreCase("mw".concat(ss)))) {  //ngeli M-WA
                    return true;
                } else if ((ss.startsWith("e")) && (candidate.equalsIgnoreCase("w".concat(ss)))) {   //ngeli M-WA
                    return true;
                } else if ((ss.startsWith("e")) && (candidate.equalsIgnoreCase("ch".concat(ss)))) {   //ngeli KI-VI
                    return true;
                } else if ((ss.startsWith("e")) && (candidate.equalsIgnoreCase("p".concat(ss)))) {    //ngeli PA-PA
                    return true;
                } else if ((ss.startsWith("e")) && (candidate.equalsIgnoreCase("kw".concat(ss)))) {   //ngeli KU-KU
                    return true;
                } else if ((ss.startsWith("e")) && (candidate.equalsIgnoreCase("ny".concat(ss)))) {   //ngeli I-ZI
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Processes English-Swahili word mixing/concatenations e.g. hazikuspoil,
     * hawatucookia, zitago
     *
     * @param word
     * @return English/Swahili word in its correct spelling
     */
    public String processWordMixing(String word) {
        word = word.toLowerCase();

        final String swa_eng_mix = "(ni|wa|ki|vi|i|zi|pa|mwa|mwe|a|u)(ki|li|l|me|na|ta)?(ni|ji|tu|m|wa|ki|vi|i|zi|po|ku|pa)?(\\w+)";
        final String nt_swa_eng_mix = "(ha|si)(m|wa|tu|ki|vi|i|zi|ku|pa)?(ku|ja|ta|i)?(m|wa|ki|vi|i|zi|u|ku|tu)?(\\w+)"; //handles vikanushi

        final String swa_eng_ifc_present = "(\\w+[^ing|in'|in])(ing|in'|in)"; //handles present progressive tense
        final String swa_eng_ifc_past = "(\\w+[^d])(d)"; //handles simple past tense

        Matcher swa_eng_mix_m = Pattern.compile(swa_eng_mix).matcher(word);
        Matcher nt_swa_eng_mix_m = Pattern.compile(nt_swa_eng_mix).matcher(word);
        Matcher swa_eng_ifc_m1 = Pattern.compile(swa_eng_ifc_present).matcher(word);
        Matcher swa_eng_ifc_m2 = Pattern.compile(swa_eng_ifc_past).matcher(word);

        //Negation of English-Swahili mixing e.g. hazikuspoil, hawatatucookia
        if (nt_swa_eng_mix_m.matches()) {
            for (String[] dictionary : dict) {
                if (word.contains(dictionary[0])) {
                    if (word.endsWith("ia")) {
                        word = (word.replace(nt_swa_eng_mix_m.group(5), dictionary[2])); //-ia
                        break;
                    } else if (word.endsWith("ii")) {
                        word = (word.replace(nt_swa_eng_mix_m.group(5), dictionary[6])); //-ii
                        break;
                    } else if (word.endsWith("iwa")) {
                        word = (word.replace(nt_swa_eng_mix_m.group(5), dictionary[4])); //-iwa
                        break;
                    } else if (word.endsWith("ana")) {
                        word = (word.replace(nt_swa_eng_mix_m.group(5), dictionary[5])); //-ana
                        break;
                    } else {
                        word = (word.replace(nt_swa_eng_mix_m.group(5), dictionary[1])); //root
                        break;
                    }
                }
            }
        } //English-Swahili mixing e.g. zilispoil, wanatucookia
        else if (swa_eng_mix_m.matches()) {
            for (String[] dictionary : dict) {
                if (word.contains(dictionary[0])) {
                    if (word.endsWith("ia")) {
                        word = (word.replace(swa_eng_mix_m.group(4), dictionary[2])); //-ia
                        break;
                    } else if (word.endsWith("ie")) {
                        word = (word.replace(swa_eng_mix_m.group(4), dictionary[3])); //-ie
                        break;
                    } else if (word.endsWith("iwa")) {
                        word = (word.replace(swa_eng_mix_m.group(4), dictionary[4])); //-iwa
                        break;
                    } else if (word.endsWith("ana")) {
                        word = (word.replace(swa_eng_mix_m.group(4), dictionary[5])); //-ana
                        break;
                    } else {
                        word = (word.replace(swa_eng_mix_m.group(4), dictionary[1])); //root
                        break;
                    }
                }
            }
        }

        //Inflections & Derivations from both Languages e.g. somaing
        if (swa_eng_ifc_m1.matches()) {
            for (String[] dictionary : dict) {
                if (swa_eng_ifc_m1.group(1).equals(dictionary[1])) {
                    word = dictionary[0].concat("ing"); //present continuous tense
                    break;
                }
            }
        }
        if (swa_eng_ifc_m2.matches()) {
            for (String[] dictionary : dict) {
                if (swa_eng_ifc_m2.group(1).equals(dictionary[1])) {
                    word = dictionary[7]; //simple past tense
                    break;
                }
            }
        }

        return word;
    }

    /**
     * Processes invalid Swahili words (Typographic errors)
     *
     * @param word
     * @return Swahili word in its correct spelling
     */
    public String processSwahiliTypos(String word) {
        word = word.toLowerCase();

        Matcher swa_morph_m = Pattern.compile(swa_morph).matcher(word);
        Matcher nt_swa_morph_m = Pattern.compile(nt_swa_morph).matcher(word);

        String return_word = word;

        //Spell-correct the invalid swahili word
        if (swa_morph_m.matches()) {
            for (String ss : swa_verbs) {
                if (swa_morph_m.group(4).equals(ss)) {
                    return return_word;
                } else {
                    int lcs_length = StringCompare.getLengthLCS(swa_morph_m.group(4), ss);
                    if (lcs_length == swa_morph_m.group(4).length() && ss.charAt(0) == swa_morph_m.group(4).charAt(0) && StringCompare.getLevenshtienDistance(swa_morph_m.group(4), ss) <= 2) {
                        word = word.replace(swa_morph_m.group(4), ss);
                        break;
                    }
                }
            }
        } else if (nt_swa_morph_m.matches()) {
            for (String ss : swa_verbs) {
                if (nt_swa_morph_m.group(5).equals(ss)) {
                    return return_word;
                } else {
                    int lcs_length = StringCompare.getLengthLCS(nt_swa_morph_m.group(5), ss);
                    if (lcs_length == nt_swa_morph_m.group(5).length() && ss.charAt(0) == nt_swa_morph_m.group(5).charAt(0) && StringCompare.getLevenshtienDistance(nt_swa_morph_m.group(5), ss) <= 2) {
                        word = word.replace(nt_swa_morph_m.group(5), ss);
                        break;
                    }
                }
            }
        }
        return word;
    }

    /**
     * Gets the probability that two English strings are a match by evaluating
     * the distortion of spellings
     *
     * @param originalString
     * @param anotherString
     * @return Probability that two English strings are a match
     */
    public static double getProbabilityEnglishMatches(String originalString, String anotherString) {

        double numerator = (double) StringCompare.getLengthLCS(originalString, anotherString); //LCS between two words

        int new_word_length = anotherString.length(); //Characters in a candidate correction
        int org_word_length = originalString.length(); //Characters in original word

        double denominator = (double) Math.max(new_word_length, org_word_length); //Find the longer of the strings
        double lcsratio = numerator / denominator;
        double levenshtien_metric = StringCompare.getLevenshtienDistance(getConsonantWriting(originalString), getConsonantWriting(anotherString));
        if (levenshtien_metric == 0.0) {
            levenshtien_metric = lcsratio;
        }
        double result = (lcsratio) / levenshtien_metric; //graphemic prob.
        return result;
    }

    /**
     * Gets the probability that two Swahili strings are a match by evaluating
     * the distortion of spellings
     *
     * @param originalString
     * @param anotherString
     * @return Probability that two English strings are a match
     */
    public static double getProbabilitySwahiliMatches(String originalString, String anotherString) {
        double numerator = (double) StringCompare.getLengthLCS(originalString, anotherString); //LCS between invalid & valid words

        int new_word_length = anotherString.length(); //Characters in a candidate correction
        int org_word_length = originalString.length(); //Characters in original word

        double denominator = (double) Math.max(new_word_length, org_word_length); //Find the longest of the strings
        double lcsratio = numerator / denominator;
        double levenshtien_metric = StringCompare.getLevenshtienDistance(originalString, anotherString);
        if (levenshtien_metric == 0.0) {
            levenshtien_metric = lcsratio;
        }
        double result = (lcsratio) / levenshtien_metric; //graphemic prob.
        return result;
    }

    /**
     * Gets the consonant writing by stripping out vowels from the input string
     *
     * @param word
     * @return String with all vowels stripped out
     */
    public static String getConsonantWriting(String word) {

        word = word.toLowerCase();
        StringBuilder sb = new StringBuilder(word);
        for (int a = 0; a < sb.length(); a++) {
            if (sb.charAt(a) == 'a') {
                sb.deleteCharAt(a);
                a--;
            } else if (sb.charAt(a) == 'e') {
                sb.deleteCharAt(a);
                a--;
            } else if (sb.charAt(a) == 'i') {
                sb.deleteCharAt(a);
                a--;
            } else if (sb.charAt(a) == 'o') {
                sb.deleteCharAt(a);
                a--;
            } else if (sb.charAt(a) == 'u') {
                sb.deleteCharAt(a);
                a--;
            }
        }
        return sb.toString(); //Return consonant-word
    }
}
