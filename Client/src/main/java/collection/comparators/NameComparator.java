package collection.comparators;

import fields.Flat;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Класс наследуемый от Comparator Flat - сортировка по полю name
 */
public class NameComparator implements Comparator<Flat>, Serializable {

    @Override
    public int compare(Flat o1, Flat o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
