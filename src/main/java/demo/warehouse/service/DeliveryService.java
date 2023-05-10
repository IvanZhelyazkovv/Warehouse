package demo.warehouse.service;

import demo.warehouse.dto.DeliveryDto;
import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Warehouse;

import java.util.Date;
import java.util.List;

public interface DeliveryService {

    void createDelivery(DeliveryDto deliveryDto);

    void updateDelivery(Delivery delivery, Warehouse warehouse, String userName);

    Delivery findID(int id);

    Delivery findByDeliveredAt(Date deliveredAt);

    Delivery findByAcceptedAt(Date acceptedAt);

    Delivery findBySupplier(String supplier);

    List<DeliveryDto> findAllDeliveries();
}
