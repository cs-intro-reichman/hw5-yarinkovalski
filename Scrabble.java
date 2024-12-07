/*
 * RUNI version of the Scrabble game.
 */
public class Scrabble {

	// Note 1: "Class variables", like the five class-level variables declared below,
	// are global variables that can be accessed by any function in the class. It is
	// customary to name class variables using capital letters and underline characters.
	// Note 2: If a variable is declared "final", it is treated as a constant value
	// which is initialized once and cannot be changed later.

	// Dictionary file for this Scrabble game
	static final String WORDS_FILE = "dictionary.txt";

	// The "Scrabble value" of each letter in the English alphabet.
	// 'a' is worth 1 point, 'b' is worth 3 points, ..., z is worth 10 points.
	static final int[] SCRABBLE_LETTER_VALUES = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3,
												  1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };

	// Number of random letters dealt at each round of this Scrabble game
	static int HAND_SIZE = 10;

	// Maximum number of possible words in this Scrabble game
	static int MAX_NUMBER_OF_WORDS = 100000;

    // The dictionary array (will contain the words from the dictionary file)
	static String[] DICTIONARY = new String[MAX_NUMBER_OF_WORDS];

	// Actual number of words in the dictionary (set by the init function, below)
	static int NUM_OF_WORDS;

	// Populates the DICTIONARY array with the lowercase version of all the words read
	// from the WORDS_FILE, and sets NUM_OF_WORDS to the number of words read from the file.
	public static void init() {
		// Declares the variable in to refer to an object of type In, and initializes it to represent
		// the stream of characters coming from the given file. Used for reading words from the file.  
		In in = new In(WORDS_FILE);
        System.out.println("Loading word list from file...");
        NUM_OF_WORDS = 0;
		while (!in.isEmpty()) {
			// Reads the next "token" from the file. A token is defined as a string of 
			// non-whitespace characters. Whitespace is either space characters, or  
			// end-of-line characters.
			DICTIONARY[NUM_OF_WORDS++] = in.readString().toLowerCase();
		}
        System.out.println(NUM_OF_WORDS + " words loaded.");
	}

	// Checks if the given word is in the dictionary.
	public static boolean isWordInDictionary(String word) {
		//// Replace the following statement with your code

		for (int i=0; i < NUM_OF_WORDS; i++)
			if (DICTIONARY[i].equals(word))
				return true;
		
		return false;
	}
	
    /*  Returns true if str1 is a subset string str2, false otherwise
     *  Examples:
     *  subsetOf("sap","space") returns true
     *  subsetOf("spa","space") returns true
     *  subsetOf("pass","space") returns false
     *  subsetOf("c","space") returns true */
	public static boolean subsetOf(String str1, String str2) 
    {
         //// Replace the following statement with your code
    
		 if (str2=="")
		 	return false;
        int ind3;
		for (int ind1 = 0; ind1 < str1.length();ind1++)
		{
			while (str2.length()>0) 
			{
				ind3 = str2.indexOf(str1.charAt(ind1)); 
				if (ind3 == -1)
					return false;
				else
				{
					str2 = str2.substring(0,ind3) + str2.substring(ind3+1);
					break;
				}
        	}
		}
		return true;
    
    } 


	// Returns the Scrabble score of the given word.
	// If the length of the word equals the length of the hand, adds 50 points to the score.
	// If the word includes the sequence "runi", adds 1000 points to the game.
	public static int wordScore(String word) {
		//// Replace the following statement with your code

		int score = 0;
		
		int ind = 0;
		for (int i = 0; i < word.length(); i++) {
			ind = word.charAt(i) - 'a';
			score+= SCRABBLE_LETTER_VALUES[ind];
		}

		score = score * word.length();

		if (word.length() == HAND_SIZE)
			score+= 50;

		if (subsetOf("runi", word))
			score+=1000;

		return score;
	}


	// Creates a random hand of length (HAND_SIZE - 2) and then inserts
	// into it, at random indexes, the letters 'a' and 'e'
	// (these two vowels make it easier for the user to construct words)
	public static String createHand() 
	{
		//// Replace the following statement with your code
		String str = MyString.randomStringOfLetters(HAND_SIZE-2);
		str = MyString.insertRandomly ('e', str);
		str = MyString.insertRandomly ('a', str);

		return str;
	}
	
    // Runs a single hand in a Scrabble game. Each time the user enters a valid word:
    // 1. The letters in the word are removed from the hand, which becomes smaller.
    // 2. The user gets the Scrabble points of the entered word.
    // 3. The user is prompted to enter another word, or '.' to end the hand. 
	public static void playHand(String hand) 
	{
		int n = hand.length();
		int score = 0;
		// Declares the variable in to refer to an object of type In, and initializes it to represent
		// the stream of characters coming from the keyboard. Used for reading the user's inputs.   
		In in = new In();
		while (hand.length() > 0) {
			System.out.println("Current Hand: " + MyString.spacedString(hand));
			System.out.println("Enter a word, or '.' to finish playing this hand:");
			// Reads the next "token" from the keyboard. A token is defined as a string of 
			// non-whitespace characters. Whitespace is either space characters, or  
			// end-of-line characters.

			String input = in.readString();

			if (input.equals("."))
				break;
			
			//// Replace the following break statement with code
			//// that completes the hand playing loop

			if (!isWordInDictionary(input)) {
				System.out.println("No such word in the dictionary.Try again.\n");

			} else if (!subsetOf(input,hand)) {
				System.out.println("Invalid word. Try again.");

			} else {
				score = score + wordScore(input);
				hand = MyString.remove(hand, input);
				System.out.println(input + " earned " + wordScore(input) + " points. Score: " + score + " points\n");
			}
		}
		if (hand.length() == 0) {
	        System.out.println("Ran out of letters. Total score: " + score + " points");
		} else {
			System.out.println("End of hand. Total score: " + score + " points");
		}
	}

	// Plays a Scrabble game. Prompts the user to enter 'n' for playing a new hand, or 'e'
	// to end the game. If the user enters any other input, writes an error message.
	public static void playGame() {
		// Initializes the dictionary
    	init();
		// The variable in is set to represent the stream of characters 
		// coming from the keyboard. Used for getting the user's inputs.  
		In in = new In();

		while(true) {
			System.out.println("Enter n to deal a new hand, or e to end the game:");
			// Gets the user's input, which is all the characters entered by 
			// the user until the user enter the ENTER character.
			String input = in.readString();

			//// Replace the following break statement with code
			//// that completes the game playing loop
			if (input.equals("n")) {
	        	input = createHand();
				playHand(input);
			}

			if (input.equals("e"))
				break;
		}
	}

	public static void main(String[] args) {
		//// Uncomment the test you want to run
		//testBuildingTheDictionary();  
		//testScrabbleScore();    
		//testCreateHands();  
		//testPlayHands();
		playGame();
	}

	public static void testBuildingTheDictionary() {
		init();
		// Prints a few words
		for (int i = 0; i < 5; i++) {
			System.out.println(DICTIONARY[i]);
		}
		System.out.println(isWordInDictionary("mango"));
	}
	
	public static void testScrabbleScore() {
		System.out.println(wordScore("bee"));	
		System.out.println(wordScore("babe"));
		System.out.println(wordScore("friendship"));
		System.out.println(wordScore("running"));
	}
	
	public static void testCreateHands() {
		System.out.println(createHand());
		System.out.println(createHand());
		System.out.println(createHand());
	}
	public static void testPlayHands() {
		init();
		//playHand("ucmtaueeck");
		//playHand("ocostrza");
		//playHand("arbffip");
		//playHand("aretiin");
	}
}
