package analyzers;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SnippetCreator {
    private final int MAXWORDSINSNIPPET = 30;
    private final int MAXWORDSINLINE = 18;
    private String text;
    private String query;

    public SnippetCreator(String text, String query) {
	this.text = text;
	this.query = query;
    }

    private boolean checkIfStringShouldBeBold(String word1, String word2) {
	if (word1.length() > 3) {
	    if (word1.toLowerCase().contains(word2.toLowerCase())
		    || word2.toLowerCase().contains(word1.toLowerCase()))
		return true;
	} else {
	    if (word1.toLowerCase().equals(word2.toLowerCase())
		    || word2.toLowerCase().equals(word1.toLowerCase()))
		return true;
	}
	return false;
    }

    private int findIndexOfQueryFirstWord(String[] queryWords) {
	for (int i = 0; i < queryWords.length; i++)
	    if (text.indexOf(queryWords[i]) != -1)
		return i;
	return 0;
    }

    private int findSnippetStart(String[] textWords, String[] queryWords) {
	int indexOfFirstWord = findIndexOfQueryFirstWord(queryWords);
	int textStart = 0;
	for (int i = 0; i < textWords.length; i++)
	    if (textWords[i].equals(queryWords[indexOfFirstWord])) {
		textStart = i;
		break;
	    }
	for (int i = 3; i > 0; i--)
	    if (textStart - i >= 0)
		return textStart - i;

	return textStart;
    }

    private void boldText(Text text) {
	text.setStyle("-fx-font-style: italic;" + "-fx-font-weight:bold");
	text.setFont(Font.font("Helvetica", FontPosture.ITALIC, 12));
	// text.setFill(Color.CORAL);
    }

    private void addFullStops(TextFlow snippet, String[] textWords,
	    String[] queryWords) {
	if (textWords.length > queryWords.length)
	    snippet.getChildren().add(new Text("..."));
    }

    private void addNewLineCharacter(TextFlow snippet, int wordsInSnippet) {
	if (wordsInSnippet == MAXWORDSINLINE)
	    snippet.getChildren().add(new Text("\n"));
    }

    private TextFlow createSnippet(String[] textWords, String[] queryWords) {
	int textStart = findSnippetStart(textWords, queryWords);

	TextFlow snippet = new TextFlow();
	int wordsInSnippet = 0;
	addFullStops(snippet, textWords, queryWords);
	for (int i = textStart; i < textWords.length; i++) {
	    wordsInSnippet++;
	    Text snippetText = new Text(textWords[i] + " ");
	    for (int j = 0; j < queryWords.length; j++)
		if (checkIfStringShouldBeBold(textWords[i], queryWords[j]))
		    boldText(snippetText);
	    snippet.getChildren().add(snippetText);
	    addNewLineCharacter(snippet, wordsInSnippet);

	    if (wordsInSnippet > MAXWORDSINSNIPPET)
		break;
	}
	addFullStops(snippet, textWords, queryWords);
	return snippet;
    }

    public TextFlow getTextSnippet() {
	String queryWords[] = query.split("[ ,.!\n]+");
	String textWords[] = text.split("[ ,.!\n]+");
	return createSnippet(textWords, queryWords);
    }
}
