package utils;

import lombok.Getter;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainLocale {
    @Getter
    private static ResourceBundle resourceBundle;

    public static void setLocale(Locale locale){
        resourceBundle = ResourceBundle.getBundle("GUI", locale);
        Locale.setDefault(locale);
    }

    @SneakyThrows
    public static String getValue(String key){
        String ans = resourceBundle.getString(key);
        switch (Locale.getDefault().getCountry()) {
            case "RU":
                ans = new String(ans.getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251");
                break;
            case "PT":
                ans = new String(ans.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                break;
            case "LV":
                ans = new String(ans.getBytes(StandardCharsets.ISO_8859_1), "ISO_8859-4");
                break;
        }
        return ans;
    }


}
