package com.cow205.mycontribution;

import java.util.Comparator;

public class DateComparator implements Comparator<DateText> {

	@Override
	public int compare(DateText lhs, DateText rhs) {
		return rhs.getDate().compareTo(lhs.getDate());
	}

}