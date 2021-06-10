package utils;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainLocale {
    @Getter
    private static ResourceBundle resourceBundle;

    public static void setLocale(Locale locale){
        resourceBundle = ResourceBundle.getBundle("GUI", locale);
        Locale.setDefault(locale);
    }

}
