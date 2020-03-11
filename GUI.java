import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;	
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;

// updated last 5/15/19 6:06pm

public class GUI extends Application
{
	public static String word;
	public static Character[] wordArray;
	public static boolean[] booleanArray;
	public static int guesses = 6;
	public static ArrayList<Character> lettersGuessed = new ArrayList<Character>();
	public static Text blankWord;
	public static Text guessCounter;
	public static Text showLetters;
	public static Button[] letterButtons;
	public static Text revealWord;
	public static Text revealWordWin;
	public static int count = 1;
	// for leaderboard stats
	public static long startTime;
	public static long endTime;
	public static double timeElapsed;
	public static Text[] leader;
	public static Text l1 = new Text();
	public static Text l2 = new Text();
	public static Text l3 = new Text();
	public static Text l4 = new Text();
	public static Text l5 = new Text();
	
	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage) throws IOException
	{
	// intro scene =-=-=-=-=-=-=-=-
		word = "";
		
		StackPane introStackPane = new StackPane();
		Scene introScene = new Scene(introStackPane, 800, 600);
		String path = System.getProperty("user.dir");
		
		// background
		BackgroundImage introBackground = new BackgroundImage(new Image(new FileInputStream(path + "\\bluegradient.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		introStackPane.setBackground(new Background(introBackground));
		
		Text title = new Text("Hangman GUI Game");
		title.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,36));
		title.setTranslateY(-200);
		
		Text author = new Text("by Dylan Cerenov");
		author.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
		author.setTranslateY(-160);
		
		Text informationHelp = new Text("Welcome to Hangman. The computer will choose a random word from a list and it is your job to figure out what word it is. You can do this by guessing letters by pressing them on the screen and guessing entire words by hitting the \"Guess Word\" button on the right side. If you guess a letter or word and it is correct, the blank word in the middle of the screen will update accordingly. If you guess a letter or word and it is incorrect, the hangman visual will advance by one and your amount of guesses will go down by one. You start with 6 guesses. There are a couple of buttons: The \"STOP\" button stops the game. The \"HELP\" button provides instructions. The \"GUESS WORD\" button allows you to guess the word entirely. Good luck!");
		informationHelp.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,14));
		informationHelp.setWrappingWidth(600);
		informationHelp.setTranslateY(-30);
		introStackPane.getChildren().addAll(informationHelp);
		
		Text prompt = new Text("Do you want to play?");
		prompt.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
		prompt.setTranslateY(100);
		
		Button playTrue = new Button("Yes");
		playTrue.setMinSize(30, 30);
		playTrue.setTranslateX(-20);
		playTrue.setTranslateY(130);
		
		Button playFalse = new Button("No");
		playFalse.setMinSize(30, 30);
		playFalse.setTranslateX(20);
		playFalse.setTranslateY(130);
		
		// leaderboard scene
			StackPane leaderboardStackPane = new StackPane();
			Scene leaderboardScene = new Scene(leaderboardStackPane, 800, 600);
			
			leaderboardStackPane.setBackground(new Background(introBackground));
			
			Text lbHeading = new Text("Leaderboard:");
			lbHeading.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,36));
			lbHeading.setTranslateY(-230);
			leaderboardStackPane.getChildren().addAll(lbHeading);
			
			Text lbSubheading = new Text("(Top 5 Scores)");
			lbSubheading.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
			lbSubheading.setTranslateY(-190);
			leaderboardStackPane.getChildren().addAll(lbSubheading);
			
			// return button
			Button returnHome = new Button("Back to Menu");
			returnHome.setOnAction(e -> 
			{
				primaryStage.setScene(introScene);
			});
			returnHome.setTranslateY(210);
			returnHome.setMinSize(70, 40);
			leaderboardStackPane.getChildren().addAll(returnHome);
			
	        leaderboardStackPane.getChildren().remove(l1);
	        l1.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
	        l1.setTranslateY(-110);
	        leaderboardStackPane.getChildren().addAll(l1);
	        
	        leaderboardStackPane.getChildren().remove(l2);
	        l2.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
	        l2.setTranslateY(-110 + 30*1);
	        leaderboardStackPane.getChildren().addAll(l2);
	   
	        leaderboardStackPane.getChildren().remove(l3);
	        l3.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
	        l3.setTranslateY(-110 + 30*2);
	        leaderboardStackPane.getChildren().addAll(l3);	        
	        
	        leaderboardStackPane.getChildren().remove(l4);
	        l4.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
	        l4.setTranslateY(-110 + 30*3);
	        leaderboardStackPane.getChildren().addAll(l4);
	        
	        leaderboardStackPane.getChildren().remove(l5);
	        l5.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
	        l5.setTranslateY(-110 + 30*4);
	        leaderboardStackPane.getChildren().addAll(l5);
        //=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
		
		Button showLeaderboard = new Button("Show Leaderboard");
		showLeaderboard.setOnAction(e -> 
		{
			HangmanMethods.refreshLeaderboard();
	        primaryStage.setScene(leaderboardScene);
		});
		showLeaderboard.setTranslateY(210);
		showLeaderboard.setMinSize(70, 40);
		introStackPane.getChildren().addAll(showLeaderboard);
		
	// end intro scene code =-=-=-=-=-=-=-
		
	// game scene =-=-=-=-=-=-=-=-
		
		StackPane gameStackPane = new StackPane();
		Scene gameScene = new Scene(gameStackPane, 800, 600);
		
		// background
		gameStackPane.setBackground(new Background(introBackground));
		
		// instantiates hangman images brother
		Image image1 = new Image(new FileInputStream(path + "\\hangmanimg1.png"));
		Image image2 = new Image(new FileInputStream(path + "\\hangmanimg2.png"));
		Image image3 = new Image(new FileInputStream(path + "\\hangmanimg3.png"));
		Image image4 = new Image(new FileInputStream(path + "\\hangmanimg4.png"));
		Image image5 = new Image(new FileInputStream(path + "\\hangmanimg5.png"));
		Image image6 = new Image(new FileInputStream(path + "\\hangmanimg6.png"));
		Image image7 = new Image(new FileInputStream(path + "\\hangmanimg7.png"));
        ImageView imgView1 = new ImageView(image1);
        imgView1.setFitHeight(300);
        imgView1.setFitWidth(300);
        ImageView imgView2 = new ImageView(image2);
        imgView2.setFitHeight(300);
        imgView2.setFitWidth(300);
        ImageView imgView3 = new ImageView(image3);
        imgView3.setFitHeight(300);
        imgView3.setFitWidth(300);
        ImageView imgView4 = new ImageView(image4);
        imgView4.setFitHeight(300);
        imgView4.setFitWidth(300);
        ImageView imgView5 = new ImageView(image5);
        imgView5.setFitHeight(300);
        imgView5.setFitWidth(300);
        ImageView imgView6 = new ImageView(image6);
        imgView6.setFitHeight(300);
        imgView6.setFitWidth(300);
        ImageView imgView7 = new ImageView(image7);
        imgView7.setFitHeight(300);
        imgView7.setFitWidth(300);
		
		// victory screen
		StackPane winStackPane = new StackPane();
		Scene winScene = new Scene(winStackPane, 800, 600);
		winStackPane.setBackground(new Background(introBackground));
		Text victory = new Text("You won!");
		victory.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,36));
		victory.setTranslateY(-20);
		winStackPane.getChildren().addAll(victory);
		
		revealWordWin = new Text();
		revealWordWin.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
		winStackPane.getChildren().addAll(revealWordWin);
		revealWordWin.setTranslateY(30);
		
		Button continueBtn = new Button("Continue");
		continueBtn.setOnAction(e -> 
		{ 
			primaryStage.setScene(introScene);
		});
		continueBtn.setTranslateY(70);
		winStackPane.getChildren().addAll(continueBtn);
		
		// victory screen leaderboard
		// have text above explaining what to do & the time it took the user to get the word
		// have name textfield enter with button to submit name
		Text showTime = new Text();
		showTime.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,20));
		showTime.setTranslateY(120);
		winStackPane.getChildren().addAll(showTime);
		
		Text leaderboardInstructions = new Text("Enter your name below to be put on the leaderboard: ");
		leaderboardInstructions.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,18));
		leaderboardInstructions.setTranslateY(150);
		winStackPane.getChildren().addAll(leaderboardInstructions);
		
		TextField inputName = new TextField();
		inputName.setMaxWidth(150);
		inputName.setTranslateY(180);
		winStackPane.getChildren().addAll(inputName);
		
		Button inputNameButton = new Button("Submit");
		inputNameButton.setOnAction(e -> 
		{
			HangmanMethods.writeLeaderboard(inputName.getText());
			inputNameButton.setDisable(true);
		});
		inputNameButton.setTranslateY(210);
		winStackPane.getChildren().addAll(inputNameButton);
	
		// loser screen
		StackPane loseStackPane = new StackPane();
		Scene loseScene = new Scene(loseStackPane, 800, 600);
		loseStackPane.setBackground(new Background(introBackground));
		
		Text loss = new Text("You lost!");
		loss.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,36));
		loseStackPane.getChildren().addAll(loss);
		loss.setTranslateY(-20);
		
		revealWord = new Text();
		revealWord.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
		loseStackPane.getChildren().addAll(revealWord);
		revealWord.setTranslateY(30);
		
		loseStackPane.getChildren().addAll(imgView3);
		
		Button continueBtn2 = new Button("Continue");
		continueBtn2.setOnAction(e -> 
		{ 
			primaryStage.setScene(introScene);
		});
		continueBtn2.setTranslateY(70);
		loseStackPane.getChildren().addAll(continueBtn2);
		
		// make buttons
		letterButtons = new Button[26];
		for (int i = 0; i < 26; i++)
		{
			final int num = i;
			letterButtons[i] = new Button("" + (char)(97+i));
			letterButtons[i].setMinSize(30, 30);
			letterButtons[i].setOnAction(new EventHandler<ActionEvent>() 
			{
				public void handle(ActionEvent arg0)
				{
					System.out.print("" + (char)(97+num));
					//gameStackPane.getChildren().remove(letterButtons[num]); // removing button after use
					letterButtons[num].setDisable(true);
					HangmanMethods.hitreg((char)(97+num)); // added 4-30, does things to word when button is hit
					
					// if conditions are met
					if (GUI.guesses == 0)
					{
						gameStackPane.getChildren().remove(imgView6);
						primaryStage.setScene(loseScene);
						endTime = System.currentTimeMillis(); // this is for leaderboard stats
					}
						
					// if all letters are guessed user wins
					String testWord = "";
					for (int i = 0; i < blankWord.getText().length(); i++)
						if (blankWord.getText().charAt(i) != ' ')
							testWord += blankWord.getText().charAt(i);
					
					if (testWord.equalsIgnoreCase(word))
					{
						gameStackPane.getChildren().remove(imgView1);
						primaryStage.setScene(winScene);
						endTime = System.currentTimeMillis(); // this is for leaderboard stats
						GUI.timeElapsed = HangmanMethods.getTime();
						showTime.setText("It took you: " + timeElapsed + " seconds to guess the word!");
					}
					
					if (count == 1 && guesses == 5)
					{
						gameStackPane.getChildren().add(imgView2);
						count++;
						gameStackPane.getChildren().remove(imgView1);
					}
					else if (count == 2 && guesses == 4)
					{
						gameStackPane.getChildren().add(imgView3);
						count++;
						gameStackPane.getChildren().remove(imgView2);
					}
					else if (count == 3 && guesses == 3)
					{
						gameStackPane.getChildren().add(imgView4);
						count++;
						gameStackPane.getChildren().remove(imgView3);
					}
					else if (count == 4 && guesses == 2)
					{
						gameStackPane.getChildren().add(imgView5);
						count++;
						gameStackPane.getChildren().remove(imgView4);
					}
					else if (count == 5 && guesses == 1)
					{
						gameStackPane.getChildren().add(imgView6);
						count++;
						gameStackPane.getChildren().remove(imgView5);
					}
				}
			});	
			gameStackPane.getChildren().addAll(letterButtons[i]);
		}
		
		// set up location of buttons
		int xCoord = -195;
		int yCoord = 210;
		for (int i = 0; i < 26; i++)
		{
			if (i < 13)
			{
				letterButtons[i].setTranslateY(yCoord);
				letterButtons[i].setTranslateX(xCoord + i * (30));
			}
			else
			{
				letterButtons[i].setTranslateY(yCoord + 30);
				letterButtons[i].setTranslateX(-585 + i * (30));
			}
		}
		
		// add help button
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Hangman Help");
		alert.setHeaderText("Welcome to Hangman. \nYou have 6 tries to guess the randomly picked word. \nYou can guess either individual letters or entire words at a time. \nGood luck!");
		alert.setContentText(" - Press \"STOP\" to stop the game at any time.\n - Press \"GUESS WORD\" to guess a word.\n - Press \"MENU\" to go back to the menu.\n - Press \"HELP\" to see this message again.");
		Button helpButton = new Button("HELP");
		helpButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0)
			{
				alert.showAndWait();
			}
		});
		gameStackPane.getChildren().addAll(helpButton);
		helpButton.setTranslateX(-300);
		helpButton.setTranslateY(210);
		
		// set back to menu button
		Button menuButton = new Button("MENU");
		menuButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent arg0)
			{
				primaryStage.setScene(introScene);
			}
		});
		menuButton.setTranslateX(280);
		menuButton.setTranslateY(210);
		
		gameStackPane.getChildren().addAll(menuButton);
		
		
		// stop button
		Button stopButton = new Button("STOP");
		stopButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent arg0)
			{
				Stage stage = (Stage) stopButton.getScene().getWindow();
			    stage.close();
			}
		});
		gameStackPane.getChildren().addAll(stopButton);
		stopButton.setTranslateX(-300);
		stopButton.setTranslateY(240);
		
		// guess word scene =-=-=-=-=-
			StackPane wordStackPane = new StackPane();
			Scene wordScene = new Scene(wordStackPane, 800, 600);
		
			// background
			wordStackPane.setBackground(new Background(introBackground));
			
			Text prompt2 = new Text("Guess your word below:");
			prompt2.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,36));
			prompt2.setTranslateY(-100);
			wordStackPane.getChildren().addAll(prompt2);
			
			TextField insert = new TextField();
			insert.setMaxWidth(300);
			wordStackPane.getChildren().addAll(insert);
			
			Button submitWordBtn = new Button("SUBMIT WORD");
			submitWordBtn.setOnAction(e -> 
			{ 
				//System.out.println(insert.getText());
				if (HangmanMethods.checkWordSyntax(insert.getText()))
				{
					if (HangmanMethods.guessWord(insert.getText())) // user guessed correctly
					{
						gameStackPane.getChildren().remove(imgView1);
						// show victory screen
						primaryStage.setScene(winScene);
						endTime = System.currentTimeMillis(); // this is for leaderboard stats
						GUI.timeElapsed = HangmanMethods.getTime();
						showTime.setText("It took you: " + timeElapsed + " seconds to guess the word!");
					}
					else
					{
						primaryStage.setScene(gameScene);
						HangmanMethods.incorrectGuess();
						if (GUI.guesses == 0)
						{
							primaryStage.setScene(loseScene);
							endTime = System.currentTimeMillis(); // this is for leaderboard stats
						}
						
						//guessed wrong word updates image
						if (count == 1 && guesses == 5)
						{
							gameStackPane.getChildren().add(imgView2);
							count++;
							gameStackPane.getChildren().remove(imgView1);
						}
						else if (count == 2 && guesses == 4)
						{
							gameStackPane.getChildren().add(imgView3);
							count++;
							gameStackPane.getChildren().remove(imgView2);
						}
						else if (count == 3 && guesses == 3)
						{
							gameStackPane.getChildren().add(imgView4);
							count++;
							gameStackPane.getChildren().remove(imgView3);
						}
						else if (count == 4 && guesses == 2)
						{
							gameStackPane.getChildren().add(imgView5);
							count++;
							gameStackPane.getChildren().remove(imgView4);
						}
						else if (count == 5 && guesses == 1)
						{
							gameStackPane.getChildren().add(imgView6);
							count++;
							gameStackPane.getChildren().remove(imgView5);
						}
					}
					
				}
				else // user entered bad input...
				{
					Text syntaxError = new Text("You entered incorrect input:\n - Only letters a-z supported\n - Input has to be same length\n   as the word");
					syntaxError.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
					//syntaxError.setFill(Color.web("#f44242")); // makes text red
					syntaxError.setTranslateX(0);
					syntaxError.setTranslateY(160);
					wordStackPane.getChildren().addAll(syntaxError);
					FadeTransition ft = new FadeTransition(Duration.millis(1500), syntaxError);
					ft.setFromValue(1.0);
					ft.setToValue(0);
					ft.setCycleCount(1);
					ft.setAutoReverse(false);
					ft.play();
				}
			});
			submitWordBtn.setTranslateY(30);
			wordStackPane.getChildren().addAll(submitWordBtn);
			
			Button cancelWordGuessBtn = new Button("CANCEL");
			cancelWordGuessBtn.setOnAction(e ->
			{
				primaryStage.setScene(gameScene);
			});
			cancelWordGuessBtn.setTranslateY(60);
			wordStackPane.getChildren().addAll(cancelWordGuessBtn);
			
			
		// end guess word scene
		Button guessWordButton = new Button("GUESS WORD");
		guessWordButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent arg0)
			{
				primaryStage.setScene(wordScene);
			}
		});
		gameStackPane.getChildren().addAll(guessWordButton);
		guessWordButton.setTranslateX(280);
		guessWordButton.setTranslateY(240);
		
		// shows guess counter
		guessCounter = new Text("Guesses: " + guesses);
		guessCounter.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,36));
		guessCounter.setTranslateY(-250);
		gameStackPane.getChildren().addAll(guessCounter);
		
		// shows incorrect guesses
		showLetters = new Text("Letters guessed:");
		showLetters.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,FontPosture.REGULAR,24));
		showLetters.setTranslateY(-190);
		gameStackPane.getChildren().addAll(showLetters);
		
		
	// end game scene code =-=-=-=-=-=-=-
		
	// leaderboards scene
	// TODO: aDD A LEADERBOARD
	// when user wins, ask them for their name and record their name next to their time in seconds
	// sort winners by the time it took them to get the word
	// 
		
		
		
		
	// else =-=-=-=-=-=-=-=-=-=-	
		
		playTrue.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent arg0)
			{
				primaryStage.setScene(gameScene);
				try 
				{
					HangmanMethods.resetGame();
					gameStackPane.getChildren().remove(imgView1);
					gameStackPane.getChildren().remove(imgView2);
					gameStackPane.getChildren().remove(imgView3);
					gameStackPane.getChildren().remove(imgView4);
					gameStackPane.getChildren().remove(imgView5);
					gameStackPane.getChildren().remove(imgView6);
					
					gameStackPane.getChildren().add(imgView1);
					inputNameButton.setDisable(false);
				} catch (IOException e) 
				{
					
				}
			}
		});
		
		playFalse.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent arg0)
			{
				Stage stage = (Stage) playFalse.getScene().getWindow();
			    stage.close();
			}
		});
		
		introStackPane.getChildren().add(title);
		introStackPane.getChildren().add(author);
		introStackPane.getChildren().add(prompt);
		introStackPane.getChildren().add(playTrue);
		introStackPane.getChildren().add(playFalse);
		primaryStage.setScene(introScene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Hangman GUI Program");
		primaryStage.show();
	// end else section =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
		
		// this is where is game begins
		// word = (HangmanMethods.chooseWord()).toLowerCase(); // gets word
		System.out.println(word); // for testing
		
//		wordArray = new Character[word.length()];
//		for (int i = 0; i < word.length(); i++) // fills character array with letters in the word
//			wordArray[i] = word.charAt(i);
		
//		booleanArray = new boolean[word.length()];
//		for (int i = 0; i < word.length(); i++) // fills booleanArray with falses
//			booleanArray[i] = false;
		
		String msg = " ";
		for (int i = 0; i < word.length(); i++)
			msg += "_ ";
		
		
		blankWord = new Text();
		blankWord.setText(msg);
		blankWord.setTranslateY(140);
		blankWord.setFont(Font.font("Times New Roman",FontWeight.NORMAL,FontPosture.REGULAR,36));
		gameStackPane.getChildren().addAll(blankWord);
	}
}