package fields;


import lombok.SneakyThrows;
import utils.MainLocale;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс аппартаментов
 */
public class Flat implements Comparable<Flat>, Serializable {
    private static final long serialVersionUID = 12334;

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long area; //Значение поля должно быть больше 0
    private Integer numberOfRooms; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private View view; //Поле не может быть null
    private Transport transport; //Поле не может быть null
    private House house; //Поле не может быть nul

    private User user;

    public Flat() {
        this.creationDate = new Date();
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

    public String getCreationDate() {
        return new SimpleDateFormat("HH:mm:ss.SSS dd-MM-yyyy").format(creationDate);
    }

    public Date getCreationDateClear(){
        return creationDate;
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

    public boolean setName(String name) {
        if (name == null || name.equals("")) {
            return false;
        } else {
            this.name = name;
            return true;
        }
    }

    public boolean setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return true;
    }

    public boolean setArea(Long area) {
        if (area > 0) {
            this.area = area;
            return true;
        } else {
            return false;
        }
    }

    public boolean setNumberOfRooms(Integer numberOfRooms) {
        if (numberOfRooms > 0) {
            this.numberOfRooms = numberOfRooms;
            return true;
        } else {
            return false;
        }
    }

    public boolean setFurnish(Furnish furnish) {
        if (furnish == null) {
            return false;
        } else {
            this.furnish = furnish;
            return true;
        }
    }

    public boolean setView(View view) {
        if (view == null) {
            return false;
        } else {
            this.view = view;
            return true;
        }
    }

    public boolean setTransport(Transport transport) {
        if (transport == null) {
            return false;
        } else {
            this.transport = transport;
            return true;
        }
    }

    public boolean setHouse(House house) {
        this.house = house;
        return true;
    }

    public boolean setId(Long id) {
        /*if (id > 0) {
            this.id = IdManager.findUniq(id);
            return true;
        } else {
            return false;
        }*/
        this.id = id;
        return true;
    }

    public boolean setCreationDate(Date creationDate) {
        if (creationDate == null) {
            return false;
        } else {
            this.creationDate = creationDate;
            return true;
        }
    }

    /**
     * Выводит информацию в строков виде об объекте
     */
    @SneakyThrows
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
                + " - " + user.getUsername();
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



