/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import sdmx.gateway.entities.Languages;

/**
 *
 * @author James
 */
public class LanguageUtil {

    public static sdmx.gateway.entities.Languages EN = new sdmx.gateway.entities.Languages();

    public static sdmx.gateway.entities.Languages FR = new sdmx.gateway.entities.Languages();

    static {
        EN.setLang("en");
        EN.setName("English");
        FR.setLang("fr");
        FR.setName("French");
    }
    public static final Languages[] LANGS = new Languages[]{EN,FR};
   public static Languages lookup(String s) {
        for (int i = 0; i < LANGS.length; i++) {
            if (LANGS[i].getLang().equals(s)) {
                return LANGS[i];
            }
        }
        return null;
    }
}
