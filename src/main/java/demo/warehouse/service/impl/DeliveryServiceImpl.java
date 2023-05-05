package demo.warehouse.service.impl;

import demo.warehouse.dto.DeliveryDto;
import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.DeliveryRepository;
import demo.warehouse.service.DeliveryService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public void createDelivery(DeliveryDto deliveryDto, Warehouse warehouse) {
        Delivery delivery = new Delivery();
        delivery.setSupplierName(deliveryDto.getSupplierName());
        delivery.setGoods(deliveryDto.getGoods());
        delivery.setSize(deliveryDto.getSize());
        delivery.setPrice(deliveryDto.getPrice());

        warehouse.setCashbox(warehouse.getCashbox() - deliveryDto.getPrice());
        deliveryRepository.save(delivery);
    }

    @Override
    public Delivery findByDeliveredAt(String deliveredAt) {
        return null;
    }

    @Override
    public Delivery findByAcceptedAt(String acceptedAt) {
        return null;
    }

    @Override
    public Delivery findBySupplier(String supplier) {
        return null;
    }
}
