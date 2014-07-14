package Engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Stephen Mwega (smwega@gmail.com)
 */
public class Phonetic {

    //Section (A) Number Homophones e.g. gr8, 7teen, sum1
    //
    /**
     * Process numeric homophones for complex English words
     *
     * @param word
     * @return English word with numbers converted to their phonemic
     * representations
     */
    public String processEnglishPhonetics(String word) {
        StringBuilder sb = new StringBuilder(word);

        if ((word.matches("[a-zA-Z]+[1246789]?[a-zA-Z]*")) || (word.matches("[1246789]?[a-zA-Z]+"))) { //||(word.matches("[a-zA-Z]+[1246789]?[a-zA-Z]*")))

            //Number Homophones e.g. gr8, 7teen, sum1
            if (sb.indexOf("1") >= 0) {
                sb.replace(sb.indexOf("1"), sb.indexOf("1") + 1, "one");
            } else if (sb.indexOf("2") >= 0) {
                sb.replace(sb.indexOf("2"), sb.indexOf("2") + 1, "too");
            } else if (sb.indexOf("4") >= 0) {
                sb.replace(sb.indexOf("4"), sb.indexOf("4") + 1, "for");
            } else if (sb.indexOf("6") >= 0) {
                sb.replace(sb.indexOf("6"), sb.indexOf("6") + 1, "six");
            } else if (sb.indexOf("7") >= 0) {
                sb.replace(sb.indexOf("7"), sb.indexOf("7") + 1, "seven");
            } else if (sb.indexOf("8") >= 0) {
                sb.replace(sb.indexOf("8"), sb.indexOf("8") + 1, "ate");
            } else if (sb.indexOf("9") >= 0) {
                sb.replace(sb.indexOf("9"), sb.indexOf("9") + 1, "nin");
            }
        }

        return sb.toString();
    }

    /**
     * Process numeric homophones for complex Swahili words
     *
     * @param word
     * @return Swahili word with numbers converted to their phonemic
     * representations
     */
    public String processSwahiliPhonetics(String word) {
        word = word.toLowerCase();

        final String swa_regex = "(m|wa|ki|vi|i|zi|pa|mwa|a|u|ni)(ki|li|l|me|na|ta)?(2)(\\w+)";
        final String nt_swa_regex = "(ha|si)(m|wa|tu|ki|vi|i|zi|ku|pa)?(ku|ja|ta|i)?(2)(\\w+)";

        Matcher swa_eng_mix_m = Pattern.compile(swa_regex).matcher(word);
        Matcher nt_swa_eng_mix_m = Pattern.compile(nt_swa_regex).matcher(word);

        if (swa_eng_mix_m.matches()) {
            if (word.contains("2")) {
                word = word.replace("2", "tu"); //Handle numeric homophone 2 e.g. wali2sumbua
            }
        } else if (nt_swa_eng_mix_m.matches()) {
            if (word.contains("2")) {
                word = word.replace("2", "tu"); //Handle numeric homophone 2 e.g. hawaku2sumbua
            }
        }
        return word;
    }

    //Section (B) Consonants, Vowels, Digraphs and Diphthongs
    //English consonants: b,c,d,f,g,h,j,k,l,m,n,p,r,s,t,v,w,y,z
    //English vowels: a,e,i,o,u
    //English semi-vowels: h,w,y
    //English digraphs: ch,sh,th,wh,ph,gh,gn,kn,wr,mb,lf,lk,tl,ck,ng,tch,lm,rd,ll (may not be complete!)
    //English diphthongs: ae,ai,au,aw,ay,ea,ee,ei,eu,ey,ew,ia,ie,oa,oi,oy,oe,ou,ow,oo,ue,ui,uy (may not be complete!)
    //Swahili consonants: b,d,f,g,h,j,k,l,m,n,p,r,s,t,v,w,y,z
    //Swahili vowels: a,e,i,o,u
    //Swahili semi-vowels: w,y
    //Swahili digraphs: ch,dh,gh,kh,ng,ng',ny,sh,th
    //
    /**
     * Given an English word, returns the Phonemic(sound) representation of that
     * word
     *
     * @param grapheme
     * @return Phonemic(sound) representation of the English word
     */
    public String getEnglishPhoneme(String grapheme) {
        StringBuilder sb = new StringBuilder(grapheme);

        //VOWEL-LETTER COMBINATIONS
        //case 'alk'
        if (sb.indexOf("alk") > 0) {
            sb.replace(sb.indexOf("alk"), sb.indexOf("alk") + 3, "ok"); //<ok> sound e.g. chalk,talk
        }

        //case 'olk'
        if (sb.indexOf("olk") > 0) {
            sb.replace(sb.indexOf("olk"), sb.indexOf("olk") + 3, "ok"); //<ok> sound e.g. yolk
        }

        //case '-eer':
        if (sb.indexOf("eer") > 0) {
            sb.replace(sb.indexOf("eer"), sb.indexOf("eer") + 3, "ia"); //<ia> sound e.g. steer,beer,cheer
        }

        //case 'hon-':
        if ((sb.length() > 3) && (sb.indexOf("hon") == 0)) {
            sb.deleteCharAt(0); //<o> sound e.g. honor,honest
        }

        //case '-igh':
        if ((sb.length() > 3) && (sb.indexOf("igh") > 0)) {
            sb.replace(sb.indexOf("igh"), sb.indexOf("igh") + 3, "y"); //<ai> sound e.g. high,sigh
        }

        //case '-ous':
        if ((sb.length() >= 4) && (sb.indexOf("ous") > 0)) {
            sb.replace(sb.indexOf("ous"), sb.indexOf("ous") + 2, "a"); //replace 'ous' at the ending with 'a' e.g. dubious
        }
        //case 1: 'tion':
        if ((sb.length() > 4) && (sb.indexOf("tion") > 0)) {
            sb.replace(sb.indexOf("tion"), sb.indexOf("tion") + 4, "shen"); //<shen> sound e.g. nation
        }
        //case 2: 'sion':
        if ((sb.length() > 4) && (sb.indexOf("sion") > 0)) {
            sb.replace(sb.indexOf("sion"), sb.indexOf("sion") + 4, "shen"); //<shen> sound e.g. television
        }

        //case 'ure'
        //case 1 '-ture':
        if ((sb.length() > 4) && (sb.indexOf("tur") > 0) && (isVowel(sb.charAt(sb.indexOf("tur") + 3)))) {
            sb.replace(sb.indexOf("tur"), sb.indexOf("tur") + 2, "cha"); //<cha> sound e.g. culture,future,cultural,futuristic
        } //case 2: '-tur':
        else if ((sb.length() > 3) && (sb.indexOf("ure") > 0)) {
            sb.replace(sb.indexOf("ure"), sb.indexOf("ure") + 3, "ua"); //<ua> sound e.g. manure,sure,cure,pure,lure
        }

        //PHONOGRAMS
        //case 'ar':
        if (sb.indexOf("ar") >= 0) {
            if (sb.lastIndexOf("ar") == sb.length() - 2) {
                sb.replace(sb.lastIndexOf("ar"), sb.lastIndexOf("ar") + 2, "a"); //<a> sound e.g. car,far.
            } else if (isConsonant(sb.charAt(sb.indexOf("ar") + 2))) {
                sb.deleteCharAt(sb.indexOf("ar") + 1); //<a> sound e.g. ark,card
            }
        }

        //case 'er':
        if (sb.indexOf("er") >= 0) {
            if (sb.lastIndexOf("er") == sb.length() - 2) {
                sb.replace(sb.lastIndexOf("er"), sb.lastIndexOf("er") + 2, "a"); //<a> sound e.g. after,clever
            } else if (isConsonant(sb.charAt(sb.indexOf("er") + 2))) {
                sb.replace(sb.indexOf("er"), sb.indexOf("er") + 2, "a"); //<a> sound e.g. herd,jerk
            }
        }

        //case 'ir':
        if (sb.indexOf("ir") >= 0) {
            if (sb.lastIndexOf("ir") == sb.length() - 2) {
                sb.replace(sb.lastIndexOf("ir"), sb.lastIndexOf("ir") + 2, "a"); //<a> sound e.g. fir,stir
            } else if (isConsonant(sb.charAt(sb.indexOf("ir") + 2))) {
                sb.replace(sb.indexOf("ir"), sb.indexOf("ir") + 2, "a"); //<a> sound e.g. irk,swirl
            }
        }

        //case 'or':
        if (sb.indexOf("or") >= 0) {
            if (sb.indexOf("for") >= 0) {
                sb.deleteCharAt(sb.indexOf("for") + 2); //<fo> sound e.g. for,before
            } else if (sb.lastIndexOf("or") == sb.length() - 2) {
                sb.replace(sb.lastIndexOf("or"), sb.lastIndexOf("or") + 2, "a"); //<o> sound e.g. windsor,cursor
            } else if (isConsonant(sb.charAt(sb.indexOf("or") + 2))) {
                sb.replace(sb.indexOf("or"), sb.indexOf("or") + 2, "o"); //<o> sound e.g. fork,horn,dormitory
            }
        }

        //case 'ur':
        if (sb.indexOf("ur") >= 0) {
            if (sb.lastIndexOf("ur") == sb.length() - 2) {
                sb.replace(sb.lastIndexOf("ur"), sb.lastIndexOf("ur") + 2, "a"); //<a> sound e.g. fur,concur
            } else if (isConsonant(sb.charAt(sb.indexOf("ur") + 2))) {
                sb.replace(sb.indexOf("ur"), sb.indexOf("ur") + 2, "a"); //<a> sound e.g. burn,church
            }
        }

        //DIGRAPHS & DIPHTHONGS
        //case 'ae':
        if (sb.indexOf("ae") >= 0) {
            sb.replace(sb.indexOf("ae"), sb.indexOf("ae") + 2, "e"); //<e> sound e.g. aerospace, formulae
        } else if ((sb.indexOf("a") >= 0) && (sb.indexOf("e", sb.indexOf("a")) == sb.indexOf("a") + 2)) {
            sb.replace(sb.indexOf("a"), sb.indexOf("a") + 1, "ei"); //<ei> sound e.g. mate
        } //case 'ai': case 'air':
        else if ((sb.indexOf("a") >= 0) && (sb.indexOf("i", sb.indexOf("a")) == sb.indexOf("a") + 1) && (sb.indexOf("r", sb.indexOf("i")) == sb.indexOf("i") + 1)) {
            sb.replace(sb.indexOf("a"), sb.indexOf("a") + 3, "ea"); //<ea> sound e.g. flair
        } else if ((sb.indexOf("a") > 0) && (sb.indexOf("i", sb.indexOf("a")) == sb.indexOf("a") + 1)) {
            sb.replace(sb.indexOf("a"), sb.indexOf("a") + 2, "ei"); //<ei> sound e.g. wait
        } //case 'au': exception=laugh
        else if (sb.indexOf("au") >= 0) {
            sb.replace(sb.indexOf("au"), sb.indexOf("au") + 2, "o"); //<o> sound e.g. audit,dinosaur
        } //case 'aw':
        else if (sb.indexOf("aw") > 0) {
            sb.replace(sb.indexOf("aw"), sb.indexOf("aw") + 2, "o"); //<o> sound e.g. claw,saw
        } //case 'ay':
        else if (sb.indexOf("ay") > 0) {
            sb.replace(sb.indexOf("ay"), sb.indexOf("ay") + 2, "ei"); //<ei> sound e.g. tray,clay
        } //case 'ea': exception=great,wear
        else if (sb.indexOf("ea") >= 0) {
            sb.replace(sb.indexOf("ea"), sb.indexOf("ea") + 2, "i"); //<i> sound e.g. eat,eavesdrop
        } //case 'e..e':
        else if ((sb.indexOf("e") >= 0) && (sb.indexOf("r", sb.indexOf("e")) == sb.indexOf("e") + 1) && (sb.indexOf("e", sb.indexOf("r")) == sb.indexOf("r") + 1)) {
            sb.replace(sb.indexOf("e"), sb.indexOf("e") + 3, "ia"); //<ia> sound e.g. here
        } else if (sb.indexOf("ee") >= 0) {
            sb.replace(sb.indexOf("ee"), sb.indexOf("ee") + 2, "i"); //<i> sound e.g. eel,feel
        } //case 'ei':
        else if (sb.indexOf("ei") > 0) {
            sb.replace(sb.indexOf("ei"), sb.indexOf("ei") + 2, "i"); //<i> sound e.g. receive
        } //case 'ey':
        else if (sb.indexOf("ey") > 0) {
            sb.replace(sb.indexOf("ey"), sb.indexOf("ey") + 2, "ei"); //<ei> sound e.g hey,they
        }

        //case 'i..e':
        if (sb.indexOf("ier") > 0) {
            sb.replace(sb.indexOf("ier"), sb.indexOf("ier") + 3, "ia"); //<ia> sound e.g. cavalier,barrier
        } else if ((sb.indexOf("i") > 0) && (sb.indexOf("i") != sb.length() - 1) && (isConsonant(sb.charAt(sb.indexOf("i") + 1))) && (sb.indexOf("e", sb.indexOf("i")) == sb.indexOf("i") + 2)) {
            sb.replace(sb.indexOf("i"), sb.indexOf("i") + 1, "y"); //<ai> sound e.g. bike,crime
        } else if (sb.indexOf("ie") > 0) {
            sb.replace(sb.indexOf("ie"), sb.indexOf("ie") + 2, "i"); //<i> sound e.g. thief
        } //case 'oa':
        else if (sb.indexOf("oa") >= 0) {
            sb.replace(sb.indexOf("oa"), sb.indexOf("oa") + 2, "o"); //<o> sound e.g. goat,float
        } //case 'oe': exception=canoe,bioengineer,doer
        else if (sb.indexOf("oe") > 0) {
            sb.replace(sb.indexOf("oe"), sb.indexOf("oe") + 2, "o"); //<o> sound e.g. hoe
        } //case 'oo': exception=poor
        else if (sb.indexOf("oo") >= 0) {
            sb.replace(sb.indexOf("oo"), sb.indexOf("oo") + 2, "u"); //<u> sound e.g. pool,fool,stool,look
        } //case 'ou':
        else if (sb.indexOf("ough") == 0) {
            sb.replace(sb.indexOf("ough"), sb.indexOf("ough") + 4, "ot"); //<o> sound  e.g. ought
        } else if ((sb.indexOf("ough") == 3) && (sb.indexOf("th") == 0)) {
            sb.replace(sb.indexOf("ough"), sb.indexOf("ough") + 4, "u"); //<u> sound  e.g. through
        } else if ((sb.indexOf("ough") == 2) && (sb.indexOf("th") == 0)) {
            sb.replace(sb.indexOf("ough"), sb.indexOf("ough") + 4, "o"); //<o> sound  e.g. thought
        } else if ((sb.indexOf("ough") > 0) && ((sb.indexOf("r") == sb.indexOf("ough") - 1) || (sb.indexOf("t") == sb.indexOf("ough") - 1))) {
            sb.replace(sb.indexOf("ou"), sb.indexOf("ou") + 2, "a"); //<a> sound  e.g. rough, tough
        } else if ((sb.indexOf("ough") > 0) && ((sb.indexOf("c") == sb.indexOf("ough") - 1) || (sb.indexOf("d") == sb.indexOf("ough") - 1))) {
            sb.replace(sb.indexOf("ou"), sb.indexOf("ou") + 2, "o"); //<o> sound  e.g. cough, dough
        } else if (sb.indexOf("ou") > 0) {
            sb.replace(sb.indexOf("ou"), sb.indexOf("ou") + 2, "u"); //<u> sound  e.g. ghoul
        } //case 'ow': exception=sow
        else if (sb.indexOf("ow") >= 0) {
            if (sb.indexOf("ow") >= 2) {
                sb.replace(sb.indexOf("ow"), sb.indexOf("ow") + 2, "o"); //<o> sound e.g. throw,pillow,know
            } else {
                sb.replace(sb.indexOf("ow"), sb.indexOf("ow") + 2, "ao"); //<ao> sound e.g. owl,how,now,cow
            }
        } //case 'oy'
        else if (sb.indexOf("oy") >= 0) {
            sb.replace(sb.indexOf("oy"), sb.indexOf("oy") + 2, "oi"); //<oi> sound e.g. boy,toy,oyster
        } //case 'ue': swa=uende
        else if (sb.indexOf("ue") > 0) {
            sb.replace(sb.indexOf("ue"), sb.indexOf("ue") + 2, "u"); //<u> sound e.g. sue,queue
        } //case 'ui':
        else if (sb.indexOf("ui") > 0) {
            sb.replace(sb.indexOf("ui"), sb.indexOf("ui") + 2, "u"); //<u> sound e.g. suit,fruit
        }

        /*
         * CONSONANT DIGRAPHS
         */
        //case 'ck':
        if (sb.indexOf("ck") > 0) {
            sb.replace(sb.indexOf("ck"), sb.indexOf("ck") + 2, "k"); //<k> sound e.g. lick,tickle
        }

        //case 'dg':
        if (sb.indexOf("dg") > 0) {
            sb.replace(sb.indexOf("dg"), sb.indexOf("dg") + 2, "j"); //<j> sound e.g. edge
        }

        //case 'ex':
        if ((sb.indexOf("ex") == 0)) {
            sb.deleteCharAt(0); //<x> sound e.g. exit,excel
        }

        //case 'gh':
        if (sb.length() > 2) {
            if ((sb.indexOf("gh") >= 0) && (sb.indexOf("gh") < sb.length() - 2)) {
                sb.replace(sb.indexOf("gh"), sb.indexOf("gh") + 2, "g"); //<g> sound e.g. ghost,ghetto,lugha
            } else if (sb.indexOf("gh") >= sb.length() - 2) {
                sb.replace(sb.indexOf("gh"), sb.indexOf("gh") + 2, "f"); //<f> sound e.g. laugh,cough
            }
        }

        //case 'gn':
        if (sb.indexOf("gn") == 0) {
            sb.replace(sb.indexOf("gn"), sb.indexOf("gn") + 2, "n"); //<n> sound e.g. gnome
        }

        /* Not sure in Swahili e.g. kengele
         //case 'ge':
         if(sb.indexOf("ge") >= 0) {
         sb.replace(sb.indexOf("ge"), sb.indexOf("ge")+1, "je"); //<je> sound replacement e.g. gender,general
         }//end if

         //case 'gi':
         if(sb.indexOf("gi") >= 0) {
         sb.replace(sb.indexOf("gi"), sb.indexOf("gi")+1, "j"); //<ji> sound replacement e.g. gigantic,gist
         }//end if

         //case 'gy':
         if(sb.indexOf("gy") >= 0) {
         sb.replace(sb.indexOf("gy"), sb.indexOf("gy")+1, "j"); //<jy> sound replacement e.g. gym,edgy
         }//end if
         *
         */
        //case 'kn':
        if (sb.indexOf("kn") == 0) {
            sb.deleteCharAt(sb.indexOf("kn")); //<n> sound e.g. know
        }

        //case 'mb':
        if ((sb.length() > 2) && (sb.indexOf("mb") == sb.length() - 2)) {
            sb.deleteCharAt(sb.indexOf("mb") + 1); //<m> sound e.g.comb,lamb
        }

        //case 'mn':
        if (sb.length() > 2) {
            if (sb.indexOf("mn") == sb.length() - 2) {
                sb.replace(sb.lastIndexOf("mn"), sb.lastIndexOf("mn") + 2, "m"); //<m> sound e.g. column
            }
        }

        //case 'ph':
        if ((sb.length() > 2) && (sb.indexOf("ph") == 0)) {
            sb.replace(sb.indexOf("ph"), sb.indexOf("ph") + 2, "f"); //<f> sound e.g. phone,phenomenon
        }

        //case 'pn':
        if ((sb.length() > 2) && (sb.indexOf("pn") == 0)) {
            sb.replace(sb.indexOf("pn"), sb.indexOf("pn") + 2, "n"); //<n> sound e.g. pneumonia
        }

        //case 'ps':
        if ((sb.length() > 2) && (sb.indexOf("ps") == 0)) {
            sb.deleteCharAt(sb.indexOf("ps")); //<s> sound e.g. psychology
        }

        //case 'rh':
        if ((sb.length() > 2) && (sb.indexOf("rh") == 0)) {
            sb.replace(sb.indexOf("rh"), sb.indexOf("rh") + 2, "r"); //<r> sound e.g. rhino,rhetorical
        }

        //case 'tch':
        if ((sb.length() >= 3) && (sb.indexOf("tch") > 0)) {
            sb.deleteCharAt(sb.indexOf("tch")); //<ch> sound e.g. catch,fetch
        }

        //case 'wr':
        if ((sb.length() > 2) && (sb.indexOf("wr") == 0)) {
            sb.deleteCharAt(sb.indexOf("wr")); //<r> sound e.g. write
        }

        //case 'wh':
        if ((sb.indexOf("wha") == 0) || (sb.indexOf("whe") == 0) || (sb.indexOf("whi") == 0) || (sb.indexOf("why") == 0)) {
            sb.deleteCharAt(1); //<w> sound e.g. what,when,whale,whistle,whip
        } else if (sb.indexOf("who") == 0) {
            sb.deleteCharAt(0); //<h> sound e.g. whole,who,whom
        }

        //case '-h':
        if ((sb.length() > 2) && (sb.lastIndexOf("h") == sb.length() - 1)) {
            if ((sb.indexOf("ah") == sb.length() - 2) && (isConsonant(sb.charAt(sb.lastIndexOf("ah") - 1)))) {
                sb.deleteCharAt(sb.lastIndexOf("ah") + 1); //<a> sound e.g. tempah
            } else if ((sb.indexOf("eh") == sb.length() - 2) && (isConsonant(sb.charAt(sb.lastIndexOf("eh") - 1)))) {
                sb.deleteCharAt(sb.lastIndexOf("eh") + 1); //<e> sound e.g. tempeh
            } else if ((sb.indexOf("ih") == sb.length() - 2) && (isConsonant(sb.charAt(sb.lastIndexOf("ih") - 1)))) {
                sb.deleteCharAt(sb.lastIndexOf("ih") + 1); //<i> sound e.g. tempih
            } else if ((sb.indexOf("oh") == sb.length() - 2) && (isConsonant(sb.charAt(sb.lastIndexOf("oh") - 1)))) {
                sb.deleteCharAt(sb.lastIndexOf("oh") + 1); //<o> sound e.g. tempoh
            } else if ((sb.indexOf("uh") == sb.length() - 2) && (isConsonant(sb.charAt(sb.lastIndexOf("uh") - 1)))) {
                sb.replace(sb.lastIndexOf("uh"), sb.lastIndexOf("uh") + 2, "a"); //<a> sound e.g. tempuh
            }
        }

        //case 'qu':
        if (sb.indexOf("qu") >= 0) {
            for (int counter = sb.indexOf("qu") + 1; counter < sb.length(); counter++) {
                if (isConsonant(sb.charAt(counter))) {
                    sb.replace(sb.indexOf("qu"), sb.indexOf("qu") + 2, "kw"); //<kw> sound e.g. equal, quit, quick
                    break;
                }
            }
        }

        /*
         *
         //case 'ce':
         if(sb.indexOf("ce") >= 0) {
         sb.replace(sb.indexOf("ce"), sb.indexOf("ce")+2, "se"); //replace ce with se e.g. central,centipede
         }//end if

         //case 'ci':
         if(sb.indexOf("ci") >= 0) {
         sb.replace(sb.indexOf("ci"), sb.indexOf("ci")+2, "si"); //replace ci with si e.g. city,civil
         }//end if

         //case 'cy':
         if(sb.indexOf("cy") >= 0) {
         sb.replace(sb.indexOf("cy"), sb.indexOf("cy")+2, "see"); //replace cy with see e.g. cynical,tracy
         }//end if
         *
         */
        //case '-y': 'vcy'
        if (sb.length() > 2 && sb.lastIndexOf("y") == sb.length() - 1) {
            if ((isConsonant(sb.charAt(sb.lastIndexOf("y") - 1))) && (isVowel(sb.charAt(sb.lastIndexOf("y") - 2)))) {
                sb.replace(sb.lastIndexOf("y"), sb.lastIndexOf("y") + 1, "i"); //<i> sound e.g. party,study
            }
        }

        String phoneme = sb.toString();
        return phoneme;
    }

    /**
     * Given a Swahili word, returns the Phonemic(sound) representation of that
     * word
     *
     * @param grapheme
     * @return Phonemic(sound) representation of the Swahili word
     */
    public String getSwahiliPhoneme(String grapheme) {
        StringBuilder sb = new StringBuilder(grapheme);

        //CONSONANT DIGRAPHS
        //case 'dh'
        if (sb.indexOf("dh") >= 0) {
            sb.replace(sb.indexOf("dh"), sb.indexOf("dh") + 2, "th"); //<th> sound e.g. dhamini, dhahabu
        }//end if

        //case 'kh'
        if (sb.indexOf("kh") >= 0) {
            sb.replace(sb.indexOf("kh"), sb.indexOf("kh") + 2, "k"); //<k> sound e.g. alkhamisi
        }//end if

        //case 'gh'
        if (sb.indexOf("gh") >= 0) {
            sb.replace(sb.indexOf("gh"), sb.indexOf("gh") + 2, "g"); //<g> sound e.g. lugha, ghorofa
        }//end if

        String phoneme = sb.toString();
        return phoneme;
    }//end method

    /**
     * Given the phonemic representation of a word, returns a code representation of
     * that phoneme
     *
     * @param phoneme
     * @return Code representing phoneme of a word
     */
    public String getPhoneticCode(String phoneme) {
        StringBuilder phonetic_code = new StringBuilder(phoneme);

        //Phonetic code - Phoneme
        // 0 - 'th','t','d','dh'
        // 1 - 'sh','s','z','x'
        // 2 - 'q','k','c','kh'
        // 3 - 'f','v'
        // 4 - 'l','r'
        // 5 - 'g','j','gh'
        // 6 - 'y','i'
        // 7 - a,e,i,o,u
        //phonetic code = 0
        while (phonetic_code.indexOf("th") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("th"), phonetic_code.indexOf("th") + 2, "0");
        }
        while (phonetic_code.indexOf("t") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("t"), phonetic_code.indexOf("t") + 1, "0");
        }
        while (phonetic_code.indexOf("d") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("d"), phonetic_code.indexOf("d") + 1, "0");
        }

        //phonetic code = 1
        while (phonetic_code.indexOf("sh") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("sh"), phonetic_code.indexOf("sh") + 2, "1");
        }
        while (phonetic_code.indexOf("s") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("s"), phonetic_code.indexOf("s") + 1, "1");
        }
        while (phonetic_code.indexOf("x") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("x"), phonetic_code.indexOf("x") + 1, "1");
        }
        while (phonetic_code.indexOf("z") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("z"), phonetic_code.indexOf("z") + 1, "1");
        }

        //phonetic code = 2
        while (phonetic_code.indexOf("q") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("q"), phonetic_code.indexOf("q") + 1, "2");
        }
        while (phonetic_code.indexOf("k") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("k"), phonetic_code.indexOf("k") + 1, "2");
        }
        while (phonetic_code.indexOf("c") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("c"), phonetic_code.indexOf("c") + 1, "2");
        }

        //phonetic code = 3
        while (phonetic_code.indexOf("f") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("f"), phonetic_code.indexOf("f") + 1, "3");
        }
        while (phonetic_code.indexOf("v") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("v"), phonetic_code.indexOf("v") + 1, "3");
        }

        //phonetic code = 4
        while (phonetic_code.indexOf("l") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("l"), phonetic_code.indexOf("l") + 1, "4");
        }
        while (phonetic_code.indexOf("r") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("r"), phonetic_code.indexOf("r") + 1, "4");
        }

        //phonetic code = 5
        while (phonetic_code.indexOf("g") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("g"), phonetic_code.indexOf("g") + 1, "5");
        }
        while (phonetic_code.indexOf("j") >= 0) {
            phonetic_code.replace(phonetic_code.indexOf("j"), phonetic_code.indexOf("j") + 1, "5");
        }

        //phonetic code = 6
        if (phonetic_code.indexOf("y") == phonetic_code.length() - 1) {
            phonetic_code.replace(phonetic_code.indexOf("y"), phonetic_code.indexOf("y") + 1, "6");
        }
        if (phonetic_code.indexOf("i") == phonetic_code.length() - 1) {
            phonetic_code.replace(phonetic_code.indexOf("i"), phonetic_code.indexOf("i") + 1, "6");
        }

        /*
         //phonetic code = 7
         while(phonetic_code.indexOf("a")>=0){
         phonetic_code.replace(phonetic_code.indexOf("a"), phonetic_code.indexOf("a")+1, "7");
         }
         while(phonetic_code.indexOf("e")>=0){
         phonetic_code.replace(phonetic_code.indexOf("e"), phonetic_code.indexOf("e")+1, "7");
         }
         while(phonetic_code.indexOf("i")>=0){
         phonetic_code.replace(phonetic_code.indexOf("i"), phonetic_code.indexOf("i")+1, "7");
         }
         while(phonetic_code.indexOf("o")>=0){
         phonetic_code.replace(phonetic_code.indexOf("o"), phonetic_code.indexOf("o")+1, "7");
         }
         while(phonetic_code.indexOf("u")>=0){
         phonetic_code.replace(phonetic_code.indexOf("u"), phonetic_code.indexOf("u")+1, "7");
         }
         *
         */
        String phonological_code = phonetic_code.toString();
        return phonological_code;
    }

    /**
     * Check if a Character is a consonant (b,c,d,f,g,h,j,k,l,m,n,p,r,s,t,v,w,y,z)
     *
     * @param character
     * @return If a character is a consonant, then return true
     */
    private boolean isConsonant(char character) {
        char[] char_array = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'};
        for (char ch : char_array) {
            if (character == ch) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a Character is a vowel (a,e,i,o,u,y)
     *
     * @param character
     * @return If a character is a vowel, then return true
     */
    private boolean isVowel(char character) {
        char[] char_array = {'a', 'e', 'i', 'o', 'u', 'y'};
        for (char ch : char_array) {
            if (character == ch) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the probability that two strings are a match by evaluating the
     * distortion in pronunciations
     *
     * @param originalString
     * @param anotherString
     * @return Probability that two strings are a match
     */
    public static double getProbabilityMatches(String originalString, String anotherString) {

        double numerator = (double) StringCompare.getLengthLCS(originalString, anotherString);   //LCS between word phonemes

        int new_word_length = anotherString.length(); //Characters in a candidate correction
        int org_word_length = originalString.length(); //Characters in original word

        double denominator;

        if (org_word_length <= new_word_length) {
            denominator = (double) Math.min(new_word_length, org_word_length);
        } else {
            denominator = (double) Math.max(new_word_length, org_word_length);
        }

        double lcsratio = numerator / denominator;
        double levenshtien_metric = StringCompare.getLevenshtienDistance(originalString, anotherString);
        if (levenshtien_metric == 0.0) {
            levenshtien_metric = lcsratio;
        }
        double result = (lcsratio) / levenshtien_metric; //phonemic prob.
        return result;
    }
}
