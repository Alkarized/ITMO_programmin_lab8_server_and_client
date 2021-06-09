package utils;

import fields.Flat;
import javafx.scene.control.TableColumn;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class HashFields {
    @Getter
    @Setter
    private HashMap<String, TableColumn<Flat, ?>> hash = new HashMap<>();

    public void registerNewField(String string, TableColumn<Flat, ?> tableColumn) {
        hash.put(string, tableColumn);
    }

}
