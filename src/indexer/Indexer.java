package indexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

import docdata.DocumentData;

public class Indexer {
    private IndexWriter indexWriter;

    public Indexer() {
	try {
	    Directory indexDir = FSDirectory.open(new File(
		    "/home/jimbouris/workspace/indexDirBig"));
	    // Directory indexDir = FSDirectory.open(new
	    // File("/home/jimbouris/workspace/indexDir"));
	    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41,
		    new EnglishAnalyzer());
	    config.setOpenMode(config.getOpenMode());
	    indexWriter = new IndexWriter(indexDir, config);

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    private void indexDocument(DocumentData docData) {

	Document document = new Document();
	document.add(new TextField("text", docData.getText(), Field.Store.YES));
	document.add(new StringField("review_id", docData.getReviewId(),
		Field.Store.YES));
	document.add(new TextField("stars", docData.getStars().toString(),
		Field.Store.YES));
	document.add(new StringField("user_id", docData.getUserId(),
		Field.Store.YES));
	document.add(new StringField("date", docData.getDate(), Field.Store.YES));
	document.add(new StringField("business_id", docData.getBusinessId(),
		Field.Store.YES));
	document.add(new StringField("score", docData.getScore().toString(),
		Field.Store.YES));

	try {
	    indexWriter.addDocument(document);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void buildIndex(List<DocumentData> docData) {
	for (int i = 0; i < docData.size(); i++) {
	    indexDocument(docData.get(i));
	    docData.remove(i);
	}
	try {
	    indexWriter.commit();
	    indexWriter.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public IndexWriter getWritter() {
	return indexWriter;
    }

}
