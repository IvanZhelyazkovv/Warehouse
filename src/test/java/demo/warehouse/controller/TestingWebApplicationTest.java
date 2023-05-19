package demo.warehouse.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import demo.warehouse.repository.RoleRepository;
import demo.warehouse.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingWebApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "admin", password = "123qwe", authorities = {"ROLE_ADMIN"})
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/listings"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Accept")))
                .andExpect(content().string(containsString("Reject")))
                .andExpect(content().string(containsString("Offers")));

        mvc.perform(post("/make/listing/save")
                .contentType(APPLICATION_FORM_URLENCODED)
                .param("firmName","testFrim")
                .param("price", "300")
                .param("size", "2")
                .param("goods", "tomatoes"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        mvc.perform(post("/make/delivery/save")
                        .contentType(APPLICATION_FORM_URLENCODED)
                        .param("supplierName","testSupplierName")
                        .param("price", "300")
                        .param("size", "2")
                        .param("goods", "tomatoes"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}