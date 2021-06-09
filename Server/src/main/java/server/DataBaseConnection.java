package server;

import fields.Flat;
import fields.User;
import message.MessageColor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import utils.EncryptMD2;
import utils.SerializableAnswerToClient;

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

    public Flat addFlatToBD(Flat flat, PriorityQueue<Flat> queue) {
        try {
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
                return flat;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Flat removeFlatFromDBIfUserCorrectly(Flat flat, User user, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction.begin();
        }

        if (flat.getUser().getUsername().equals(user.getUsername()) && flat.getUser().getPassword().equals(user.getPassword())) {
            session.remove(flat);
        } else {
            return null;
        }

        transaction.commit();

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.remove(flat);
            return flat;
        } else {
            return null;
        }
    }

    public List<Flat> getFlatCollection() {
        return (List<Flat>) session.createQuery("FROM Flat").list();
    }

    public int clearCollectionByUser(User user, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        //User userDB = session.get(User.class, user.getUsername());
        //System.out.println("count - " + userDB.getFlats().size());
        //System.out.println(queue);
        //if (userDB.getFlats().size() < 1) return 0;
        //int count = userDB.getFlats().size();
        //userDB.getFlats().clear();
        //session.update(userDB);
        int count = Math.toIntExact(queue.stream().filter(e -> e.getUser().getUsername().equals(user.getUsername())).count());
        queue.stream()
                .filter((e)-> e.getUser().getUsername().equals(user.getUsername()))
                .forEach((e)-> session.remove(e));
        transaction.commit();
        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.removeIf((e)->e.getUser().getUsername().equals(user.getUsername()));
            return count;
        } else {
            return 0;
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

    public Flat removeById(Long id, User user, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }

        Flat flat = session.get(Flat.class, id);
        if (user.getUsername().equals(flat.getUser().getUsername())) {
            session.remove(flat);
        } else {
            return null;
        }
        transaction.commit();

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.remove(flat);
            return flat;
        } else {
            return null;
        }
    }

    public Flat updateElementById(Long id, User user, PriorityQueue<Flat> queue, Flat flat) {
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
            return null;
        }
        try {
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
            queue.remove(flatDBTEMP);
            queue.add(flatDB);

            return flatDB;
        } else {
            return null;
        }
    }

    public SerializableAnswerToClient removeLowerFlatsFromDB(Flat flat, User user, PriorityQueue<Flat> queue) {
        Transaction transaction = session.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }

        PriorityQueue<Flat> localQueue = new PriorityQueue<>(queue);
        if (localQueue.stream().filter(e -> e.compareTo(flat) < 0 && e.getUser().getUsername().equals(user.getUsername())).count() < 1) {
            return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Не удалось удалить элементы", null);
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

            return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, "Все элементы, меньше данного - удалены!", queue);
        } else {
            return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Не удалось удалить элементы", null);
        }
    }
}
