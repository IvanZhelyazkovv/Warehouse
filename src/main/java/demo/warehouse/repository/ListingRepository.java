package demo.warehouse.repository;

import demo.warehouse.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    Listing findByid(Long id);

    Listing findByGoods(String goods);

    @Query("SELECT l FROM Listing l WHERE l.acceptedAt >= :dateFrom and l.acceptedAt <= :dateTo")
    List<Listing> findByPeriod(Date dateFrom, Date dateTo);

    @Query("SELECT l FROM Listing l WHERE l.acceptedAt >= :dateFrom and l.acceptedAt <= :dateTo and l.goods = :good")
    List<Listing> findByPeriodAndGood(Date dateFrom, Date dateTo,String good);

    @Query("SELECT l FROM Listing l WHERE l.acceptedAt >= :dateFrom and l.acceptedAt <= :dateTo and l.workedBy = :operator")
    List<Listing> findByPeriodAndOperator(Date dateFrom, Date dateTo, String operator);

    @Query("SELECT SUM(l.price) FROM Listing l WHERE l.acceptedAt >= :dateFrom and l.acceptedAt <= :dateTo and l.status = 'confirmed' ")
    Integer findIncome(Date dateFrom, Date dateTo);
}
