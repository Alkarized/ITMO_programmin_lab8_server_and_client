package collection;

import collection.comparators.CoordinatesComparator;
import collection.comparators.NameComparator;
import collection.comparators.NumberOfRoomsComparator;
import fields.Flat;
import fields.User;
import lombok.SneakyThrows;
import message.MessageColor;
import server.DataBaseConnection;
import utils.MainLocale;
import utils.SerializableAnswerToClient;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Главнй класс для работы коллекцией
 */
public class CollectionManager {
    private final PriorityQueue<Flat> collection;
    private final Date date;
    private final DataBaseConnection dataBaseConnection;

    public CollectionManager() {
        date = new Date();
        dataBaseConnection = new DataBaseConnection();
        List<Flat> list = dataBaseConnection.getFlatCollection();
        collection = new PriorityQueue<>(new NameComparator());
        collection.addAll(list);
    }

    public PriorityQueue<Flat> getCollection() {
        return collection;
    }

    /**
     * Реалзицаия команнды info:
     *
     * @return информация о коллекции
     */
    @SneakyThrows
    public String printInfoAboutCollection() {
        return (new String(MainLocale.getResourceBundle().getString("manager_collection_type").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + getCollection().getClass().getName() + "\n" +
                new String(MainLocale.getResourceBundle().getString("manager_collection_date").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + getDate() + "\n" +
                new String(MainLocale.getResourceBundle().getString("manager_collection_size").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + getCollection().size());

    }

    public Date getDate() {
        return date;
    }

    /**
     * Очистка коллекции
     *
     * @return ответ на клиент
     */
    public String clearCollection(User user) {
        if (getCollection().size() > 0) {
            return String.valueOf(dataBaseConnection.clearCollectionByUser(user, getCollection()));
        }
        return "0";
    }

    /**
     * Сортировка коллекции, используя компаратор
     *
     * @param comp - условие сортировки (компаратор)
     * @return отсортированная коллекция
     */
    public PriorityQueue<Flat> sortCollectionByComp(Comparator<Flat> comp) {
        List<Flat> newList = new ArrayList<>(collection);
        newList.sort(comp);
        PriorityQueue<Flat> newCollection = new PriorityQueue<>(comp);
        newCollection.addAll(newList);
        return newCollection;
    }

    /**
     * Метод для получения первого элемента коллекции
     *
     * @return первый элемент коллекции или -1 если элементов в коллекции нет
     */
    public String getHeadOfCollection() {
        if (getCollection().size() > 0) {
            return Objects.requireNonNull(getCollection().peek()).printInfoAboutElement();
        } else {
            return null;
        }
    }

    /**
     * Счетчик элементов коллекции, у которых число комнат меньше заданного
     *
     * @param number данное число
     * @return полученное число элементов
     */
    public String countLessNumberOfRooms(int number) {
        if (getCollection().size() > 0) {
            int count = 0;
            PriorityQueue<Flat> newQueue = new PriorityQueue<>(getCollection());
            while (newQueue.size() > 0) {
                if (newQueue.poll().getNumberOfRooms() < number) {
                    count++;
                }
            }
            return (new Integer(count)).toString();
        } else {
            return "-1";
        }
    }

    /**
     * Поиск минимального элемента коооекции по координатам
     *
     * @return ответ клиенту
     */
    public String findElementWithMinCoordinates() {
        if (getCollection().size() > 0) {
            PriorityQueue<Flat> queue = sortCollectionByComp(new CoordinatesComparator());
            return Objects.requireNonNull(queue.peek()).printInfoAboutElement();
        } else {
            return "Коллекция пуста! ";
        }
    }

    /**
     * Реализация команды PrintFieldNumberOfRoomsCommand
     *
     * @return Значения поля numberOfRooms в порялке убывания
     */
    @SneakyThrows
    public String printFieldDescendingNumberOfRooms() {
        if (getCollection().size() > 0) {
            PriorityQueue<Flat> queue = sortCollectionByComp(new NumberOfRoomsComparator());
            StringBuilder stringBuilder = new StringBuilder();
            queue.forEach((flat -> stringBuilder.append(flat.getNumberOfRooms()).append("\t\n")));
            return new String(MainLocale.getResourceBundle().getString("manager_collection_number_of_rooms").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") +
                    "\n" + stringBuilder.toString();
        } else {
            return "Коллекция пуста!";
        }
    }

    /**
     * Удаление элемента коллекции по id
     *
     * @param id заданный id
     * @return ответ клиенту
     */
    public SerializableAnswerToClient removeElementById(Long id, User user) {
        Flat flat = null;
        if (getCollection().size() > 0) {
            if (getCollection().stream().filter((e) -> e.getId().equals(id)).count() == 1) {
                return new SerializableAnswerToClient(dataBaseConnection.removeById(id, user, getCollection()));

            } else {
                return new SerializableAnswerToClient((Flat) null);
            }
        } else {
            return new SerializableAnswerToClient((Flat) null);
        }
    }

    /**
     * Удаление первого элемента коллекции
     *
     * @return ответ клиенту
     */
    public Flat removeFirstElement(User user) {
        if (getCollection().size() > 0) {
            return dataBaseConnection.removeFlatFromDBIfUserCorrectly(getCollection().peek(), user, getCollection());
        } else {
            return null;
        }
    }

    /**
     * Выводит все элементы коллекции
     *
     * @return ответ клиенту
     */
    public String printAllElements() {
        if (getCollection().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            getCollection().forEach((flat -> stringBuilder.append(flat.printInfoAboutElement()).append("\n")));
            return stringBuilder.toString();
        } else {
            return "Коллекция пуста! ";
        }
    }

    /**
     * Добовление элемента в коллекцию
     *
     * @param flat Новый элемент
     * @return ответ клиенту
     */
    public SerializableAnswerToClient addElement(Flat flat) {
        //flat.toString();
        if (flat != null) {
            return new SerializableAnswerToClient(dataBaseConnection.addFlatToBD(flat, getCollection()));
        } else {
            return new SerializableAnswerToClient((Flat) null);
        }
    }

    /**
     * бновление элемента коллекции по id
     *
     * @param id   данный id
     * @param flat обновленный элемент
     * @return ответ клиенту
     */
    public SerializableAnswerToClient updateElement(Long id, Flat flat, User user) {
        if (getCollection().stream().filter((e) -> e.getId().equals(id)).count() == 1 && getCollection().size() > 0 && (flat != null)) {
            return new SerializableAnswerToClient(dataBaseConnection.updateElementById(id, user, getCollection(), flat));
        } else {
            return new SerializableAnswerToClient((Flat) null);
        }
    }

    /**
     * Удаляет элемент меньше заданного
     *
     * @param flat данный элемент
     * @return ответ клиенту
     */
    @SneakyThrows
    public SerializableAnswerToClient removeLower(Flat flat, User user) {
        if (getCollection().size() > 0) {
            if (flat != null) {
                return dataBaseConnection.removeLowerFlatsFromDB(flat, user, getCollection());
            } else {

                //Messages.normalMessageOutput("Не удалось получить элемент для сравнения.");
                return new SerializableAnswerToClient(MessageColor.ANSI_RED, new String(MainLocale.getResourceBundle().getString("manager_collection_lower_err1").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"), null);
            }
        } else {
            //Messages.normalMessageOutput("В коллекции нет элементов, нечего удалять");
            return new SerializableAnswerToClient(MessageColor.ANSI_PURPLE, new String(MainLocale.getResourceBundle().getString("manager_collection_lower_err2").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251"), null);
        }
        //Messages.normalMessageOutput("Все элементы, меньше данного - удалены!");
    }

    public DataBaseConnection getDataBaseConnection() {
        return dataBaseConnection;
    }

    public SerializableAnswerToClient authClient(User user) {
        SerializableAnswerToClient answer;
        if (dataBaseConnection.checkAndAuthorizeUser(user)) {
            answer = new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, "Вы успешно зашли!");
            answer.setBool(true);
        } else {
            answer = new SerializableAnswerToClient(MessageColor.ANSI_RED, "Ошибка входа");
            answer.setBool(false);
        }
        return answer;
    }

    public SerializableAnswerToClient registerClient(User user) {
        SerializableAnswerToClient answer;
        if (dataBaseConnection.registerNewUser(user)) {
            answer = new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, "Новый клиент успешно создан!");
            answer.setBool(true);
        } else {
            answer = new SerializableAnswerToClient(MessageColor.ANSI_RED, "Ошибка регистрации");
            answer.setBool(false);
        }
        return answer;
    }
}
