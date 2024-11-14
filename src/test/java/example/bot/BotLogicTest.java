package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotLogicTest {

    private User user;
    private FakeBot fakeBot;
    private BotLogic botLogic;
    private int messageIndex;

    /**
     * Инициализация бота и индекса сообщения перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        user = new User(Long.MIN_VALUE);
        fakeBot = new FakeBot();
        botLogic = new BotLogic(fakeBot);
        messageIndex = -1;
    }

    /**
     * Тест команды /test с правильными ответами
     */
    @Test
    void testRightTest() {
        botLogic.processCommand(user,"/test");
        messageIndex++;
        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getMessage(messageIndex));
        botLogic.processCommand(user,"100");
        messageIndex++;
        Assertions.assertEquals("Правильный ответ!", fakeBot.getMessage(messageIndex));
    }

    /**
     * Тест команды /test с не правильными ответами
     */
    @Test
    void testWrongTest(){
        botLogic.processCommand(user,"/test");
        messageIndex++;
        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getMessage(messageIndex));
        botLogic.processCommand(user,"10");
        messageIndex++;
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", fakeBot.getMessage(messageIndex));
    }

    /**
     * Тест для команды /notify
     */
    @Test
    void testNotify() throws InterruptedException {
        botLogic.processCommand(user,"/notify");
        messageIndex++;
        Assertions.assertEquals("Введите текст напоминания", fakeBot.getMessage(messageIndex));
        botLogic.processCommand(user,"напоминание");
        messageIndex++;
        Assertions.assertEquals("Через сколько секунд напомнить?", fakeBot.getMessage(messageIndex));
        botLogic.processCommand(user,"1");
        messageIndex++;
        Assertions.assertEquals("Напоминание установлено", fakeBot.getMessage(messageIndex));
        Assertions.assertNotEquals("Сработало напоминание: 'напоминание'", fakeBot.getLastMessage());
        Thread.sleep(1200);
        Assertions.assertEquals("Сработало напоминание: 'напоминание'", fakeBot.getLastMessage());
    }

    /**
     * Тест для команды /repeat
     */
    @Test
    void testRepeat(){
        botLogic.processCommand(user,"/test");
        messageIndex++;
        botLogic.processCommand(user,"10");
        messageIndex+=2;
        botLogic.processCommand(user,"/repeat");
        messageIndex++;
        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getLastMessage());
        botLogic.processCommand(user,"100");
        messageIndex++;
        assertEquals("Правильный ответ!", fakeBot.getMessage(messageIndex));
    }
}