package utils;


import java.util.Locale;
import java.util.ResourceBundle;

public class MainLocale {
    private static ResourceBundle resourceBundle;

    public static void setLocale(Locale locale){
        resourceBundle = ResourceBundle.getBundle("GUI", locale);
        Locale.setDefault(locale);
    }

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static void setResourceBundle(ResourceBundle resourceBundle) {
        MainLocale.resourceBundle = resourceBundle;
    }
}
