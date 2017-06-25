package gui;

import java.io.File;

import util.DateComparatorNewest;
import util.DateComparatorOldest;
import util.SentimentComparatorHigh;
import util.SentimentComparatorLow;
import util.StarsCustomComparatorHigh;
import util.StarsCustomComparatorLow;
import docdata.DocumentData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ResultWindowController {
    @FXML
    private Button searchButton;
    private GuiMain mainApp;
    @FXML
    private TextField textField;
    @FXML
    private ComboBox<String> sortByComboBox;
    @FXML
    private ComboBox<String> pageComboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextFlow textFlow1;
    @FXML
    private TextFlow textFlow2;
    @FXML
    private TextFlow textFlow3;
    @FXML
    private TextFlow textFlow4;
    @FXML
    private TextFlow textFlow5;
    @FXML
    private TextFlow textFlow6;
    @FXML
    private TextFlow textFlow7;
    @FXML
    private TextFlow textFlow8;
    @FXML
    private TextFlow textFlow9;
    @FXML
    private TextFlow textFlow10;
    @FXML
    private TextFlow suggestion;
    @FXML
    private Text results;

    private String query;
    private int totalHits;
    private int currentPage = 1;
    private int pages;
    private String[] suggestions;
    public ObservableList<String> sortOptions = FXCollections
	    .observableArrayList();
    public ObservableList<String> pageValues = FXCollections
	    .observableArrayList();
    private ObservableList<DocumentData> documents;

    public ResultWindowController() {
	;
    }

    @FXML
    private void initialize() {
	File file = new File("/home/jimbouris/workspace/index.jpeg");
	Image img = new Image(file.toURI().toString());
	imageView.setImage(img);
	textField.setPromptText("insert your query");
	sortOptions.addAll("Sentiment High", "Sentiment Low", "Rating High",
		"Rating Low", "Date Newest", "Date Oldest");
	sortByComboBox.getItems().addAll(sortOptions);
	sortByComboBox.setValue("Sentiment High");
	// suggestion.setVisible(false);
    }

    public void initSuggestion() {
	if (suggestions != null && suggestions.length > 0 && totalHits < 10) {
	    Text text0 = new Text("Did you mean:  ");
	    Text text1 = new Text(suggestions[0]);
	    text1.setStyle("-fx-font-weight:bold ;");
	    text1.setFill(Color.PURPLE);
	    Text text2 = new Text(" ?");
	    suggestion.getChildren().addAll(text0, text1, text2);
	    suggestion.setVisible(true);
	    return;
	}
	suggestion.setVisible(false);
    }

    @FXML
    public void handleClickOnSuggestion() {
	String query = suggestions[0];
	if (query.equals("") || (suggestion.isVisible() == false))
	    return;
	mainApp.showResultsForQuery(query);
    }

    @FXML
    public void handleSearchButton() {
	String query = textField.getText();
	if (query.equals(""))
	    return;
	mainApp.showResultsForQuery(query);
    }

    @FXML
    public void handleEnterButton(KeyEvent ke) {
	if (ke.getCode().equals(KeyCode.ENTER))
	    handleSearchButton();
    }

    @FXML
    public void handleSortByComboBoxSelection() {
	String selectedItem = sortByComboBox.getSelectionModel()
		.getSelectedItem();
	if (selectedItem == null)
	    return;
	if (selectedItem.equals("Rating High"))
	    documents.sort(new StarsCustomComparatorLow());
	else if (selectedItem.equals("Rating Low"))
	    documents.sort(new StarsCustomComparatorHigh());
	else if (selectedItem.equals("Sentiment High"))
	    documents.sort(new SentimentComparatorHigh());
	else if (selectedItem.equals("Sentiment Low"))
	    documents.sort(new SentimentComparatorLow());
	else if (selectedItem.equals("Date Newest"))
	    documents.sort(new DateComparatorNewest());
	else if (selectedItem.equals("Date Oldest"))
	    documents.sort(new DateComparatorOldest());
	sortByComboBox.setValue(selectedItem);
	setDataOfPage();
    }

    @FXML
    public void handleCLickOnTableView1() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i);
    }

    public void handleCLickOnTableView2() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 1);
    }

    public void handleCLickOnTableView3() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 2);
    }

    public void handleCLickOnTableView4() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 3);
    }

    public void handleCLickOnTableView5() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 4);
    }

    public void handleCLickOnTableView6() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 5);
    }

    public void handleCLickOnTableView7() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 6);
    }

    public void handleCLickOnTableView8() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 7);
    }

    public void handleCLickOnTableView9() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 8);
    }

    public void handleCLickOnTableView10() {
	int i = (currentPage - 1) * 10;
	showDetailedReview(i + 9);
    }

    private void showDetailedReview(int i) {
	if (i > documents.size() - 1)
	    return;
	mainApp.showDetailedReview(documents.get(i));
    }

    private void fillPagesCombobox() {
	pages = (int) Math.ceil(documents.size() / 10);
	for (int i = 0; i < pages; i++)
	    pageValues.add((String.valueOf(i + 1)));
	pageComboBox.getItems().addAll(pageValues);
	pageComboBox.setValue(String.valueOf(currentPage));
    }

    public void setResultsData(ObservableList<DocumentData> documents) {
	this.documents = documents;
	documents.sort(new SentimentComparatorHigh());
	fillPagesCombobox();
	setDataOfPage();
    }

    public void setSuggestions(String[] suggestions) {
	this.suggestions = suggestions;
    }

    @FXML
    public void handlePageComboBox() {
	String selection = pageComboBox.getSelectionModel().getSelectedItem();
	if (selection == null)
	    return;
	pageComboBox.setValue(selection);
	currentPage = Integer.parseInt(selection);
	setDataOfPage();
    }

    private void clearPage() {
	textFlow1.getChildren().clear();
	textFlow2.getChildren().clear();
	textFlow3.getChildren().clear();
	textFlow4.getChildren().clear();
	textFlow5.getChildren().clear();
	textFlow6.getChildren().clear();
	textFlow7.getChildren().clear();
	textFlow8.getChildren().clear();
	textFlow9.getChildren().clear();
	textFlow10.getChildren().clear();
    }

    private void fillTextFLow(TextFlow textFlow, int i) {
	if (i > documents.size() - 1)
	    return;
	DocumentData docData = documents.get(i);

	textFlow.setVisible(true);
	Text text0 = new Text("Review id: ");
	Text text1 = new Text(docData.getReviewId() + "\n");
	text1.setStyle("-fx-font-weight:bold ;");
	Text text3 = new Text(docData.getSentiment() + "\t");
	text3.setStyle("-fx-font-weight:bold ;");
	if (docData.getSentiment().equals("Positive"))
	    text3.setFill(Color.GREEN);
	else if (docData.getSentiment().equals("Negative"))
	    text3.setFill(Color.RED);
	else
	    text3.setFill(Color.BLUE);
	Text text5 = new Text(docData.getStarsProperty().getValue() + "\n");
	text5.setFill(Color.GOLDENROD);
	text5.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.BOLD, 20));
	textFlow.getChildren().addAll(text0, text1, text3, text5);
	textFlow.getChildren().add(docData.getTextSnippet(query));
    }

    private void setDataOfPage() {
	clearPage();
	int i = (currentPage - 1) * 10;
	fillTextFLow(textFlow1, (i));
	fillTextFLow(textFlow2, (i + 1));
	fillTextFLow(textFlow3, (i + 2));
	fillTextFLow(textFlow4, (i + 3));
	fillTextFLow(textFlow5, (i + 4));
	fillTextFLow(textFlow6, (i + 5));
	fillTextFLow(textFlow7, (i + 6));
	fillTextFLow(textFlow8, (i + 7));
	fillTextFLow(textFlow9, (i + 8));
	fillTextFLow(textFlow10, (i + 9));
    }

    public void setMainApp(GuiMain mainApp) {
	this.mainApp = mainApp;
	results.setText("Found " + totalHits + " results.");
    }

    public void setQuery(String query) {
	this.query = query;
	textField.setPromptText(query);

    }

    public void setTotalHits(int totalHits) {
	this.totalHits = totalHits;
    }
}