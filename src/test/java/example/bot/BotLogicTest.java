package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест логики бота
 */
class BotLogicTest {

    /**
     * Пользователь
     */
    private User user;
    /**
     * Фейк бот для тестов
     */
    private FakeBot fakeBot;
    /**
     * Логика бота, которую нужно проверить
     */
    private BotLogic botLogic;

    /**
     * Инициализация бота и индекса сообщения перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        user = new User(123L);
        fakeBot = new FakeBot();
        botLogic = new BotLogic(fakeBot);
    }

    /**
     * Тест команды /test с правильными ответами
     */
    @Test
    void testRightTest() {
        botLogic.processCommand(user,"/test");
        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getMessage(0));
        botLogic.processCommand(user,"100");
        Assertions.assertEquals("Правильный ответ!", fakeBot.getMessage(1));
    }

    /**
     * Тест команды /test с неправильными ответами
     */
    @Test
    void testWrongTest(){
        botLogic.processCommand(user,"/test");
        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getMessage(0));
        botLogic.processCommand(user,"10");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", fakeBot.getMessage(1));
    }

    /**
     * Тест для команды /notify
     */
    @Test
    void testNotify() throws InterruptedException {
        botLogic.processCommand(user,"/notify");
        Assertions.assertEquals("Введите текст напоминания", fakeBot.getMessage(0));
        botLogic.processCommand(user,"напоминание");
        Assertions.assertEquals("Через сколько секунд напомнить?", fakeBot.getMessage(1));
        botLogic.processCommand(user,"1");
        Assertions.assertEquals("Напоминание установлено", fakeBot.getMessage(2));
        Assertions.assertNotEquals("Сработало напоминание: 'напоминание'", fakeBot.getLastMessage());
        Thread.sleep(1200);
        Assertions.assertEquals("Сработало напоминание: 'напоминание'", fakeBot.getLastMessage());
    }

    /**
     * Тест для команды /repeat, после неправильного ответа
     */
    @Test
    void testWrongRepeat(){
        botLogic.processCommand(user,"/test");
        botLogic.processCommand(user,"10");
        botLogic.processCommand(user,"/repeat");
        Assertions.assertEquals("Вычислите степень: 10^2", fakeBot.getLastMessage());
        botLogic.processCommand(user,"100");
        assertEquals("Правильный ответ!", fakeBot.getMessage(4));
        botLogic.processCommand(user,"/repeat");
        Assertions.assertEquals("Нет вопросов для повторения", fakeBot.getLastMessage());
    }

    /**
     * Тест для команды /repeat, когда нет неправильных ответов
     */
    @Test
    void testRightRepeat(){
        botLogic.processCommand(user,"/test");
        botLogic.processCommand(user,"100");
        botLogic.processCommand(user,"/repeat");
        Assertions.assertEquals("Нет вопросов для повторения", fakeBot.getLastMessage());
    }
}