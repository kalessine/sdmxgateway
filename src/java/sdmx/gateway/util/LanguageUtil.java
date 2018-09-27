/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import sdmx.gateway.entities.Language;


/**
 *
 * @author James
 */
public class LanguageUtil {

    public static sdmx.gateway.entities.Language EN = new sdmx.gateway.entities.Language();

    public static sdmx.gateway.entities.Language FR = new sdmx.gateway.entities.Language();

    static {
        EN.setLang("en");
        EN.setName("English");
        FR.setLang("fr");
        FR.setName("French");
    }
    public static final Language[] LANGS = new Language[]{EN,FR};
   public static Language lookup(String s) {
        for (int i = 0; i < LANGS.length; i++) {
            if (LANGS[i].getLang().equals(s)) {
                return LANGS[i];
            }
        }
        return null;
    }
}
