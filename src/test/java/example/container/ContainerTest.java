package example.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ContainerTest {
    /**
     * Проверка добавления элемента
     */
    @Test
    void testAdd(){
        Item item = new Item(1);
        Container container = new Container();
        Assertions.assertEquals(0, container.size());
        container.add(item);
        Assertions.assertEquals(1, container.size());
        Assertions.assertTrue(container.contains(item));
    }

    /**
     * Проверка удаления элемента
     */
    @Test
    void testRemove(){
        Item item = new Item(1);
        Item item2 = new Item(2);
        Container container = new Container();
        container.add(item);
        container.add(item2);
        container.remove(item);
        Assertions.assertEquals(1, container.size());
        Assertions.assertFalse(container.contains(item));
        container.remove(item2);
        Assertions.assertEquals(0, container.size());
    }
}