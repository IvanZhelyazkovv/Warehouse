package demo.warehouse.controller;

import demo.warehouse.entity.Goods;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.service.WarehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WarehouseController {
    private WarehouseService warehouseService;

    private GoodsRepository goodsRepository;

    public WarehouseController(GoodsRepository goodsRepository, WarehouseService warehouseService) {
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
}
