package demo.warehouse.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Goods;
import demo.warehouse.entity.Listing;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.DeliveryRepository;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.repository.ListingRepository;
import demo.warehouse.repository.WarehouseRepository;
import demo.warehouse.service.DeliveryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WarehouseControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryService deliveryService;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Warehouse warehouse = new Warehouse(1L, "demo", 210000);
        warehouseRepository.save(warehouse);
        Delivery delivery = new Delivery(
                1L,
                "testName",
                "tomatoes",
                1000,
                20000,
                new Date(2023, Calendar.APRIL, 8),
                new Date(2023, Calendar.APRIL, 9),
                "admin",
                "pending"
        );
        deliveryRepository.save(delivery);
        Listing listing = new Listing(
                1L,
                "testFirm",
                "tomatoes",
                1000,
                20000,
                new Date(2023, Calendar.APRIL, 10),
                new Date(2023, Calendar.APRIL, 11),
                "admin",
                "confirmed"
        );
        listingRepository.save(listing);
        Goods tomatoes = new Goods(1L, "tomatoes", 2000);
        goodsRepository.save(tomatoes);
        Goods potatoes = new Goods(2L, "potatoes", 2000);
        goodsRepository.save(potatoes);
        Goods cucumbers = new Goods(3L, "cucumbers", 2000);
        goodsRepository.save(cucumbers);
    }

    @Test
    public void testUnauthorizedUsers() throws Exception {
        mvc.perform(get("/stock"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/reference"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/reference/check"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin", password = "123qwe", authorities = {"ROLE_ADMIN"})
    @Test
    public void testAuthorizedUsersActions() throws Exception {
        mvc.perform(get("/stock"))
                .andExpect(content().string(containsString("Warehouse stock")))
                .andExpect(content().string(containsString(
                        "Current amount of money: " + warehouseRepository.findByName("demo").getCashbox()
                )))
                .andExpect(status().isOk());

        mvc.perform(post("/reference/check")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .queryParam("type", "Deliveries")
                        .queryParam("user", "")
                        .queryParam("dateFrom", "2023-03-10")
                        .queryParam("dateTo", "2023-05-10"))
                .andExpect(content().string(containsString("Deliveries")))
                .andReturn();

        mvc.perform(post("/reference/check")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .queryParam("type", "Listings")
                        .queryParam("user", "")
                        .queryParam("dateFrom", "2023-03-10")
                        .queryParam("dateTo", "2023-05-10"))
                .andExpect(content().string(containsString("Offers")))
                .andReturn();

        mvc.perform(post("/reference/check")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .queryParam("type", "Operator")
                        .queryParam("user", "")
                        .queryParam("dateFrom", "2023-03-10")
                        .queryParam("dateTo", "2023-05-10"))
                .andExpect(content().string(containsString("Deliveries")))
                .andExpect(content().string(containsString("Offers")))
                .andReturn();

        mvc.perform(get("/reference"))
                .andExpect(content().string(containsString("References")))
                .andReturn();
    }
}
