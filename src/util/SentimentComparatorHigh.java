package util;

import java.util.Comparator;

import docdata.DocumentData;

public class SentimentComparatorHigh implements Comparator<DocumentData> {
    public int compare(DocumentData data1, DocumentData data2) {
	if (data1.getScore() > data2.getScore())
	    return 0;
	return 1;
    }
}
