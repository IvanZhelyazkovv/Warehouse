package demo.warehouse.controller;

import demo.warehouse.dto.GoodsDto;
import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Goods;
import demo.warehouse.entity.Listing;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.DeliveryRepository;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.repository.ListingRepository;
import demo.warehouse.service.WarehouseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WarehouseController {
    private WarehouseService warehouseService;

    private GoodsRepository goodsRepository;

    private DeliveryRepository deliveryRepository;

    private ListingRepository listingRepository;

    public WarehouseController(
            GoodsRepository goodsRepository,
            WarehouseService warehouseService,
            DeliveryRepository deliveryRepository,
            ListingRepository listingRepository
    ) {
        this.listingRepository = listingRepository;
        this.deliveryRepository = deliveryRepository;
        this.warehouseService = warehouseService;
        this.goodsRepository = goodsRepository;
    }

    @GetMapping("stock")
    public String makeListing(Model model) {
        List<Goods> goods = goodsRepository.findAll();
        Warehouse warehouse = warehouseService.findByName("demo");
        model.addAttribute("cashbox", warehouse.getCashbox());
        model.addAttribute("goods", goods);
        return "stock";
    }

    @GetMapping("reference")
    public String reference() {
        return "reference";
    }

    @RequestMapping(value = "/reference/check", method = RequestMethod.POST)
    public String reference(
            @RequestParam("type") String type,
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
            Model model,
            Authentication authentication
    ) {
        switch (type) {
            case "Operator":
                List<Delivery> deliveriesByOperator = deliveryRepository.findByPeriodAndOperator(dateFrom, dateTo, authentication.getName());
                model.addAttribute("deliveries", deliveriesByOperator);
                List<Listing> listingsByOperator = listingRepository.findByPeriodAndOperator(dateFrom, dateTo, authentication.getName());
                model.addAttribute("listings", listingsByOperator);
                return "operator-history";
            case "Deliveries":
                List<Delivery> deliveries = deliveryRepository.findByPeriod(dateFrom, dateTo);
                model.addAttribute("deliveries", deliveries);
                return "deliveries";
            case "listings":
                List<Listing> listings = listingRepository.findByPeriod(dateFrom, dateTo);
                model.addAttribute("deliveries", listings);
                return "listing";
            case "stock":
                final List<Goods> goods = goodsRepository.findAll();

                final List<GoodsDto> incomeGoodsDtos = goods.stream().map(good ->
                        GoodsDto.builder()
                                .name(good.getName())
                                .size(deliveryRepository.findByGoods(
                                        good.getName()).stream().map(Delivery::getSize).reduce(Integer::sum).orElse(0))
                                .build()
                ).collect(Collectors.toList());

                final List<GoodsDto> soldGoodsDtos = goods.stream().map(good ->
                        GoodsDto.builder()
                                .name(good.getName())
                                .size(listingRepository.findByGoods(
                                        good.getName()).stream().map(Listing::getSize).reduce(Integer::sum).orElse(0))
                                .build()
                ).collect(Collectors.toList());

                int expenses = deliveryRepository.findExpenses(dateFrom, dateTo);
                int income = listingRepository.findIncome(dateFrom, dateTo);
                model.addAttribute("incomeGoods", incomeGoodsDtos);
                model.addAttribute("soldGoods", soldGoodsDtos);
                model.addAttribute("expenses", income);
                model.addAttribute("income", expenses);
                model.addAttribute("profit", income - expenses);

                return "stock-reference";
        }

        return type;
    }
}
