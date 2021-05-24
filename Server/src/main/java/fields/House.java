package fields;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс Дома
 */
@Entity
public class House implements Serializable {
    private static final long serialVersionUID = 8634;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_house")
    @SequenceGenerator(name = "seq_house", sequenceName = "seq_house", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(mappedBy = "house", orphanRemoval = true)
    private Flat flat;

    private String name; //Поле может быть null

    @Column(nullable = false)
    private Long year; //Значение поля должно быть больше 0

    @Column(nullable = false, name = "number_of_flats_on_floor")
    private Long numberOfFlatsOnFloor; //Значение поля должно быть больше 0

    public String getName() {
        return name;
    }

    public Long getYear() {
        return year;
    }

    public Long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public void setName(String name) {
        this.name = name;

    }

    public House() {

    }

    public House(String name, Long year, Long numberOfFlatsOnFloor) {
        this.name = name;
        this.year = year;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public void setYear(Long year) {

        this.year = year;

    }

    public void setNumberOfFlatsOnFloor(Long numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
