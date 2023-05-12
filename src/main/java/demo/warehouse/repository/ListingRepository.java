package demo.warehouse.repository;

import demo.warehouse.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    Listing findByid(Long id);

    Listing findByOperatorName(String operatorName);

    Listing findByListedAt(Date listedAt);

    Listing findByPrice(int price);

    Listing findByGoods(String goods);
}
