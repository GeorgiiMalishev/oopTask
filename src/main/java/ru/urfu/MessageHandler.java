package ru.urfu;

/**
 * Обработчик сообщений
 */
public class MessageHandler {
    /**
     * Обработать сообщение
     */
    public String handleMessage(String message) {
        return repeatMessage(message);
    }

    /**
     * Повторить сообщение
     */
    private String repeatMessage(String message) {
        return String.format("Ваше сообщение: '%s'", message);
    }
}
