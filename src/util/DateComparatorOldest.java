package util;

import java.util.Comparator;
import java.util.Date;

import docdata.DocumentData;

public class DateComparatorOldest implements Comparator<DocumentData> {

    private int getYear(String date) throws Exception {
	return Integer.parseInt(date.split("-")[0]);
    }

    private int getMonth(String date) throws Exception {
	return Integer.parseInt(date.split("-")[1]);
    }

    private int getDate(String date) throws Exception {
	return Integer.parseInt(date.split("-")[2]);
    }

    public int compare(DocumentData data1, DocumentData data2) {
	try {
	    Date date1 = new Date();
	    date1.setYear(getYear(data1.getDate()));
	    date1.setMonth(getMonth(data1.getDate()));
	    date1.setDate(getDate(data1.getDate()));
	    Date date2 = new Date();
	    date2.setYear(getYear(data2.getDate()));
	    date2.setMonth(getMonth(data2.getDate()));
	    date2.setDate(getDate(data2.getDate()));
	    if (date1.after(date2))
		return 1;
	    return 0;
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
	return 0;
    }
}
