package demo.warehouse.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import demo.warehouse.dto.WarehouseDto;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.WarehouseRepository;
import demo.warehouse.service.WarehouseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DeliveryControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Warehouse warehouse = new Warehouse(1L,"demo",200000);
        warehouseRepository.save(warehouse);
    }

    @Test
    public void testUnauthorizedUsers() throws Exception {
        mvc.perform(get("/deliveries"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/make/delivery"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/make/delivery/save"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/accept/delivery/1"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());

        mvc.perform(get("/reject/delivery/1"))
                .andExpect(redirectedUrlTemplate("http://localhost/login"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin", password = "123qwe", authorities = {"ROLE_ADMIN"})
    @Test
    public void testAuthorizedUsersActions() throws Exception {
        mvc.perform(get("/make/delivery"))
                .andExpect(content().string(containsString("Your Name")))
                .andExpect(status().isOk());

        mvc.perform(post("/make/delivery/save")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("supplierName", "testSupplierName")
                        .param("price", "300")
                        .param("size", "2")
                        .param("goods", "tomatoes"))
                .andExpect(redirectedUrlTemplate("/make/delivery?success"))
                .andReturn();

        mvc.perform(get("/deliveries"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Accept")))
                .andExpect(content().string(containsString("Reject")))
                .andExpect(content().string(containsString("Deliveries")));


        mvc.perform(get("/accept/delivery/1"))
                .andExpect(redirectedUrlTemplate("/deliveries?success"))
                .andExpect(status().is3xxRedirection());
//
        mvc.perform(get("/reject/delivery/1"))
                .andExpect(redirectedUrlTemplate("/deliveries?reject"))
                .andExpect(status().is3xxRedirection());
    }
}
