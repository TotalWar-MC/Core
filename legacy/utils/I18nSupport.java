package io.github.eirikh1996.movecraftworldborder;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class I18nSupport {
    private static Properties languageFile;

    public static boolean initialize(){
        File langFile = new File(MWBMain.getInstance().getDataFolder().getAbsolutePath() + "/localisation/mwblang_" + Settings.locale + ".properties");
        try {
        languageFile = new Properties();
        languageFile.load(new FileInputStream(langFile));
        } catch (Exception e){
            e.printStackTrace();
            MWBMain.getInstance().getServer().getPluginManager().disablePlugin(MWBMain.getInstance());
            return false;
        }
        return true;
    }
    public static String getInternationalizedString(String key){
        return languageFile.getProperty(key) != null ? languageFile.getProperty(key) : key;
    }
}
