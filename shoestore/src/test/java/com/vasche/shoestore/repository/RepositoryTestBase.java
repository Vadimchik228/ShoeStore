package com.vasche.shoestore.repository;

import com.vasche.shoestore.domain.order.Order;
import com.vasche.shoestore.domain.orderItem.OrderItem;
import com.vasche.shoestore.domain.orderItem.Status;
import com.vasche.shoestore.domain.shoe.Season;
import com.vasche.shoestore.domain.shoe.Sex;
import com.vasche.shoestore.domain.shoe.Shoe;
import com.vasche.shoestore.domain.shoe.ShoeModel;
import com.vasche.shoestore.domain.user.Role;
import com.vasche.shoestore.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTestBase {

    @Container
    private static final PostgreSQLContainer<?> postgres = (PostgreSQLContainer)
            new PostgreSQLContainer<>("postgres").withReuse(true);

    @DynamicPropertySource
    private static void configurePostgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public OrderItemRepository orderItemRepository;

    @Autowired
    public CartItemRepository cartItemRepository;

    @Autowired
    public ShoeRepository shoeRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        orderRepository.deleteAll();
        orderItemRepository.deleteAll();
        cartItemRepository.deleteAll();
        shoeRepository.deleteAll();
    }

    public User getUser() {
        return User.builder()
                .name("Vadim1")
                .username("schebet1@gmail.com")
                .password("12345")
                .roles(Set.of(Role.ROLE_USER))
                .build();
    }

    public Shoe getShoe(String title) {
        return Shoe.builder()
                .price(BigDecimal.valueOf(100))
                .shoeModel(ShoeModel.SHOES)
                .sex(Sex.UNISEX)
                .season(Season.DEMISEASON)
                .description("Some desc")
                .title(title)
                .size(45)
                .build();
    }

    public Order getOrder() {
        return Order.builder()
                .address("address")
                .email("email")
                .city("city")
                .firstName("name")
                .lastName("name")
                .phoneNumber("89507742275")
                .orderTime(LocalDateTime.now())
                .build();
    }

    public OrderItem getOrderItem(Order order, Shoe shoe) {
        return OrderItem.builder()
                .order(order)
                .shoe(shoe)
                .quantity(1)
                .status(Status.IN_ASSEMBLY)
                .build();
    }

    public Order getOrder(User user) {
        return Order.builder()
                .address("address")
                .email("email")
                .city("city")
                .firstName("name")
                .lastName("name")
                .phoneNumber("89507742275")
                .orderTime(LocalDateTime.now())
                .user(user)
                .build();
    }

    public Shoe getShoe() {
        return Shoe.builder()
                .price(BigDecimal.valueOf(100))
                .shoeModel(ShoeModel.SHOES)
                .sex(Sex.UNISEX)
                .season(Season.DEMISEASON)
                .description("Some desc")
                .title("Some title")
                .size(45)
                .build();
    }

}
