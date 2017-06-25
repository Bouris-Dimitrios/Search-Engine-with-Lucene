package analyzers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordsParser {
    private BufferedReader bufferedReader;
    private final String filePath;

    public WordsParser(String path) {
	filePath = path;
	openFile();
    }

    private void openFile() {
	try {
	    bufferedReader = new BufferedReader(new FileReader(filePath));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

    }

    public Set<String> getSetWithWords() {
	String currentLine;
	Set<String> wordsSet = new HashSet<String>();
	try {
	    while ((currentLine = bufferedReader.readLine()) != null) {
		wordsSet.add(currentLine);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return wordsSet;
    }

}
