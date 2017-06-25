package mainengine;

import java.net.URL;

import analyzers.CustomSpellChecker;
import docdata.DocumentData;
import Searcher.Searcher;
import fileSplitter.Splitter;
import indexer.Indexer;
import javafx.collections.ObservableList;

public class MainEngine {
    private Splitter fileSplitter;
    private Indexer indexer;
    private Searcher searcher;

    public MainEngine() {
	// createIndex();
	searcher = new Searcher();
    }

    private void createIndex() {
	fileSplitter = new Splitter();
	indexer = new Indexer();
	indexer.buildIndex(fileSplitter.extractDocumentDataList());
    }

    public ObservableList<DocumentData> askQuery(String query) {
	return searcher.askQuery(query);
    }

    public int getTotalHits() {
	return searcher.getTotalHits();
    }

    public String[] getSuggestions(String askedQuery) {
	CustomSpellChecker spellChecker = new CustomSpellChecker();
	return spellChecker.getSugestions(askedQuery);
    }
}
