package docdata;

import java.util.Properties;

import analyzers.SnippetCreator;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class DocumentData {
    private String reviewId;
    private String text;
    private Long stars;
    private String userId;
    private String date;
    private String businessId;

    private Double score;

    public DocumentData(String reviewId, String text, Long stars,
	    String userId, String date, String businessId, double score) {
	this.reviewId = reviewId;
	this.text = text;
	this.setStars(stars);
	this.userId = userId;
	this.date = date;
	this.businessId = businessId;
	this.score = score;

    }

    public DocumentData() {
    }

    public void setScore(double score) {
	this.score = score;
    }

    public StringProperty getStarsProperty() {
	String starsString = "";
	for (int i = 0; i < stars.intValue(); i++)
	    starsString += "٭";
	return new SimpleStringProperty(starsString);
    }

    public StringProperty getSentimentProperty() {
	return new SimpleStringProperty(getSentiment());
    }

    public String getSentiment() {
	if (score > 0.0)
	    return "Positive";
	else if (score < 0.0)
	    return "Negative";
	return "Neutral";
    }

    public StringProperty getTextProperty() {
	return new SimpleStringProperty(text);
    }

    public String getStars() {
	return String.valueOf(stars.intValue());
    }

    public String getReviewId() {
	return reviewId;
    }

    public String getText() {
	return text;
    }

    public String getDate() {
	return date;
    }

    public String getUserId() {
	return userId;
    }

    public Double getScore() {
	return score;
    }

    public String getBusinessId() {
	return businessId;
    }

    public void setStars(Long stars) {
	this.stars = stars;
    }

    public TextFlow getTextSnippet(String query) {
	return new SnippetCreator(text, query).getTextSnippet();
    }
}
