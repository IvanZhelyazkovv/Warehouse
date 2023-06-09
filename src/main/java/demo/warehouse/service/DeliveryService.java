package demo.warehouse.service;

import demo.warehouse.dto.DeliveryDto;
import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Warehouse;

import java.util.Date;
import java.util.List;

public interface DeliveryService {

    void createDelivery(DeliveryDto deliveryDto);

    String updateDelivery(Delivery delivery, Warehouse warehouse, String userName, String status);

    Delivery findID(int id);

    List<DeliveryDto> findAllDeliveries();
}
