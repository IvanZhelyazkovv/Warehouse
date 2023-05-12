package demo.warehouse.controller;

import demo.warehouse.dto.ListingDto;
import demo.warehouse.entity.Goods;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.service.ListingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ListingController {
    private GoodsRepository goodsRepository;

    private ListingService listingService;

    public ListingController(GoodsRepository goodsRepository, ListingService listingService) {
        this.listingService = listingService;
        this.goodsRepository = goodsRepository;
    }

    @GetMapping("make/listing")
    public String makeListing(Model model) {
        ListingDto listing = new ListingDto();
        model.addAttribute("listing", listing);
        return "make-listing";
    }

    @PostMapping("make/listing/save")
    public String ListingGoods(
            @Valid @ModelAttribute("listing") ListingDto listing,
            BindingResult result,
            Model model) {
        Goods goods = goodsRepository.findByName(listing.getGoods());
        Integer size = goods.getSize();
        if (size <= listing.getSize()) {
            result.rejectValue("size", null, "We don't have that amount");
            return "deliveries";
        }
        if (result.hasErrors()) {
            model.addAttribute("listing", listing);
            return "make-listing";
        }
        listingService.listGoods(listing);
        return "redirect:/make/listing?success";
    }
}
