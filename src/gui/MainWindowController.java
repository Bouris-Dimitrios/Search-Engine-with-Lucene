package gui;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainWindowController {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField textField;
    @FXML
    private Button searchButton;

    private GuiMain mainApp;

    public MainWindowController() {
    }

    @FXML
    private void initialize() {
	File file = new File("/home/jimbouris/workspace/index.jpeg");
	// Image img = new
	// Image("http://mikecann.co.uk/wp-content/uploads/2009/12/javafx_logo_color_1.jpg");
	Image img = new Image(file.toURI().toString());
	imageView.setImage(img);
	textField.setPromptText("insert your query");
    }

    public void setMainApp(GuiMain mainApp) {
	this.mainApp = mainApp;
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
}