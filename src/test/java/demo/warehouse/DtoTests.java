package demo.warehouse;

import demo.warehouse.dto.DeliveryDto;
import demo.warehouse.dto.GoodsDto;
import demo.warehouse.dto.ListingDto;
import demo.warehouse.dto.UserDto;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class DtoTests {
    @Test
    public void testDeliveryDto() {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(1L);
        deliveryDto.setSupplierName("testName");
        deliveryDto.setPrice(200);
        deliveryDto.setGoods("potatoes");
        deliveryDto.setSize(20);
        deliveryDto.setDeliveredAt(new Date());
        deliveryDto.setAcceptedAt(new Date());
        deliveryDto.setWorkedBy("admin");
        deliveryDto.setStatus("confirmed");
        assertThat(deliveryDto.getId(), equalTo((long) 1));
        assertThat(deliveryDto.getWorkedBy(), equalTo("admin"));
    }

    @Test
    public void testListingDto() {
        ListingDto listingDto = new ListingDto();
        listingDto.setId(1L);
        listingDto.setFirmName("testFirmName");
        listingDto.setPrice(200);
        listingDto.setGoods("potatoes");
        listingDto.setSize(20);
        listingDto.setListedAt(new Date());
        listingDto.setAcceptedAt(new Date());
        listingDto.setWorkedBy("admin");
        listingDto.setStatus("confirmed");
        assertThat(listingDto.getId(), equalTo((long) 1));
        assertThat(listingDto.getFirmName(), equalTo("testFirmName"));
    }

    @Test
    public void testUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("test@test.com");
        userDto.setPassword("123qwe");
        assertThat(1L, equalTo((long) 1));
        assertThat(userDto.getPassword(), equalTo("123qwe"));
    }
}
