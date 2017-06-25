package analyzers;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class CustomSpellChecker {
    private SpellChecker spellChecker;
    private Directory dictionaryDirectory;
    private final int NUMOFSUGGESTIONS = 5;
    private final float ACCURACY = 0.2f;

    public CustomSpellChecker(IndexReader reader) {
	try {
	    IndexWriterConfig writerConfig = new IndexWriterConfig(
		    Version.LUCENE_4_10_4, new EnglishAnalyzer());
	    dictionaryDirectory = FSDirectory.open(new File(
		    "/home/jimbouris/workspace/DictionaryDirectory"));
	    spellChecker = new SpellChecker(dictionaryDirectory);
	    spellChecker.indexDictionary(new PlainTextDictionary(new File(
		    "/usr/share/dict/american-english")), writerConfig, true);

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public CustomSpellChecker() {
	try {
	    dictionaryDirectory = FSDirectory.open(new File(
		    "/home/jimbouris/workspace/DictionaryDirectory"));
	    spellChecker = new SpellChecker(dictionaryDirectory);
	} catch (final Exception e) {
	    e.printStackTrace();
	}

    }

    public String[] getSugestions(String query) {
	try {
	    return spellChecker.suggestSimilar(query, NUMOFSUGGESTIONS,
		    ACCURACY);
	} catch (IOException e) {
	    e.printStackTrace();
	    return new String[0];
	}
    }
}
