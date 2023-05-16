package demo.warehouse.repository;

import demo.warehouse.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByid(Long id);

    List<Delivery> findByGoods(String goods);

    Delivery findBySupplierName(String supplierName);

    Delivery findByDeliveredAt(Date deliverdAt);

    @Query("SELECT d FROM Delivery d WHERE d.acceptedAt >= :dateFrom and d.acceptedAt <= :dateTo")
    List<Delivery> findByPeriod(Date dateFrom, Date dateTo);

    @Query("SELECT d FROM Delivery d WHERE d.acceptedAt >= :dateFrom and d.acceptedAt <= :dateTo and d.workedBy = :operator")
    List<Delivery> findByPeriodAndOperator(Date dateFrom, Date dateTo, String operator);

    @Query("SELECT d FROM Delivery d WHERE d.acceptedAt >= :dateFrom and d.acceptedAt <= :dateTo and d.goods = :goodsName")
    List<Delivery> findByPeriodAndGoods(Date dateFrom, Date dateTo, String goodsName);

    @Query("SELECT sum(d.price) FROM Delivery d WHERE d.acceptedAt >= :dateFrom and d.acceptedAt <= :dateTo and d.status = 'confirmed'")
    Integer findExpenses(Date dateFrom, Date dateTo);
}
