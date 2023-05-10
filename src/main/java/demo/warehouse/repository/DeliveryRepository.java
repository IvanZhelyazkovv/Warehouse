package demo.warehouse.repository;

import demo.warehouse.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Delivery findByid(Long id);
    Delivery findBySupplierName(String supplierName);

    Delivery findByDeliveredAt(Date deliverdAt);

    Delivery findByAcceptedAt(Date acceptedAt);

}
