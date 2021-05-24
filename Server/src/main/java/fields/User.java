package fields;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "usersData")
public class User implements Serializable {
    private static final long serialVersionUID = 12213123;

    @Id
    @Column(name = "username", nullable = false, updatable = false)
    private String username;

    @Column(name = "password", nullable = false, updatable = false)
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Flat> flats;

    @Column(name = "salt", nullable = false, updatable = false)
    private String salt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Flat> getFlats() {
        return flats;
    }

    public void setFlats(List<Flat> flats) {
        this.flats = flats;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
