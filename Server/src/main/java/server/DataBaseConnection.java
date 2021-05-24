package server;

import fields.Flat;
import fields.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import utils.EncryptMD2;

import java.util.List;
import java.util.PriorityQueue;
import java.util.TimeZone;

public class DataBaseConnection {
    private Session session;
    private SessionFactory sessionFactory;

    public DataBaseConnection() {
        startBase();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getSession() {
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public void startBase() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        session = sessionFactory.withOptions()
                .jdbcTimeZone(TimeZone.getTimeZone("UTC"))
                .openSession();
    }

    public synchronized boolean addFlatToBD(Flat flat, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }
        session.save(flat.getCoordinates());
        session.save(flat.getHouse());
        session.save(flat);
        transaction.commit();

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.add(flat);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeFlatFromDBIfUserCorrectly(Flat flat, User user, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }

        if (flat.getUser().getUsername().equals(user.getUsername()) && flat.getUser().getPassword().equals(user.getPassword())) {
            session.remove(flat);
        } else {
            return false;
        }

        transaction.commit();

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.remove(flat);
            return true;
        } else {
            return false;
        }
    }

    public List<Flat> getFlatCollection() {
        return (List<Flat>) session.createQuery("FROM Flat").list();
    }

    public boolean clearCollectionByUser(User user, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        User userDB = session.get(User.class, user.getUsername());
        if (userDB.getFlats().size() < 1) return false;
        userDB.getFlats().clear();
        session.update(userDB);

        transaction.commit();

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.removeIf(flat -> flat.getUser().getUsername().equals(user.getUsername()));
            return true;
        } else {
            return false;
        }
    }

    public boolean checkAndAuthorizeUser(User user) {
        User userDB = session.get(User.class, user.getUsername());
        if (userDB != null) {
            return EncryptMD2.encrypt(user.getPassword(), userDB.getSalt()).equals(userDB.getPassword());
        } else {
            return false;
        }
    }

    public boolean registerNewUser(User user) {
        User userDB = session.get(User.class, user.getUsername());
        if (userDB == null) {
            Transaction transaction = session.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            User userToDB = new User(user.getUsername(), user.getPassword());
            userToDB.setSalt(EncryptMD2.generateRandomString());
            userToDB.setPassword(EncryptMD2.encrypt(userToDB.getPassword(), userToDB.getSalt()));
            session.save(userToDB);

            transaction.commit();

            if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean removeById(Long id, User user, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }

        Flat flat = session.get(Flat.class, id);
        if (user.getUsername().equals(flat.getUser().getUsername())) {
            session.remove(flat);
        } else {
            return false;
        }
        transaction.commit();

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.remove(flat);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateElementById(Long id, User user, PriorityQueue<Flat> queue, Flat flat) {
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        Flat flatDB = session.get(Flat.class, id);
        Flat flatDBTEMP = session.get(Flat.class, id);
        if (user.getUsername().equals(flatDB.getUser().getUsername())) {
            flatDB.setName(flat.getName());
            flatDB.setArea(flat.getArea());
            flatDB.getCoordinates().setX(flat.getCoordinates().getX());
            flatDB.getCoordinates().setY(flat.getCoordinates().getY());
            flatDB.setNumberOfRooms(flat.getNumberOfRooms());
            flatDB.setFurnish(flat.getFurnish());
            flatDB.setView(flat.getView());
            flatDB.setTransport(flat.getTransport());
            flatDB.getHouse().setName(flat.getHouse().getName());
            flatDB.getHouse().setYear(flat.getHouse().getYear());

            session.update(flatDB);

        } else {
            return false;
        }
        try {
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.remove(flatDBTEMP);
            queue.add(flatDB);

            return true;
        } else {
            return false;
        }
    }

    public boolean removeLowerFlatsFromDB(Flat flat, User user, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }

        PriorityQueue<Flat> localQueue = new PriorityQueue<>(queue);
        if (localQueue.stream().filter(e -> e.compareTo(flat) < 0 && e.getUser().getUsername().equals(user.getUsername())).count() < 1) {
            return false;
        } else {
            localQueue.stream()
                    .filter(e -> e.compareTo(flat) < 0 && e.getUser().getUsername().equals(user.getUsername()))
                    .forEach(e -> session.remove(e));
            localQueue.removeIf(e -> e.compareTo(flat) < 0 && e.getUser().getUsername().equals(user.getUsername()));
        }

        transaction.commit();

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.clear();
            queue.addAll(localQueue);

            return true;
        } else {
            return false;
        }
    }
}
