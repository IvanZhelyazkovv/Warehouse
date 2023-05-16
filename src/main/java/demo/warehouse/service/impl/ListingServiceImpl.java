package demo.warehouse.service.impl;

import demo.warehouse.dto.ListingDto;
import demo.warehouse.entity.Goods;
import demo.warehouse.entity.Listing;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.repository.ListingRepository;
import demo.warehouse.repository.WarehouseRepository;
import demo.warehouse.service.ListingService;
import demo.warehouse.service.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {
    private ListingRepository listingRepository;
    private GoodsRepository goodsRepository;

    private WarehouseRepository warehouseRepository;

    private WarehouseService warehouseService;

    public ListingServiceImpl(
            ListingRepository listingRepository,
            GoodsRepository goodsRepository,
            WarehouseRepository warehouseRepository,
            WarehouseService warehouseService
    ) {
        this.warehouseService = warehouseService;
        this.warehouseRepository = warehouseRepository;
        this.goodsRepository = goodsRepository;
        this.listingRepository = listingRepository;
    }

    @Override
    public void listGoods(ListingDto listingDto) {
        Listing listing = new Listing();
        listing.setFirmName(listingDto.getFirmName());
        listing.setGoods(listingDto.getGoods());
        listing.setSize(listingDto.getSize());
        listing.setPrice(listingDto.getPrice());
        listingRepository.save(listing);
    }

    @Override
    public String updateOffer(Listing listing, String userName, String status)  {
        String message = "success";
        listing.setStatus(status);
        listing.setWorkedBy(userName);
        if (!Objects.equals(status, "rejected")) {
            Goods good = goodsRepository.findByName(listing.getGoods());
            good.setSize(good.getSize() - listing.getSize());
            goodsRepository.save(good);

            if (good.getSize() <= 100) {
                message = "critical";
            }

            Warehouse warehouse = warehouseService.findByName("demo");
            warehouse.setCashbox(warehouse.getCashbox() + listing.getPrice());
            warehouseRepository.save(warehouse);
        }

        listingRepository.save(listing);

        return message;
    }

    @Override
    public Listing findByid(Long id) {
        return listingRepository.findByid((long) id);
    }

    @Override
    public Listing findByFirmName(String firmName) {
        return listingRepository.findByFirmName(firmName);
    }

    @Override
    public Listing findByListedAt(Date listedAt) {
        return listingRepository.findByListedAt(listedAt);
    }

    @Override
    public Listing findByPrice(int price) {
        return listingRepository.findByPrice(price);
    }

    @Override
    public List<ListingDto> findAllListings() {
        List<Listing> listings = listingRepository.findAll();
        return listings.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private ListingDto convertEntityToDto(Listing listing) {
        ListingDto listingDto = new ListingDto();
        listingDto.setId(listing.getId());
        listingDto.setFirmName(listing.getFirmName());
        listingDto.setPrice(listing.getPrice());
        listingDto.setGoods(listing.getGoods());
        listingDto.setSize(listing.getSize());
        listingDto.setListedAt(listing.getListedAt());
        listingDto.setAcceptedAt(listing.getAcceptedAt());
        listingDto.setWorkedBy(listing.getWorkedBy());
        listingDto.setStatus(listing.getStatus());
        return listingDto;
    }
}
