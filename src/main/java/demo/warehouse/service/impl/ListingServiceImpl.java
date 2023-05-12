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
        listing.setOperatorName(listingDto.getOperatorName());
        listing.setGoods(listingDto.getGoods());
        listing.setSize(listingDto.getSize());
        listing.setPrice(listingDto.getPrice());
        listingRepository.save(listing);

        Goods goods = goodsRepository.findByName(listing.getGoods());
        goods.setSize(goods.getSize() - listing.getSize());
        goodsRepository.save(goods);
        Warehouse warehouse = warehouseService.findByName("demo");
        warehouse.setCashbox(warehouse.getCashbox() + listing.getPrice());
        warehouseRepository.save(warehouse);
    }

    @Override
    public Listing findByid(Long id) {
        return listingRepository.findByid((long) id);
    }

    @Override
    public Listing findByOperatorName(String operatorName) {
        return listingRepository.findByOperatorName(operatorName);
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
        listingDto.setOperatorName(listing.getOperatorName());
        listingDto.setPrice(listing.getPrice());
        listingDto.setGoods(listing.getGoods());
        listingDto.setSize(listing.getSize());
        listingDto.setListedAt(listing.getListedAt());
        return listingDto;
    }
}
