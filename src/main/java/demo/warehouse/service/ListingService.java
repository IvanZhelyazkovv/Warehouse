package demo.warehouse.service;

import demo.warehouse.dto.ListingDto;
import demo.warehouse.entity.Listing;

import java.util.Date;
import java.util.List;

public interface ListingService {
    void listGoods(ListingDto ListingDto);

    String updateOffer(Listing listing, String userName, String status);
    Listing findByid(Long id);

    Listing findByFirmName(String firmName);

    Listing findByListedAt(Date listedAt);

    Listing findByPrice(int price);

    List<ListingDto> findAllListings();
}
