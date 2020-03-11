import java.util.*;
import javafx.scene.text.Text;
import java.io.*;

//updated last 5/15/19 6:06pm

public class HangmanMethods 
{
	public static Scanner scan = new Scanner(System.in);
	public static Random rand = new Random();
	public static int WORD_COUNT;
	
	public HangmanMethods() throws IOException
	{
		WORD_COUNT = howManyWordsAreInTheTextFileProvided();
	}
	
	
	//******************************************
	// Choose what word is chosen from input.txt
	//******************************************
	public static String chooseWord() throws IOException
	{
		WORD_COUNT = howManyWordsAreInTheTextFileProvided();
		Scanner scanWord = new Scanner(new File("input.txt"));
		String word = "";
		int random = rand.nextInt(WORD_COUNT) + 1; //1-WORD_COUNT
		for (int i = 0; i < WORD_COUNT; i++)
		{
			word = scanWord.next();
			if (i == random)
				break;
		}
	
		return word;
	}
	
	//********************************************************
	// Finds out how many words are in the text file provided.
	//********************************************************
	public static int howManyWordsAreInTheTextFileProvided() throws IOException
	{
		Scanner scanWord = new Scanner(new File("input.txt"));
		int returnCount = 0;
		while (scanWord.hasNext())
		{
			returnCount++;
			scanWord.next();
		}
		return returnCount;
	}
	
	//*******************************************************
	// This method is called when a letter button is pressed.
	// TODO: finish this header
	//*******************************************************
	public static void hitreg(char letter)
	{
//		//String consoleOutput = scan.next();
//		//char mostRecentInput = consoleOutput.charAt(consoleOutput.length()-1);
//		//System.out.println("input: " + mostRecentInput);
//		System.out.println(letter);
		
		//GUI.lettersGuessed.add(letter);
		
		if (isLetterInWord(letter)) // user guessed correct letter
		{
			// update word
			// tell user they guessed correctly
			for (int i = 0; i < GUI.word.length(); i++) // updates center word
			{
				if (GUI.wordArray[i] == letter)
					GUI.booleanArray[i] = true;
				GUI.blankWord.setText(updateWord());
			}
		}
		else // user guessed incorrect letter
		{
			incorrectGuess();
			GUI.lettersGuessed.add(letter);
			GUI.showLetters.setText("Incorrect Letters Guessed: " + updateList());
		}
	}
	
	//***********************************************************************
	// This method returns the incomplete string that is shown to the player.
	//***********************************************************************
	public static String updateWord()
	{
		String returnMsg = "";
		for (int i = 0; i < GUI.word.length(); i++)
		{
			if (GUI.booleanArray[i] == true)
				returnMsg += " " + GUI.wordArray[i].toString() + " ";
			else
				returnMsg += " _ ";
		}
		return returnMsg;
	}
	
	//*******************************
	// Updates list
	//*******************************
	public static String updateList()
	{
		String returnMsg = "";
		for (int i = 0; i < GUI.lettersGuessed.size(); i++)
		{
			returnMsg += " " + GUI.lettersGuessed.get(i);
		}
		return returnMsg;
	}
	
	//************************************************
	// Checks the word syntax for the word guess scene
	//************************************************
	public static boolean checkWordSyntax(String guess)
	{
		boolean returnVal = true;
		for (int i = 0; i < guess.length(); i++)
		{
			if (guess.charAt(i) < 97 || guess.charAt(i) > 122) // if letter is not a-z or A-Z
				returnVal = false;
		}
		if (guess.length() != GUI.word.length())
			returnVal = false;
		return returnVal;
	}
	
	//***************************************************
	// Method is called when word is trying to be guessed
	// true: word guess is correct
	// false: word guess is not correct
	//***************************************************
	public static boolean guessWord(String guess)
	{
		boolean returnVal = false;
		if (guess.equals(GUI.word))
			returnVal = true;
		return returnVal;
	}
	
	
	//***********************************************
	// Method checks if the letter is in the word
	// True: letter in the word
	// False: letter isn't in the word
	//***********************************************
	public static boolean isLetterInWord(char letter)
	{
		boolean returnVal = false;
		for (int i = 0; i < GUI.word.length(); i++)
		{
			if (letter == GUI.word.charAt(i))
				returnVal = true;
		}
		return returnVal;
	}
	
	//***********************************************
	// Removes one guess and updates guess count text
	// added for the sake of convienience
	//***********************************************
	public static void incorrectGuess()
	{
		GUI.guesses--;
		GUI.guessCounter.setText("Guesses: " + GUI.guesses); // updates guess count
	}
	
	//****************************************
	// For the leaderboard. Returns the times.
	//****************************************
	public static double getTime()
	{
		//System.out.println("==" + (0.0 + GUI.endTime - GUI.startTime) / 1000 + "==");
		return (0.0 + GUI.endTime - GUI.startTime) / 1000;
	}
	
	//***********************************************
	// Writes the leaderboard
	//***********************************************
	public static void writeLeaderboard(String input)
	{
		try
		{
			FileWriter paperbackwriter = new FileWriter("leaderboard.txt", true);
			BufferedWriter out = new BufferedWriter(paperbackwriter); 
//		    out.write(input + " : \"" + GUI.word + "\" : "+ GUI.timeElapsed + " seconds \n");
			out.write("\n" + GUI.timeElapsed + " : " + input + " : " + GUI.word);
		    out.close(); 
		}
		catch(IOException e)
		{
		    e.printStackTrace();
		}
	}
	
	public static void refreshLeaderboard()
	{
		// sets data in the table
		ArrayList<String> stats = new ArrayList<String>();
					
		// get winners
		try (BufferedReader br = new BufferedReader(new FileReader("leaderboard.txt"))) {
		   String line;
		   while ((line = br.readLine()) != null) {
				stats.add(line);
		   }
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// sorts list by winners. lowest time to highest
		int n = stats.size();  
        String temp;
        for(int i=0; i < n; i++)
        {
        	for(int j=1; j < (n-i); j++)
        	{
        		Scanner scan1 = new Scanner(stats.get(j-1));
        		Scanner scan2 = new Scanner(stats.get(j));
        		if (scan1.nextDouble() > scan2.nextDouble()) //if(arr[j-1] > arr[j])
        		{
        			//swap elements
        			temp = stats.get(j-1);//temp = arr[j-1];
        			stats.set(j-1, stats.get(j));//arr[j-1] = arr[j];
        			stats.set(j, temp);//arr[j] = temp;
        		}
        	}
        }
        
        // show top 5 players
        GUI.leader = new Text[5];
        for (int i = 0; i < GUI.leader.length; i++)
        {	
        	if (i < stats.size())
        	{
        		Scanner statsc = new Scanner(stats.get(i));
	        	double tempTime = statsc.nextDouble();
	        	statsc.next();
	        	String tempName = statsc.next();
	        	statsc.next();
	        	String tempWord = statsc.nextLine();
	        	tempWord = tempWord.substring(1);
	        	GUI.leader[i] = new Text(tempName + " guessed \"" + tempWord + "\" in " + tempTime + " seconds");
        	}
        	else
        		GUI.leader[i] = new Text("EMPTY");
        }
		GUI.l1.setText("1st place: " + GUI.leader[0].getText());
		GUI.l2.setText("2nd place: " + GUI.leader[1].getText());
		GUI.l3.setText("3rd place: " + GUI.leader[2].getText());
		GUI.l4.setText("4th place: " + GUI.leader[3].getText());
		GUI.l5.setText("5th place: " + GUI.leader[4].getText());
	}
	
	//***********************************************
	// resets the game
	//***********************************************
	public static void resetGame() throws IOException
	{
		// resets word
		GUI.word = (HangmanMethods.chooseWord()).toLowerCase();
		
		// word array
		GUI.wordArray = new Character[GUI.word.length()];
		for (int i = 0; i < GUI.word.length(); i++) // fills character array with letters in the word
			GUI.wordArray[i] = GUI.word.charAt(i);
		
		// boolean array
		GUI.booleanArray = new boolean[GUI.word.length()];
		for (int i = 0; i < GUI.word.length(); i++) // fills booleanArray with falses
			GUI.booleanArray[i] = false;
		
		// guess counter
		GUI.guesses = 6;
		
		// letters guessed
		GUI.lettersGuessed.clear();
		
		//blank word
		String msg = " ";
		for (int i = 0; i < GUI.word.length(); i++)
			msg += "_ ";
		GUI.blankWord.setText(msg);
		
		//guessCounter
		GUI.guessCounter.setText("Guesses: " + 6);
		
		//showLetters
		GUI.showLetters.setText("Incorrect Letters Guessed: " + updateList());
		
		// keyboard in the middle
		for (int i = 0; i < 26; i++)
			GUI.letterButtons[i].setDisable(false);
		
		// updates reveal word for loss screen
		GUI.revealWord.setText("The word was: " + GUI.word);
		
		// updates reveal word for the win screen
		GUI.revealWordWin.setText("The word was: " + GUI.word);
		
		//resets count for images
		GUI.count = 1;
		
		// leaderboard timer start
		GUI.startTime = System.currentTimeMillis();
		
		System.out.println(GUI.word); // for testing purposes
	}
}