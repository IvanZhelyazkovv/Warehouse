package demo.warehouse.service;

import demo.warehouse.dto.DeliveryDto;
import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Warehouse;

public interface DeliveryService {

    void createDelivery(DeliveryDto deliveryDto, Warehouse warehouse);

    Delivery findByDeliveredAt(String deliveredAt);

    Delivery findByAcceptedAt(String acceptedAt);

    Delivery findBySupplier(String supplier);
}
