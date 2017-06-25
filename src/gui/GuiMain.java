package gui;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import docdata.DocumentData;
import mainengine.MainEngine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class GuiMain extends Application {

    private Stage primaryStage;
    private AnchorPane mainWindow;
    private static MainEngine engine;
    private String query;

    @Override
    public void start(Stage primaryStage) {
	File file = new File("/home/jimbouris/workspace/index.jpeg");
	Image img = new Image(file.toURI().toString());
	this.primaryStage = primaryStage;
	this.primaryStage.setTitle("Welcome to lucene search engine!");
	initMainWindowLayout();

    }

    public void initMainWindowLayout() {
	try {

	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(GuiMain.class.getResource("MainWindow.fxml"));
	    mainWindow = (AnchorPane) loader.load();
	    Scene scene = new Scene(mainWindow);
	    primaryStage.setScene(scene);
	    MainWindowController mainWindowController = loader.getController();
	    mainWindowController.setMainApp(this);
	    primaryStage.show();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void showResultsForQuery(String query) {
	this.query = query;
	try {
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(GuiMain.class.getResource("ResultsWindow.fxml"));
	    AnchorPane page = (AnchorPane) loader.load();
	    Stage resulstWindowStage = new Stage();
	    resulstWindowStage.setTitle("Results Window");
	    resulstWindowStage.initModality(Modality.WINDOW_MODAL);
	    resulstWindowStage.initOwner(primaryStage);
	    Scene scene = new Scene(page);
	    resulstWindowStage.setScene(scene);

	    ResultWindowController controller = loader.getController();
	    controller.setQuery(query);
	    controller.setResultsData(engine.askQuery(query));
	    controller.setTotalHits(engine.getTotalHits());
	    controller.setSuggestions(engine.getSuggestions(query));
	    controller.initSuggestion();
	    controller.setMainApp(this);
	    resulstWindowStage.show();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public Stage getPrimaryStage() {
	return primaryStage;
    }

    public static void main(String[] args) {
	engine = new MainEngine();
	launch(args);
    }

    public void showDetailedReview(DocumentData data) {
	try {
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(GuiMain.class.getResource("ReviewWindow.fxml"));
	    AnchorPane page = (AnchorPane) loader.load();
	    Stage dialogStage = new Stage();
	    dialogStage.setTitle("Detailed Review Window");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);

	    ReviewWindowController controller = loader.getController();
	    controller.setMainApp(this);
	    controller.setQuery(query);
	    controller.setReviewData(data);
	    dialogStage.show();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}