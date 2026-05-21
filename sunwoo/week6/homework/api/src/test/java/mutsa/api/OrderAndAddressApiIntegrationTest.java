package mutsa.api;

import mutsa.api.domain.Address;
import mutsa.api.domain.Cart;
import mutsa.api.domain.Product;
import mutsa.api.domain.Users;
import mutsa.api.enums.OrderStatus;
import mutsa.api.repository.AddressRepository;
import mutsa.api.repository.CartItemRepository;
import mutsa.api.repository.CartRepository;
import mutsa.api.repository.OrdersRepository;
import mutsa.api.repository.ProductRepository;
import mutsa.api.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderAndAddressApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @BeforeEach
    void setUp() {
        ordersRepository.deleteAll();
        cartItemRepository.deleteAll();
        cartRepository.deleteAll();
        addressRepository.deleteAll();
        productRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    void addressApiCreatesListsUpdatesAndDeletesAddress() throws Exception {
        Users user = saveUser("tester", "tester@example.com", "password");

        MvcResult createResult = mockMvc.perform(post("/api/users/{userId}/addresses", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "집",
                                  "zipCode": "12345",
                                  "address": "서울시 강남구 테헤란로 123",
                                  "phoneNumber": "010-1234-5678"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("집"))
                .andExpect(jsonPath("$.zipCode").value("12345"))
                .andReturn();

        Long addressId = readLong(createResult, "id");

        mockMvc.perform(get("/api/users/{userId}/addresses", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(addressId))
                .andExpect(jsonPath("$[0].name").value("집"));

        mockMvc.perform(patch("/api/users/{userId}/addresses/{addressId}", user.getId(), addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "회사",
                                  "zipCode": "54321",
                                  "address": "서울시 중구 세종대로 10",
                                  "phoneNumber": "010-9999-8888"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("회사"))
                .andExpect(jsonPath("$.zipCode").value("54321"));

        mockMvc.perform(delete("/api/users/{userId}/addresses/{addressId}", user.getId(), addressId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/users/{userId}/addresses", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void directOrderApiCreatesOrderAndCancelRestoresStock() throws Exception {
        Users user = saveUser("tester", "tester@example.com", "password");
        Address address = addressRepository.save(Address.create(user, "집", "12345", "서울시 강남구 테헤란로 123", "010-1234-5678"));
        Product product = productRepository.save(Product.create("무선 마우스", 25000, 10, "가벼운 무선 마우스"));

        MvcResult orderResult = mockMvc.perform(post("/api/users/{userId}/orders/direct", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "addressId": %d,
                                  "productId": %d,
                                  "quantity": 2
                                }
                                """.formatted(address.getId(), product.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.addressId").value(address.getId()))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.ORDER_COMPLETED.name()))
                .andExpect(jsonPath("$.totalPrice").value(50000))
                .andExpect(jsonPath("$.orderItems[0].productName").value("무선 마우스"))
                .andExpect(jsonPath("$.orderItems[0].orderPrice").value(25000))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(2))
                .andReturn();

        Long orderId = readLong(orderResult, "orderId");
        assertEquals(8, productRepository.findById(product.getId()).orElseThrow().getStock());

        mockMvc.perform(patch("/api/users/{userId}/orders/{orderId}/cancel", user.getId(), orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.CANCELED.name()));

        assertEquals(10, productRepository.findById(product.getId()).orElseThrow().getStock());
    }

    @Test
    void cartOrderApiCreatesOrderAndClearsCart() throws Exception {
        Users user = saveUser("tester", "tester@example.com", "password");
        Address address = addressRepository.save(Address.create(user, "집", "12345", "서울시 강남구 테헤란로 123", "010-1234-5678"));
        Product product = productRepository.save(Product.create("기계식 키보드", 30000, 5, "청축 키보드"));
        Cart cart = Cart.builder()
                .users(user)
                .cartItems(new ArrayList<>())
                .build();
        cart.addProduct(product, 3);
        cartRepository.save(cart);

        mockMvc.perform(post("/api/users/{userId}/orders/cart", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "addressId": %d
                                }
                                """.formatted(address.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.ORDER_COMPLETED.name()))
                .andExpect(jsonPath("$.totalPrice").value(90000))
                .andExpect(jsonPath("$.orderItems[0].productName").value("기계식 키보드"))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(3));

        assertEquals(2, productRepository.findById(product.getId()).orElseThrow().getStock());
        assertTrue(cartItemRepository.findAll().isEmpty());
    }

    private Users saveUser(String name, String email, String password) {
        Users user = BeanUtils.instantiateClass(Users.class);
        ReflectionTestUtils.setField(user, "name", name);
        ReflectionTestUtils.setField(user, "email", email);
        ReflectionTestUtils.setField(user, "password", password);
        return usersRepository.save(user);
    }

    private Long readLong(MvcResult result, String fieldName) throws Exception {
        String response = result.getResponse().getContentAsString();
        Pattern pattern = Pattern.compile("\"" + fieldName + "\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(response);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Field not found in response: " + fieldName);
        }
        return Long.parseLong(matcher.group(1));
    }
}
