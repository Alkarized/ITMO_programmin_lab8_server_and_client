package commands;

import collection.Receiver;
import fields.Flat;
import fields.User;
import utils.SerializableAnswerToClient;

import java.io.Serializable;

/**
 * Абстрактный класс команды для патерна Command
 */
public abstract class Command implements Serializable {
    private static final long serialVersionUID = 12311;
    protected transient Receiver receiver;

    public Command() {

    }

    /**
     * Метод для работы клиент-серверного соединения, создание ответа клиенту
     *
     * @param receiver Ресивер
     * @param flat     Если есть, то элемент коллекции
     * @param args     Если есть, то аргументы для команды
     * @return ответ клиенту
     */
    public abstract SerializableAnswerToClient execute(Receiver receiver, Flat flat, String[] args, User user);


}
