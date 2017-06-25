package fileSplitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import analyzers.SentimentAnalysis;
import docdata.DocumentData;

public class Splitter {
    private File file;
    private FileInputStream fileStream;
    private final String folderPath = "/home/jimbouris/workspace/";
    // private final String filePath =
    // "/home/jimbouris/workspace/yelp_academic_dataset_review.json";
    private JSONParser parser;
    private List<DocumentData> allDocumentsData;
    private SentimentAnalysis sentimentAnalyzer;
    private int numOfFiles = 79;
    private String path;

    public Splitter() {
	parser = new JSONParser();
	sentimentAnalyzer = new SentimentAnalysis();
	allDocumentsData = new ArrayList<DocumentData>();
    }

    private char getAsciiValueOf(int value) {
	return (char) value;
    }

    private String createPath(int i) {
	if (i < 26)
	    path = folderPath + "newa" + getAsciiValueOf(i + 97);
	else if (i < 52)
	    path = folderPath + "newb" + getAsciiValueOf(i + 97 - 26);
	else if (i < 78)
	    path = folderPath + "newc" + getAsciiValueOf(i + 97 - 52);
	else
	    return folderPath + "newda";
	return path;
    }

    private void checkIfFileExists(File file) {
	if (!file.exists()) {
	    System.out.println(file.getAbsolutePath() + " does not exist.");
	    return;
	}
    }

    private void openFile(int i) throws FileNotFoundException {
	String path = createPath(i);
	file = new File(path);
	checkIfFileExists(file);
	fileStream = new FileInputStream(file);
    }

    private void readJSONReviewsAndAddThemToList() throws IOException {
	int readChar;
	String JSONreview = "";
	while ((readChar = fileStream.read()) != -1) {
	    JSONreview += (char) readChar;
	    if (JSONreview.contains("business_id") && (char) readChar == '}') {
		allDocumentsData.add(parseDocumentDataFromJSONString(JSONreview
			.toString()));
		JSONreview = "";
	    }
	}
    }

    public List<DocumentData> extractDocumentDataList() {
	try {
	    for (int i = 0; i <= 80; i++) {
		openFile(i);
		readJSONReviewsAndAddThemToList();
		fileStream.close();

		System.out.println("Size: " + allDocumentsData.size());
		System.out.println("File " + path + "parsed");
	    }
	    System.out
		    .println("-------------DOCUMENT DATA for file -------------------");

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return allDocumentsData;
    }

    private DocumentData parseDocumentDataFromJSONString(String JSONreview) {
	try {
	    Object obj;
	    obj = parser.parse(JSONreview);
	    JSONObject jsonObject = (JSONObject) obj;
	    Long stars = (Long) jsonObject.get("stars");
	    Double score = sentimentAnalyzer.getSentimentScore(
		    (String) jsonObject.get("text"), stars.intValue());

	    DocumentData docData = new DocumentData(
		    (String) jsonObject.get("review_id"),
		    (String) jsonObject.get("text"),
		    (Long) jsonObject.get("stars"),
		    (String) jsonObject.get("user_id"),
		    (String) jsonObject.get("date"),
		    (String) jsonObject.get("business_id"), score);
	    return docData;
	} catch (ParseException e) {
	    e.printStackTrace();
	    return new DocumentData();
	}
    }
}
