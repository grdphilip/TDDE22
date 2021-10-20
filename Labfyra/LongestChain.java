
/**
 * The LongestChain class contains the necessary data to perform breadth first
 * searches in accordance with the laboration, and determine the longest chain.
 *
 * @author Magnus Nielsen, Tommy Färnqvist
 */
class LongestChain {
	private Queue q; // queue used in the BFS
	private String goalWord; // goal word of the BFS
	private int wordLength;
	private final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö', 'é' };
	private int alphabetLength = alphabet.length;

	public LongestChain(int wordLength) {
		this.wordLength = wordLength;
		q = new Queue();
	}

	/**
	 * IsGoal checks if w is the goal word.
	 *
	 * @param w - the word to compare with the goal word (String)
	 * @return - true if w is the goal word, false otherwise (boolean)
	 */
	private boolean isGoal(String w) {
		return w.equals(goalWord);
	}

	/**
	 * makeChildren creates all words that differ in one letter from the given word
	 * x. If the word is encountered for the first time it will be added to the
	 * queue "q" for later processing.
	 *
	 * @param x - the word of origin (WordRec)
	 * @return the goal word if it's found, null otherwise (WordRec)
	 */

	// Gör om ordet till en String av bokstäver för att underlätta sökningen

	private WordRec makeChildren(WordRec x) {
		char[] ourWord = x.getWord().toCharArray(); //Gör ordet vi skickar in till en array av characters
		int i = 0;

		for (; i < ourWord.length; i++) { //Loopar över vårt ord

			int c = i;
			for (; c < alphabetLength; c++) { // Loopa över alfabetet 

				if (alphabet[c] != ourWord[i]) { // kollar ifall bokstaven på plats i stämmer överens med c 

					char[] tmp = x.getWord().toCharArray(); // Om ej skapar vi ett nytt temporärt ord 
					tmp[i] = alphabet[c];                   // 

					String res = WordList.contains(new String(tmp)); // Skapar ny sträng av temporära ordet 
					if (res != null && WordList.markAsUsedIfUnused(res)) { // Om den inte är använd skapar vi en ny ordkedja
						WordRec wr = new WordRec(res, x); // Med res
						if (isGoal(res)) {
							return wr;
						}
						q.enqueue(wr); // Queua för senare behandling
					}
				}
			}
		}
		return null;
	}

	/**
	 * BreadthFirst perform a BFS from startWord to find the shortest path to
	 * endWord. The shortest path will be returned as a chain of word posts
	 * (WordRec). If there is no path between the two words, null will be returned.
	 *
	 * @param startWord - the word from which we wish to begin the search (String)
	 * @param endWord   - the goal word, the end of the search (String)
	 * @return - a chain of word posts that can be traversed if the goal word is
	 *         found, null otherwise (WordRec)
	 */
	public WordRec breadthFirst(String startWord, String endWord) {
		WordList.eraseUsed();
		WordRec start = new WordRec(startWord, null);
		WordList.markAsUsedIfUnused(startWord);

		goalWord = endWord;
		q.empty();
		q.enqueue(start);

		try {

			while (true) {

				WordRec wr = makeChildren((WordRec) q.dequeue());
				if (wr != null) {
					return wr;

				}
			}
		}

		catch (Exception e) {
			return null;
		}

	}

	public void NeighborBreadthFirstSearch(String endWord) {
		WordList.eraseUsed();
		WordRec endList = new WordRec(endWord, null);
		WordList.markAsUsedIfUnused(endWord);

		q.empty();
		q.enqueue(endList);
		int maxChainLength = 0;
		WordRec maxChainRec = null;

		try {
			while (true) {

				WordRec x = ((WordRec) q.dequeue());

				char[] ourWord = x.getWord().toCharArray();
				int i = 0;

				for (; i < ourWord.length; i++) { // Går igenom varje bokstav i vårat ord

					int c = i;
					for (; c < alphabetLength; c++) {

						if (alphabet[c] != ourWord[i]) { // Om bokstaven c går igenom i alfabeter ej finns i vårat ord

							char[] tmp = x.getWord().toCharArray();
							tmp[i] = alphabet[c]; // Skapar vi en temporär C

							String res = WordList.contains(new String(tmp));
							if (res != null && WordList.markAsUsedIfUnused(res)) {
								WordRec wr = new WordRec(res, x);

								if (wr.chainLength() > maxChainLength) { // Om den nuvarande kedjan är längre än föregående kedjor blir det den nya längsta
									maxChainLength = wr.chainLength();
									maxChainRec = wr;
								}
								q.enqueue(wr);
							}
						}
					}
				}
			}
		} catch (Exception e) {

			if (maxChainRec != null) {
				System.out.println(endWord + ": " + maxChainLength + " ord");
				maxChainRec.printChain();
			}
		}
	}
}

// 3 idéer! Antingen snygga till makechildren så den går på bokstäver istället för orden för att hitta kedja direkt. 
// Skriv om bredden först sökningen så att den går igenom kedjan av grannord istället för att göra en bredden först sökning på alla möjliga kedjo
// Eventuellt göra allting i samma metod och kalla på från main. 
