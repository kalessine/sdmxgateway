package org.opensdmx.adapter.olap4j.external;

public class Description {

	public enum Language {
		// The languages used in FAO
		en, fr, es, zh, ru, ar;
		public static Language parse(String lang) {
			Language parsed = null;
			for (Language i : Language.values()) {
				if (lang.equals(i.toString())) {
					parsed = i;
				}
			}
			return parsed;
		}
	}

	private String desciption;
	private Language language;

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
