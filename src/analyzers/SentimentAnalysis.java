package analyzers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.document.Document;

public class SentimentAnalysis {

    static Set<String> posWords = new HashSet<String>();
    static Set<String> negWords = new HashSet<String>();
    static int[] stats = new int[6];
    private double minScore;
    private double maxScore;

    public SentimentAnalysis() {
	minScore = 10000;
	maxScore = -10000;
	WordsParser wordsParser = new WordsParser(
		"/home/jimbouris/workspace/negative-words.txt");
	negWords = wordsParser.getSetWithWords();
	wordsParser = new WordsParser(
		"/home/jimbouris/workspace/positive-words.txt");
	posWords = wordsParser.getSetWithWords();
    }

    public double getSentimentScore(String reviewText, int stars) {
	reviewText = reviewText.toLowerCase();
	reviewText = reviewText.trim();
	double negCounter = 0;
	double posCounter = 0;
	String[] words = reviewText.split("[ ,.!\n]+");

	for (int i = 0; i < words.length; i++) {
	    if (posWords.contains(words[i])) {
		if (i == 0)
		    posCounter++;
		else if (i >= 1 && !(words[i - 1].equals("not")))
		    posCounter++;
		else
		    negCounter++;
	    }
	    if (negWords.contains(words[i])) {
		if (i == 0)
		    negCounter++;
		else if (i >= 1 && !(words[i - 1].equals("not")))
		    negCounter++;
		else
		    posCounter++;
	    }
	}
	double score = (posCounter - negCounter);
	if (stars == 1)
	    score *= 0.8;
	else if (stars == 2)
	    score *= 0.9;
	else if (stars == 4)
	    score *= 1.1;
	else if (stars == 5)
	    score *= 1.2;
	return score;
    }
}