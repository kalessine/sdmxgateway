package org.opensdmx.adapter.olap4j.util;

import java.util.HashMap;
import java.util.Map;

/**
 * SDMX 2.1 suppurts a number of time formats. This class implements conversions
 * from the olap4j unique name format to the SDMX formats. Only those
 * conversions are implemented yet which were needed by the current
 * requirements.
 * 
 * 
 * 
 * SDMX 2.1 timeformat supports these formats:
 * 
 * - SDMX specific: ReportingYearType ReportingSemesterType
 * ReportingTrimesterType ReportingQuarterType ReportingMonthType
 * ReportingWeekType ReportingDayType
 * 
 * - generic: GregorianTimePeriodType xs:dateTime
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class WhatTime {

	public static final String TIME = "Time";
	//public static final String DATE_YEAR = "Date.Year";
	public static final String DATE_YEAR = "Year";
	public static final String SPLIT_REGEXP = "\\]\\.\\[";
	public static final String YEAR_REGEXP = "^[0-9][0-9][0-9][0-9]";
	public static final String QUARTER_REGEXP = "^(Q1|Q2|Q3|Q4)";
	public static final String MONTH_REGEXP = "^(January|February|March|April|May|June|July|August|September|October|November|December)";
	public static final Map<String, String> MONTH_MAP = new HashMap<String, String>();

	static {
		MONTH_MAP.put("January", "01");
		MONTH_MAP.put("February", "02");
		MONTH_MAP.put("March", "03");
		MONTH_MAP.put("April", "04");
		MONTH_MAP.put("May", "05");
		MONTH_MAP.put("June", "06");
		MONTH_MAP.put("July", "07");
		MONTH_MAP.put("August", "08");
		MONTH_MAP.put("September", "09");
		MONTH_MAP.put("October", "10");
		MONTH_MAP.put("November", "11");
		MONTH_MAP.put("December", "12");
	}

	/**
	 * Supports this format [Time].[1997].[Q2].[April]
	 * 
	 * Example IN: [Time].[1997].[Q2].[April] OUT 1997-04
	 * 
	 * @param olap4jTime
	 * @return
	 */
	public String olapTime2Sdmx(String olap4jTime) {
		String sdmxTime = "";
		if (timeYearQuarterMonth(olap4jTime)) {
			sdmxTime = convert2TimeYearQuarterMonth(olap4jTime);
		}
		if (dateYear(olap4jTime)) {
			sdmxTime = convert2TimeYear(olap4jTime);
		}

		return sdmxTime;
	}

	/**
	 * IN "[Date.Year].[1961]";
	 * 
	 * OUT true or false
	 * 
	 * @param olap4jTime
	 * @return true if this is a date year format, false if else
	 */
	private boolean dateYear(String olap4jTime) {
		String[] elements = splitAndRemoveBrackets(olap4jTime);
		boolean hasDateYearFormat = true;
		if (elements.length != 2) {
			hasDateYearFormat = false;
		}
		if (hasDateYearFormat && !elements[0].equals(DATE_YEAR)) {
			hasDateYearFormat = false;
		}
		if (hasDateYearFormat && !elements[1].matches(YEAR_REGEXP)) {
			hasDateYearFormat = false;
		}
		return hasDateYearFormat;
	}

	private String convert2TimeYear(String olap4jTime) {
		String[] elements = splitAndRemoveBrackets(olap4jTime);
		return elements[1];
	}

	/**
	 * 
	 * [Time].[1997].[Q2].[April]
	 * 
	 * 
	 * @param olap4jTime
	 * @return is timeYearQuarterMonth
	 */
	private boolean timeYearQuarterMonth(String olap4jTime) {
		String[] elements = splitAndRemoveBrackets(olap4jTime);
		boolean hasTimeYearQuarterMonthFormat = true;
		if (elements.length != 4) {
			hasTimeYearQuarterMonthFormat = false;
		}
		if (hasTimeYearQuarterMonthFormat && !elements[0].equals(TIME)) {
			hasTimeYearQuarterMonthFormat = false;
		}
		if (hasTimeYearQuarterMonthFormat && !elements[1].matches(YEAR_REGEXP)) {
			hasTimeYearQuarterMonthFormat = false;
		}
		if (hasTimeYearQuarterMonthFormat && !elements[2].matches(QUARTER_REGEXP)) {
			hasTimeYearQuarterMonthFormat = false;
		}
		if (hasTimeYearQuarterMonthFormat && !elements[3].matches(MONTH_REGEXP)) {
			hasTimeYearQuarterMonthFormat = false;
		}
		return hasTimeYearQuarterMonthFormat;
	}

	private String convert2TimeYearQuarterMonth(String olap4jTime) {
		String[] elements = splitAndRemoveBrackets(olap4jTime);
		String reportingMonth = elements[1] + "-" + MONTH_MAP.get(elements[3]);
		return reportingMonth;
	}

	public String[] splitAndRemoveBrackets(String olap4jTime) {
		String[] elements = olap4jTime.split(SPLIT_REGEXP);
		removeBrackets(elements);
		return elements;
	}

	public void removeBrackets(String[] arrayWithBracketStrings) {
		for (int i = 0; i < arrayWithBracketStrings.length; i++) {
			String element = arrayWithBracketStrings[i];
			if (element.startsWith("[")) {
				element = element.substring(1, element.length());

			}
			if (element.endsWith("]")) {
				element = element.substring(0, element.length() - 1);
			}
			arrayWithBracketStrings[i] = element;
		}
	}

}
