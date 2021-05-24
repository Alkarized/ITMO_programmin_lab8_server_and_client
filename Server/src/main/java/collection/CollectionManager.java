package collection;

import collection.comparators.CoordinatesComparator;
import collection.comparators.NameComparator;
import collection.comparators.NumberOfRoomsComparator;
import fields.Flat;
import fields.User;
import message.MessageColor;
import server.DataBaseConnection;
import utils.SerializableAnswerToClient;

import java.util.*;

/**
 * Главнй класс для работы коллекцией
 */
public class CollectionManager {
    private final PriorityQueue<Flat> collection;
    private final Date date;
    private final DataBaseConnection dataBaseConnection;
    private boolean dbIsUsed = false;

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
    public String printInfoAboutCollection() {
        return ("Тип коллекции - " + getCollection().getClass().getName() + "\n" +
                "Дата иницализации - " + getDate() + "\n" +
                "Кол-во элементов - " + getCollection().size());

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
            if (dataBaseConnection.clearCollectionByUser(user, getCollection()))
                return "Коллекция успешно очищена! ";
            else
                return "Не удалось отчитсть коллекцию!";
        } else {
            return "Коллекция пуста! ";
        }
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
            return "Коллекция пуста! ";
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
    public String printFieldDescendingNumberOfRooms() {
        if (getCollection().size() > 0) {
            PriorityQueue<Flat> queue = sortCollectionByComp(new NumberOfRoomsComparator());
            StringBuilder stringBuilder = new StringBuilder();
            queue.forEach((flat -> stringBuilder.append(flat.getNumberOfRooms())));
            return ("Значения поля numberOfRooms всех элементов в порядке убывания:\n " + stringBuilder.toString() + " ");
        } else {
            return "Коллекция пуста! ";
        }
    }

    /**
     * Удаление элемента коллекции по id
     *
     * @param id заданный id
     * @return ответ клиенту
     */
    public SerializableAnswerToClient removeElementById(Long id, User user) {
        if (getCollection().size() > 0) {
            if (getCollection().stream().filter((e) -> e.getId().equals(id)).count() == 1) {
                if (dataBaseConnection.removeById(id, user, getCollection())) {
                    return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, "Элемент успешно удален! ");
                } else {
                    return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, "Не удалось удалить элемент");
                }
            } else {
                return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Элемент с данным ID отсутствует в коллекции! ");
            }
        } else {
            return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, "Коллекция пуста! ");
        }
    }

    /**
     * Удаление первого элемента коллекции
     *
     * @return ответ клиенту
     */
    public String removeFirstElement(User user) {
        if (getCollection().size() > 0) {
            if (dataBaseConnection.removeFlatFromDBIfUserCorrectly(getCollection().peek(), user, getCollection())) {
                return "Первый элемент успешно удален! ";
            } else {
                return "Не удалось удалить элемент";
            }
        } else {
            return "Коллекция пуста! ";
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
        if (flat != null) {
            dataBaseConnection.addFlatToBD(flat, getCollection());
            return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, "Элемент успешно добавлен в коллекцию! ");
        } else {
            return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Ошибка при добавлении элемента! Попробуйте еще раз. ");
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
            if (dataBaseConnection.updateElementById(id, user, getCollection(), flat)) {
                return new SerializableAnswerToClient(MessageColor.ANSI_GREEN, "Элемент успешно обновлен");
            } else {
                return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Не удалось обновить элемент");
            }
        } else {
            return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Ошибка!!! Возможные причины: \n\t" +
                    "> элемент с данным ID отсутствует в коллекции\n\t" +
                    "> коллекция пуста\n Попробуйте еще раз.");
        }
    }

    /**
     * Удаляет элемент меньше заданного
     *
     * @param flat данный элемент
     * @return ответ клиенту
     */
    public SerializableAnswerToClient removeLower(Flat flat, User user) {
        if (getCollection().size() > 0) {
            if (flat != null) {
                try {
                    if (dataBaseConnection.removeLowerFlatsFromDB(flat, user, getCollection())) {
                        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, "Все элементы, меньше данного - удалены!");
                    } else {
                        return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Не удалось удалить элементы");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Что-то пошло не так... " + e.toString());
                }
            } else {
                //Messages.normalMessageOutput("Не удалось получить элемент для сравнения.");
                return new SerializableAnswerToClient(MessageColor.ANSI_RED, "Не удалось получить элемент для сравнения. ");
            }
        } else {
            //Messages.normalMessageOutput("В коллекции нет элементов, нечего удалять");
            return new SerializableAnswerToClient(MessageColor.ANSI_PURPLE, "В коллекции нет элементов, нечего удалять ");
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
