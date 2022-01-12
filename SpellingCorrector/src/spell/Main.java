package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];

		//
        //Create an instance of your corrector here
        //
		ISpellCorrector corrector = new SpellCorrector();

		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}

		System.out.println("Suggestion is: " + suggestion);

		// SPELL CORRECTOR TESTING
//		String word = "top";

		//deletion
//		for (int i = 0; i < word.length(); i++) {
//			StringBuilder builder = new StringBuilder(word);
//			builder.deleteCharAt(i);
//			System.out.println(builder.toString());
//		}
		//transposition
//		for (int i = 0; i < word.length() - 1; i++) {
//			StringBuilder builder = new StringBuilder(word);
//			char first = builder.charAt(i);
//			char second = builder.charAt(i+1);
//			builder.setCharAt(i, second);
//			builder.setCharAt(i+1, first);
//			System.out.println(builder.toString());
//		}
		//alteration
//		for (int i = 0; i < word.length(); i++) {
//			for (int j = 0; j < 26; j++) {
//				char letter = (char)('a' + j);
//				// the line below is because StringBuilder.replace needs a string
//				String toReplace = Character.toString(letter);
//				StringBuilder builder = new StringBuilder(word);
//				if (letter == word.charAt(i)) {
//					continue;
//				}
//				else {
//					builder.replace(i,i+1,toReplace);
//				}
//				System.out.println(builder.toString());
//			}
//		}
		//insertion
//		for (int i = 0; i < word.length(); i++) {
//			for (int j = 0; j < 26; j++) {
//				char letter = (char)('a' + j);
//				StringBuilder builder = new StringBuilder(word);
//				builder.insert(i,letter);
//				System.out.println(builder.toString());
//			}
//		}
//		for (int j = 0; j < 26; j++) {
//			char letter = (char)('a' + j);
//			StringBuilder builder = new StringBuilder(word);
//			builder.append(letter);
//			System.out.println(builder.toString());
//		}


		// TRIE TESTING
//		Trie testTrie = new Trie();
//		testTrie.add("car");
//		testTrie.add("cat");
//		String testString = testTrie.toString();
//		System.out.println(testString);
//		System.out.println(testTrie.getNodeCount());
//		System.out.println(testTrie.getWordCount());
//
//		if (testTrie.find("car") == null) {
//			System.out.println("wrong");
//		}
//		else {
//			System.out.println("correct");
//		}
//
//		if (testTrie.find("c") == null) {
//			System.out.println("correct");
//		}
//		else {
//			System.out.println("wrong");
//		}
//
//		Trie testTrie2 = new Trie();
//		testTrie2.add("car");
//		testTrie2.add("car");
//		testTrie2.add("cat");
//
//		if (testTrie.equals(testTrie2)) {
//			System.out.println("wrong -- should not be equal");
//		}
//		else {
//			System.out.println("correct!");
//		}

	}

}
