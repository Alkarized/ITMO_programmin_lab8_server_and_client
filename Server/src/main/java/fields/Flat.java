package fields;


import utils.MainLocale;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс аппартаментов
 */
@Entity
public class Flat implements Comparable<Flat>, Serializable {
    private static final long serialVersionUID = 12334;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flat")
    @SequenceGenerator(name = "seq_flat", sequenceName = "seq_flat", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @ManyToOne()
    @JoinColumn(name = "creator_username", nullable = false)
    private User user;

    @Column(nullable = false, name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "cooridnates_id", nullable = false)
    private Coordinates coordinates; //Поле не может быть null

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Column(nullable = false)
    private Long area; //Значение поля должно быть больше 0

    @Column(nullable = false, name = "number_of_rooms")
    private Integer numberOfRooms; //Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    private Furnish furnish; //Поле не может быть null

    @Enumerated(EnumType.STRING)
    private View view; //Поле не может быть null

    @Enumerated(EnumType.STRING)
    private Transport transport; //Поле не может быть null

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id", nullable = false)
    private House house; //Поле не может быть null

    public Flat() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Transient
    public String getCreationDate() {
        return new SimpleDateFormat("HH:mm:ss.SSS dd-MM-yyyy").format(creationDate);
    }

    public Long getArea() {
        return area;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Furnish getFurnish() {
        return furnish;
    }

    public View getView() {
        return view;
    }

    public Transport getTransport() {
        return transport;
    }

    public House getHouse() {
        return house;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * Выводит информацию в строков виде об объекте
     */
    @lombok.SneakyThrows
    @Transient
    public String printInfoAboutElement() {
        return "id - " + id + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_name").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + name + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_coords").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + coordinates.getX() + ", " + coordinates.getY() + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_creation").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + getCreationDate() + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_area").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + area + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_number_of_rooms").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + numberOfRooms + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_furnish").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + furnish + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_view").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + view + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_transport").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + transport + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_home_name").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + house.getName() + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_home_year").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + house.getYear() + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_home_number").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + house.getNumberOfFlatsOnFloor() + "\n" +
                new String(MainLocale.getResourceBundle().getString("flat_username").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
                + " - " + user.getUsername() + "\n" +
                "=======================================";
    }

    /**
     * Метод сравнения 2 объектов класса Flat
     */
    @Override
    public int compareTo(Flat o) {
        return this.getName().compareTo(o.getName());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Flat(User user, String name, Coordinates coordinates, Date creationDate, Long area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        this.user = user;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.furnish = furnish;
        this.view = view;
        this.transport = transport;
        this.house = house;
    }
}



