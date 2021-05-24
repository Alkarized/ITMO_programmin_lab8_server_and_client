package fields;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Класс для координат
 */
@Entity
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 12245;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_coords")
    @SequenceGenerator(name = "seq_coords", sequenceName = "seq_coords", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, name = "x")
    private Integer x; //Поле не может быть null

    @OneToOne(mappedBy = "coordinates", orphanRemoval = true)
    private Flat flat;

    @Column(nullable = false, name = "y")
    private Float y; //Максимальное значение поля: 687, Поле не может быть null

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;

    }

    public void setY(Float y) {
        this.y = y;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinates() {
    }

    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }
}
