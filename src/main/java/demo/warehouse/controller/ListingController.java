package demo.warehouse.controller;

import demo.warehouse.dto.ListingDto;
import demo.warehouse.entity.Listing;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.service.ListingService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    public String ListingGoods(@Valid @ModelAttribute("listing") ListingDto listing) {
        listingService.listGoods(listing);
        return "redirect:/make/listing?success";
    }

    @GetMapping("accept/offer/{id}")
    public String acceptOffer(@PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Listing listing = listingService.findByid((long) id);

        String message = listingService.updateOffer(listing, auth.getName(), "confirmed");

        return "redirect:/listings?" + message;
    }

    @GetMapping("reject/offer/{id}")
    public String rejectOffer(@PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Listing listing = listingService.findByid((long) id);

        listingService.updateOffer(listing, auth.getName(), "rejected");

        return "redirect:/listings?reject";
    }

    @GetMapping("listings")
    public String listOffers(Model model) {
        List<ListingDto> listings = listingService.findAllListings();
        model.addAttribute("listings", listings);
        model.addAttribute("reference", false);
        return "listings";
    }
}
