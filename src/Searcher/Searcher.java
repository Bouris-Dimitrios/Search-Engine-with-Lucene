package Searcher;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import analyzers.CustomSpellChecker;
import docdata.DocumentData;

public class Searcher {
    private final String INDEXDIR = "/home/nikospaxos/indexDirSmall";
    public static final String FIELD_CONTENTS = "text";
    public static final int HITSPERPAGE = 200;
    private IndexReader indexReader;
    private IndexSearcher indexSearcher;
    private Analyzer analyzer;
    private IndexSearcher searcher;
    private MultiFieldQueryParser queryParser;
    private TopScoreDocCollector collector;
    private CustomSpellChecker spellChecker;
    private int totalHits;
    private String[] suggestions;

    public Searcher() {
	totalHits = 0;
	Directory directory;
	try {
	    directory = FSDirectory.open(new File(
		    "/home/jimbouris/workspace/indexDirBig"));
	    indexReader = IndexReader.open(directory);
	    searcher = new IndexSearcher(indexReader);
	    spellChecker = new CustomSpellChecker(indexReader);
	    System.out.println("indexed docs: " + indexReader.numDocs());

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public int getTotalHits() {
	return totalHits;
    }

    public void printTopDocs(ScoreDoc[] topDocs) {
	int docId = topDocs[0].doc;
	try {
	    Document d = indexSearcher.doc(docId);
	    System.out.println("Stars :" + d.get("stars ") + " Text: "
		    + d.get("text"));
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private DocumentData getDocumentData(int docId) throws IOException {
	Document document = searcher.doc(docId);
	return new DocumentData(document.get("review_id"),
		document.get("text"), Long.parseLong(document.get("stars")),
		document.get("user_id"), document.get("date"),
		document.get("business_id"), Double.parseDouble(document
			.get("score")));
    }

    private void computeSuggestionsFromQuery(String askedQuery) {
	spellChecker = new CustomSpellChecker();
	suggestions = spellChecker.getSugestions(askedQuery);
    }

    public ObservableList<DocumentData> askQuery(String askedQuery) {
	totalHits = 0;
	ObservableList<DocumentData> hitsList = FXCollections
		.observableArrayList();
	try {
	    // computeSuggestionsFromQuery(askedQuery);

	    collector = TopScoreDocCollector.create(HITSPERPAGE, true);
	    queryParser = new MultiFieldQueryParser(new String[] { "text",
		    "user_id", "business_id", "review_id", "date", "stars" },
		    new EnglishAnalyzer());
	    Query query = queryParser.parse(askedQuery);
	    searcher.search(query, collector);

	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    totalHits = collector.getTotalHits();
	    for (int i = 0; i < hits.length; ++i)
		hitsList.add(getDocumentData(hits[i].doc));
	} catch (IOException | ParseException e) {
	    e.printStackTrace();
	}
	return hitsList;
    }

}
