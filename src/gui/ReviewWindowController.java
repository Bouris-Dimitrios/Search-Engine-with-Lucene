package gui;

import docdata.DocumentData;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ReviewWindowController {
    @FXML
    private Text reviewId;
    @FXML
    private Text bussinessId;
    @FXML
    private Text userId;
    @FXML
    private Text date;
    @FXML
    private Text rating;
    @FXML
    private TextFlow sentiment;
    @FXML
    private TextFlow review;
    private GuiMain mainApp;
    private String query;

    public ReviewWindowController() {
    }

    @FXML
    private void initialize() {

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

    private void boldText(Text text) {
	text.setStyle("-fx-font-weight:bold;");
	text.setFill(Color.CORAL);
    }

    private void boldQueryWords(DocumentData data) {
	String[] reviewArray = data.getText().replaceAll(" \n", " ")
		.split("[ ,.]+");
	String[] queryArray = query.split("[ ,.]+");
	for (int i = 0; i < reviewArray.length; i++) {
	    Text reviewText = new Text(reviewArray[i] + " ");
	    for (int j = 0; j < queryArray.length; j++)
		if (checkIfStringShouldBeBold(reviewArray[i], queryArray[j]))
		    boldText(reviewText);
	    review.getChildren().add(reviewText);
	}
    }

    private void addSentiment(DocumentData data) {
	Text sentimentText = new Text(data.getSentiment());
	sentimentText.setStyle("-fx-font-weight:bold ;");
	if (data.getSentiment().equals("Positive"))
	    sentimentText.setFill(Color.GREEN);
	else if (data.getSentiment().equals("Negative"))
	    sentimentText.setFill(Color.RED);
	else
	    sentimentText.setFill(Color.BLUE);

	sentiment.getChildren().addAll(sentimentText);
    }

    private void addRating(DocumentData data) {
	rating.setText(data.getStarsProperty().getValue());
	rating.setStyle("-fx-font-weight:bold;");
	rating.setFill(Color.GOLDENROD);
	rating.setFont(Font
		.font(java.awt.Font.SERIF, FontWeight.EXTRA_BOLD, 40));
    }

    public void setReviewData(DocumentData data) {
	reviewId.setText(data.getReviewId());
	bussinessId.setText(data.getBusinessId());
	userId.setText(data.getUserId());
	date.setText(data.getDate());
	addRating(data);
	addSentiment(data);
	boldQueryWords(data);
    }

    public void setMainApp(GuiMain mainApp) {
	this.mainApp = mainApp;
    }

    public void setQuery(String query) {
	this.query = query;
    }

}