package spell;

import java.io.IOException;
import java.io.File;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {

    private Trie dictionary;
    private Set<String> similarWords;
    private Set<String> sameEditDistance;
    private Set<String> sameCount;

    public SpellCorrector() {
        dictionary = new Trie();
        similarWords = new HashSet<String>();
        sameEditDistance = new HashSet<String>();
        sameCount = new HashSet<String>();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        // read and parse file into dictionary
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);

        while(scanner.hasNext()) {
            String toAdd = scanner.next();
            dictionary.add(toAdd);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        // clear sets at the start
        similarWords.clear();
        sameEditDistance.clear();
        sameCount.clear();

        String myInputWord = inputWord.toLowerCase();

        // if the word is found in the dictionary, return it
        if (dictionary.find(myInputWord) != null) {
            return myInputWord;
        }

        // get all words with edit distance 1
        fillSimilarWords(myInputWord);

        /* if one of these words with edit distance 1 is in our dictionary, add it to sameEditDistance -- that is
        where we will check for the word with the highest count */
        for (String word : similarWords) {
            if (dictionary.find(word) != null) {
                sameEditDistance.add(word);
            }
        }
        /* HOWEVER, if none of the edit distance 1 words were found in the dictionary, we must look for edit distance
        2 words, and then do the same thing as above */
        if (sameEditDistance.isEmpty()) {
            Set<String> copy = new HashSet<>(similarWords);
            similarWords.clear();
            for (String word : copy) {
                fillSimilarWords(word);
            }
            for (String word : similarWords) {
                if (dictionary.find(word) != null) {
                    sameEditDistance.add(word);
                }
            }
        }
        // if edit distance 2 still doesn't work, return null
        if(sameEditDistance.isEmpty()) {
            return null;
        }

        /* for every word that has the closet edit distance, if it has the highest count thus far, clear and put it
        in sameCount */
        int maxCount = 0;
        for (String word : sameEditDistance) {
            int count = dictionary.find(word).getValue();
            if (count > maxCount) {
                sameCount.clear();
                sameCount.add(word);
                maxCount = count;
            }
            else if (count == maxCount) {
                sameCount.add(word);
            }
        }

        /* now that you have the words with closest edit distance and highest count stored in a set, just return
        the first one because it's in alphabetical order */
        Iterator<String> it = sameCount.iterator();
        String result = it.next();
        for (String word : sameCount) {
            if (word.compareTo(result) < 0) {
                result = word;
            }
        }
        return result;

    }

    public void fillSimilarWords(String word) {
        deletion(word);
        transposition(word);
        alteration(word);
        insertion(word);
    }

    public void deletion(String word) {
        // delete any character from the word
        for (int i = 0; i < word.length(); i++) {
            StringBuilder builder = new StringBuilder(word);
            builder.deleteCharAt(i);
            similarWords.add(builder.toString());
        }
    }

    public void transposition(String word) {
        // swap any two adjacent characters in the word
        for (int i = 0; i < word.length() - 1; i++) {
            StringBuilder builder = new StringBuilder(word);
            char first = builder.charAt(i);
            char second = builder.charAt(i+1);
            builder.setCharAt(i, second);
            builder.setCharAt(i+1, first);
            similarWords.add(builder.toString());
        }
    }

    public void alteration(String word) {
        // one character is altered
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < 26; j++) {
                char letter = (char)('a' + j);
                // the line below is because StringBuilder.replace needs a string
                String toReplace = Character.toString(letter);
                StringBuilder builder = new StringBuilder(word);
                if (letter == word.charAt(i)) {
                    continue;
                }
                else {
                    builder.replace(i,i+1,toReplace);
                }
                similarWords.add(builder.toString());
            }
        }
    }

    public void insertion(String word) {
        // one character is inserted
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < 26; j++) {
                char letter = (char)('a' + j);
                StringBuilder builder = new StringBuilder(word);
                builder.insert(i,letter);
                similarWords.add(builder.toString());
            }
        }
        // this second "for" block covers what the first one missed -- appending chars
        for (int j = 0; j < 26; j++) {
            char letter = (char)('a' + j);
            StringBuilder builder = new StringBuilder(word);
            builder.append(letter);
            similarWords.add(builder.toString());
        }
    }

}
