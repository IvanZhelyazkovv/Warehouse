package demo.warehouse.service;

import demo.warehouse.dto.ListingDto;
import demo.warehouse.entity.Listing;

import java.util.Date;
import java.util.List;

public interface ListingService {
    void listGoods(ListingDto listingDto);

    Listing findByid(Long id);

    Listing findByOperatorName(String operatorName);

    Listing findByListedAt(Date listedAt);

    Listing findByPrice(int price);

    List<ListingDto> findAllListings();
}
