package util;

import java.util.Comparator;

import docdata.DocumentData;

public class StarsCustomComparatorHigh implements Comparator<DocumentData> {
    public int compare(DocumentData data1, DocumentData data2) {
	if (Integer.parseInt(data1.getStars()) > Integer.parseInt(data2
		.getStars()))
	    return 1;
	return 0;
    }
}
