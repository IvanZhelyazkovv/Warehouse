package demo.warehouse.repository;

import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    Listing findByid(Long id);

    Listing findByFirmName(String firmName);

    Listing findByListedAt(Date listedAt);

    Listing findByPrice(int price);

    List<Listing> findByGoods(String goods);

    @Query("SELECT d FROM Listing d WHERE d.acceptedAt >= :dateFrom and d.acceptedAt <= :dateTo")
    List<Listing> findByPeriod(Date dateFrom, Date dateTo);

    @Query("SELECT d FROM Listing d WHERE d.acceptedAt >= :dateFrom and d.acceptedAt <= :dateTo and d.workedBy = :operator")
    List<Listing> findByPeriodAndOperator(Date dateFrom, Date dateTo, String operator);

    @Query("SELECT SUM(d.price) FROM Listing d WHERE d.acceptedAt >= :dateFrom and d.acceptedAt <= :dateTo and d.status = 'confirmed' ")
    Integer findIncome(Date dateFrom, Date dateTo);
}
