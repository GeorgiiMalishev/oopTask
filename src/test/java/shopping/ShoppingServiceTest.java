package shopping;
import customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import product.Product;
import product.ProductDao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

/**
 * Тестирование ShoppingService
 */
@ExtendWith(MockitoExtension.class)
class ShoppingServiceTest {

    @Mock
    private ProductDao productDaoMock;
    @InjectMocks
    private ShoppingServiceImpl shoppingService;

    private Customer customer;
    private Cart cart;
    private Product product;
    private Product product2;

    @BeforeEach
    void setUp() {
        customer = new Customer(1, "000-000-000");
        cart = new Cart(customer);
        product = new Product("Макароны", 5);
        product2 = new Product("Курица", 2);
    }
    /**
     * Тест на покупку из корзины, и очищение корзины
     */
    @Test
    public void buyFromCartTest() throws BuyException {
        cart.add(product, 4);
        assertTrue(shoppingService.buy(cart));
        assertEquals(1, product.getCount());
        verify(productDaoMock, Mockito.times(1)).save(argThat(savedProduct ->
                savedProduct.getCount() == 1
        ));

        //тест падает, так как корзина не очищается после покупки
        assertTrue(cart.getProducts().isEmpty());
    }

    /**
     * Тест на получение корзины
     */
    @Test
    public void getCartTest(){
        cart.add(product, 1);
        cart.add(product2, 1);
        Cart gettedCart = shoppingService.getCart(customer);

        //Тест не проходит, потому что getCart не возвращает существующую корзину, а создает новую
        assertEquals(gettedCart.getProducts(), cart.getProducts());
    }

    /**
     * Проверка получения корзины от разных пользователей
     */
    @Test
    public void getCartFromDiffCustomersTest(){
        Customer customer2 = new Customer(2, "111-111-111");
        Cart cart2 = new Cart(customer2);
        cart.add(product, 1);
        cart2.add(product2, 1);
        Cart gettedCart = shoppingService.getCart(customer);
        Cart gettedCart2 = shoppingService.getCart(customer2);
        assertNotEquals(gettedCart, gettedCart2);
        //Тест не проходит, потому что getCart не возвращает существующую корзину, а создает новую
        assertNotEquals(gettedCart.getProducts(), gettedCart2.getProducts());
    }
    /**
     * Проверка случая, когда мы пытаемся положить товар с отрицательным или нулевым количеством в корзину
     */
    @Test
    public void negativeValueInCartTest(){
        //Тест падает, потому что в корзину можно добавить товары с отрицательным кол-вом
        Exception exception = assertThrows(IllegalArgumentException.class, () -> cart.add(product, -10));
        assertEquals("Невозможно добавить товар с отрицательным количеством!", exception.getMessage());

        //Тест падает, потому что в корзину можно добавить товары с нулевым кол-вом
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> cart.add(product, 0));
        assertEquals("Невозможно добавить товар с нулевым количеством!", exception2.getMessage());
    }

    /**
     * Проверка случая, когда мы пытаемся купить отрицательное или нулевое кол-во товаров
     */
    @Test
    public void negativeValueBuy(){
        cart.add(product, -1);
        //Тест падает, потому что можно купить товары с отрицательным кол-вом
        Exception exception = assertThrows(BuyException.class, () -> shoppingService.buy(cart));
        assertEquals("Невозможно купить отрицательное количество товаров!", exception.getMessage());

        //Тест падает, потому что можно купить товары с нулевым кол-вом
        cart.add(product, 0);
        Exception exception2 = assertThrows(BuyException.class, () -> shoppingService.buy(cart));
        assertEquals("Невозможно купить нулевое количество товаров!", exception2.getMessage());
    }

    /**
     * Тест на покупку всего количества товара
     */
    @Test
    public void buyAllProductsTest() throws BuyException {
        //тест не пройдет, потому что нельзя добавить в корзину столько товаров, сколько всего есть на складе
        cart.add(product, 5);
        cart.add(product2, 2);
        assertTrue(shoppingService.buy(cart));
        assertEquals(0, product.getCount());
        assertEquals(0, product2.getCount());
    }

    /**
     * Тест покупки пустой корзины
     */
    @Test
    public void buyEmptyCartTest() throws BuyException {
        assertFalse(shoppingService.buy(cart));
        verify(productDaoMock, Mockito.never()).save(Mockito.any(Product.class));
    }

    /**
     * Тест на покупку товара, который закончился уже после добавления в корзину
     */
    @Test
    public void buyEmptyProduct() throws BuyException {
        Cart cart2 = new Cart(customer);
        cart.add(product, 3);
        cart2.add(product, 3);
        shoppingService.buy(cart);
        assertFalse(shoppingService.buy(cart2));
    }

    /**
     * Тестирование следующих методов не имеет смысла, так как в сервисе идет простое получение из базы данных без логики
     */
    @Test
    public void getAllProductsTest() {
    }

    @Test
    public void getProductByNameTest() {
    }
}