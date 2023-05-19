package demo.warehouse.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import demo.warehouse.entity.Goods;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.repository.WarehouseRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ListingControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Warehouse warehouse = new Warehouse(1L, "demo", 200000);
        Goods goods = new Goods(1L, "tomatoes", 2000);
        goodsRepository.save(goods);
        warehouseRepository.save(warehouse);
    }

    @Test
    public void testUnauthorizedUsers() throws Exception {
        mvc.perform(get("/listings"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/make/listing"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/make/listing/save"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/accept/listing/1"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/reject/listing/1"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin", password = "123qwe", authorities = {"ROLE_ADMIN"})
    @Test
    public void testAuthorizedUsersActions() throws Exception {
        mvc.perform(get("/make/listing"))
                .andExpect(content().string(containsString("Firm name")))
                .andExpect(content().string(containsString("List a goods")))
                .andExpect(status().isOk());

        mvc.perform(post("/make/listing/save")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("firmName", "testFirmName")
                        .param("price", "300")
                        .param("size", "1")
                        .param("goods", "tomatoes"))
                .andExpect(redirectedUrlTemplate("/make/listing?success"))
                .andReturn();

        mvc.perform(get("/listings"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Accept")))
                .andExpect(content().string(containsString("Reject")))
                .andExpect(content().string(containsString("Offers")));


        mvc.perform(get("/accept/offer/1"))
                .andExpect(redirectedUrlTemplate("/listings?success"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/reject/offer/1"))
                .andExpect(redirectedUrlTemplate("/listings?reject"))
                .andExpect(status().is3xxRedirection());
    }
}
