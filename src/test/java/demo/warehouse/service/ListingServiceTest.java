package demo.warehouse.service;

import demo.warehouse.dto.ListingDto;
import demo.warehouse.entity.Goods;
import demo.warehouse.entity.Listing;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.repository.ListingRepository;
import demo.warehouse.repository.WarehouseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest()
public class ListingServiceTest {

    @Autowired
    private ListingService listingService;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    public void listGoodsTest() {
        ListingDto listingDto = new ListingDto();
        listingDto.setFirmName("FirmName");
        listingDto.setPrice(200);
        listingDto.setGoods("tomatoes");
        listingDto.setSize(20);
        listingDto.setStatus("pending");
        listingService.listGoods(listingDto);
        assertThat(listingDto.getFirmName(), equalTo(listingRepository.findByGoods(listingDto.getGoods()).getFirmName()));
        assertThat(listingDto.getGoods(), equalTo(listingRepository.findByGoods(listingDto.getGoods()).getGoods()));
    }

    @Test
    public void rejectOfferTest() {
        Listing listing = new Listing();
        listing.setFirmName("Test");
        listing.setPrice(20);
        listing.setGoods("potatoes");
        listing.setSize(20);
        listing.setStatus("pending");
        listingRepository.save(listing);
        listingService.updateOffer(listing, "admin1@admin.com", "rejected");
        assertThat(listing.getFirmName(), equalTo(listingRepository.findByid(1L).getFirmName()));
        assertThat(listing.getWorkedBy(), equalTo(listingRepository.findByid(1L).getWorkedBy()));
        assertThat("rejected", equalTo(listingRepository.findByid(1L).getStatus()));
    }

    @Test
    public void acceptOfferTest() {
        Goods good = new Goods(1L, "tomatoes", 20);
        goodsRepository.save(good);
        Warehouse warehouse = new Warehouse(1L, "demo", 200000);
        warehouseRepository.save(warehouse);
        Listing listing = new Listing();
        listing.setFirmName("FirmName");
        listing.setPrice(200);
        listing.setGoods("tomatoes");
        listing.setSize(20);
        listing.setStatus("pending");
        listingRepository.save(listing);
        listingService.updateOffer(listing, "admin@admin.com", "confirmed");
        assertThat(listing.getFirmName(), equalTo(listingRepository.findByid(3L).getFirmName()));
        assertThat("admin@admin.com", equalTo(listingRepository.findByid(3L).getWorkedBy()));
        assertThat("confirmed", equalTo(listingRepository.findByid(3L).getStatus()));
        System.out.println(listingRepository.findAll());
    }
}
