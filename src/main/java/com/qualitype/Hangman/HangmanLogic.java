package com.qualitype.Hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

public class HangmanLogic {

	public HangmanLogic(URL url) {
		this.url = url;
		try {
			this.wordList = readWordlist();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private URL url;
	private List<String> wordList;

	private List<String> readWordlist() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(this.url.openStream()));
		String inputLine;
		List<String> out = new ArrayList<>();
		while ((inputLine = in.readLine()) != null) {
			out.add(inputLine);
		}
		return out;
	}

	public Character[] chooseWord() {
		Random r = new Random();
		String word = this.wordList.get(r.nextInt(this.wordList.size() + 1));
		Character[] charObjectArray = ArrayUtils.toObject(word.toLowerCase().toCharArray());
		return charObjectArray;
	}

	public static List<Integer> getIndices(Character[] word, Character target) {
		if (!ArrayUtils.contains(word, target)) {
			return null;
		}
		List<Integer> out = new ArrayList<>();
		for (int i = 0; i < word.length; i++) {
			if (word[i].equals(target)) {
				out.add(Integer.valueOf(i));
			}
		}
		return out;
	}

}
