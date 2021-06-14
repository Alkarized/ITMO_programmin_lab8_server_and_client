package collection;

import fields.Flat;
import fields.User;
import message.MessageColor;
import server.Connection;
import utils.MainLocale;
import utils.SerializableAnswerToClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Receiver {
    private final Connection connection;
    private final CollectionManager collectionManager;

    public Receiver(Connection connection, CollectionManager collectionManager) {
        this.connection = connection;
        connection.setReceiver(this);
        this.collectionManager = collectionManager;
    }


    public SerializableAnswerToClient getInfoAboutAllCommands() throws IOException, ClassNotFoundException {
        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW,
                "help : " + new String(MainLocale.getResourceBundle().getString("help_help").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "info : " + new String(MainLocale.getResourceBundle().getString("help_info").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        //"show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                        "add {element}: " + new String(MainLocale.getResourceBundle().getString("help_add").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "update id {element}: " + new String(MainLocale.getResourceBundle().getString("help_update").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "remove_by_id id: " + new String(MainLocale.getResourceBundle().getString("help_remove_id").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "clear: " + new String(MainLocale.getResourceBundle().getString("help_clear").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "execute_script file_name: " + new String(MainLocale.getResourceBundle().getString("help_script").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "exit: " + new String(MainLocale.getResourceBundle().getString("help_exit").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "remove_first: " + new String(MainLocale.getResourceBundle().getString("help_remove_first").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "head: " + new String(MainLocale.getResourceBundle().getString("help_head").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "remove_lower {element}: " + new String(MainLocale.getResourceBundle().getString("help_remove_lower").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "min_by_coordinates: " + new String(MainLocale.getResourceBundle().getString("help_min").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "count_less_than_number_of_rooms numberOfRooms: " + new String(MainLocale.getResourceBundle().getString("help_count").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251") + "\n" +
                        "print_field_descending_number_of_rooms: " + new String(MainLocale.getResourceBundle().getString("help_print").getBytes(StandardCharsets.ISO_8859_1), "WINDOWS-1251")
        );
    }

    public SerializableAnswerToClient getInfoAboutCollection() throws IOException, ClassNotFoundException {
        //Messages.getLogger().info("Комманда выполнена");
        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, collectionManager.printInfoAboutCollection());
    }

    public SerializableAnswerToClient clear(User user) throws IOException, ClassNotFoundException {
        //Messages.getLogger().info("Комманда выполнена");
        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, collectionManager.clearCollection(user));
    }

    public SerializableAnswerToClient removeFirst(User user) throws IOException, ClassNotFoundException {
        //Messages.getLogger().info("Комманда выполнена");
        return new SerializableAnswerToClient(collectionManager.removeFirstElement(user));
    }

    public SerializableAnswerToClient printFieldDescendingNumberOfRooms() throws IOException, ClassNotFoundException {
        //Messages.getLogger().info("Комманда выполнена");
        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, collectionManager.printFieldDescendingNumberOfRooms());
    }

    public SerializableAnswerToClient getHead() throws IOException, ClassNotFoundException {
        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, collectionManager.getHeadOfCollection());
    }

    public SerializableAnswerToClient countLessNumberOfRooms(String[] args) throws IOException, ClassNotFoundException {
        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, collectionManager.countLessNumberOfRooms(Integer.parseInt(args[1])));
    }

    public SerializableAnswerToClient printElementWithMinCoordinates() throws IOException, ClassNotFoundException {
        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, collectionManager.findElementWithMinCoordinates());
    }

    public SerializableAnswerToClient removeById(String[] args, User user) throws IOException, ClassNotFoundException {
        return collectionManager.removeElementById(Long.parseLong(args[1]), user);
    }

    public SerializableAnswerToClient printAllElements() throws IOException, ClassNotFoundException {
        return new SerializableAnswerToClient(MessageColor.ANSI_YELLOW, collectionManager.printAllElements());
    }

    public SerializableAnswerToClient addElement(Flat flat) throws IOException, ClassNotFoundException {
        return collectionManager.addElement(flat);
    }

    public SerializableAnswerToClient updateElementById(Flat flat, String[] args, User user) throws IOException, ClassNotFoundException {
        return collectionManager.updateElement((Long.parseLong(args[1])), flat, user);
    }

    public SerializableAnswerToClient removeLowerElements(Flat flat, User user) throws IOException, ClassNotFoundException {
        return collectionManager.removeLower(flat, user);
    }

    public SerializableAnswerToClient registerClient(User user) throws IOException, ClassNotFoundException {
        return collectionManager.registerClient(user);
    }

    public SerializableAnswerToClient authClient(User user) throws IOException, ClassNotFoundException {
        return collectionManager.authClient(user);
    }

    public SerializableAnswerToClient getCollection(){
        return new SerializableAnswerToClient(collectionManager.getCollection());
    }
}
