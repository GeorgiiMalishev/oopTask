package example.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Фейковый бот для тестов
 */
public class FakeBot implements Bot {
    /**
     * Сообщения, отправленные ботом
     */
    private final List<String> messages = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

    /**
     * Получить последнее сообщение
     */
    public String getLastMessage() {
        return messages.getLast();
    }

    /**
     * Получить сообщение по индексу
     */
    public String getMessage(int index) {
        return messages.get(index);
    }
}
