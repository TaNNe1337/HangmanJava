package com.qualitype.Hangman;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Hello world!
 *
 */
public class UserInput {
	private static final String URL = "http://www.netzmafia.de/software/wordlists/deutsch.txt";
	private static final int HARD = 3;
	private static final int MEDIUM = 2;
	private static final int EASY = 1;
	private int maxGuesses;

	public static void main(String[] args) {
		boolean finished = false;
		while (!finished) {
			UserInput input = new UserInput();
			int wrongGuesses = 0;
			int guesses = 0;
			Scanner scanner = new Scanner(System.in);
			input.chooseDifficulty(scanner);
			Character[] word = input.getWordAsCharArray();
			Character[] guessedWord = new Character[word.length];
			Character[] guessedChar = new Character[input.maxGuesses];
			Arrays.fill(guessedWord, '-');
			Arrays.fill(guessedChar, Character.MIN_VALUE);
			while (wrongGuesses < input.getMaxGuesses()) {
				Stream.of(guessedWord).forEach(System.out::print);
				System.out.println();
				Stream.of(guessedChar).forEach(System.out::print);
				System.out.println();
				System.out.print("Make a guess: ");
				Character userGuessedChar = Character.valueOf(scanner.next().toLowerCase().toCharArray()[0]);
				guessedChar[guesses] = userGuessedChar;
				List<Integer> indices = HangmanLogic.getIndices(word, userGuessedChar);
				guesses++;
				if (indices != null) {
					for (Integer index : indices) {
						guessedWord[index] = userGuessedChar;
					}
				} else {
					wrongGuesses++;
				}
				if (!ArrayUtils.contains(guessedWord, '-')) {
					System.out.println("You won!");
					return;
				}
			}
			System.out.println("You lost");
			System.out.println("Play again? y/n");
			String playAgain = scanner.next();

			while (!playAgain.equals("y") && !playAgain.equals("n")) {
				System.out.println("Invalid input");
				playAgain = scanner.next();
				if (playAgain.equals("n")) {
					finished = true;
				}
			}
		}
	}

	private void chooseDifficulty(Scanner scanner) {
		int input;
		System.out.print(
				"1: easy(15 wrong guesses)\n2: medium(12 wrong guesses)\n3: hard(10 wrong guesses)\nChoose difficulty: ");
		input = scanner.nextInt();
		switch (input) {
		case UserInput.EASY:
			setMaxGuesses(15);
			break;
		case UserInput.MEDIUM:
			setMaxGuesses(12);
			break;
		case UserInput.HARD:
			setMaxGuesses(10);
			break;
		default:
			System.out.print("Input not valid. Try again: ");
			chooseDifficulty(scanner);
			break;
		}
	}

	private Character[] getWordAsCharArray() {
		HangmanLogic hangman = null;
		try {
			hangman = new HangmanLogic(new URL(UserInput.URL));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return hangman.chooseWord();
	}

	public int getMaxGuesses() {
		return maxGuesses;
	}

	public void setMaxGuesses(int maxGuesses) {
		this.maxGuesses = maxGuesses;
	}
}
