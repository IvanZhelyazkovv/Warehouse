package demo.warehouse.controller;

import demo.warehouse.dto.DeliveryDto;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.service.DeliveryService;
import demo.warehouse.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeliveryController {

    private WarehouseService warehouseService;

    private DeliveryService deliveryService;

    public DeliveryController(WarehouseService warehouseService, DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        this.warehouseService = warehouseService;
    }

    @GetMapping("make/delivery")
    public String makeDelivery(Model model) {
        DeliveryDto delivery = new DeliveryDto();
        model.addAttribute("delivery", delivery);
        return "make-delivery";
    }

    @PostMapping("make/delivery/save")
    public String makeDeliverySave(@Valid @ModelAttribute("delivery") DeliveryDto delivery,
                                   BindingResult result,
                                   Model model) {
        Warehouse warehouse = warehouseService.findByName("demo");
        Integer noMoney = warehouse.getCashbox();
        if (noMoney <= 0) {
            result.rejectValue("price", null, "We can't accept deliveries right now");
        }
        if (result.hasErrors()) {
            model.addAttribute("delivery", delivery);
            return "make-delivery";
        }
        deliveryService.createDelivery(delivery, warehouse);
        return "redirect:/make/delivery?success";
    }
}
