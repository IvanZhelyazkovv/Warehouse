package demo.warehouse.service.impl;

import demo.warehouse.dto.DeliveryDto;
import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.DeliveryRepository;
import demo.warehouse.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public void createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        delivery.setSupplierName(deliveryDto.getSupplierName());
        delivery.setGoods(deliveryDto.getGoods());
        delivery.setSize(deliveryDto.getSize());
        delivery.setPrice(deliveryDto.getPrice());

        deliveryRepository.save(delivery);
    }

    @Override
    public void updateDelivery(Delivery delivery, Warehouse warehouse, String userName) {
        delivery.setAcceptedBy(userName);
        warehouse.setCashbox(warehouse.getCashbox() - delivery.getPrice());
        deliveryRepository.save(delivery);
    }

    @Override
    public Delivery findID(int id) {
        return deliveryRepository.findByid((long) id);
    }

    @Override
    public Delivery findByDeliveredAt(Date deliveredAt) {
        return deliveryRepository.findByDeliveredAt(deliveredAt);
    }

    @Override
    public Delivery findByAcceptedAt(Date acceptedAt) {
        return deliveryRepository.findByAcceptedAt(acceptedAt);
    }

    @Override
    public Delivery findBySupplier(String supplier) {
        return deliveryRepository.findBySupplierName(supplier);
    }

    @Override
    public List<DeliveryDto> findAllDeliveries() {
        List<Delivery> deliveries = deliveryRepository.findAll();
        return deliveries.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private DeliveryDto convertEntityToDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(delivery.getId());
        deliveryDto.setSupplierName(delivery.getSupplierName());
        deliveryDto.setPrice(delivery.getPrice());
        deliveryDto.setGoods(delivery.getGoods());
        deliveryDto.setSize(delivery.getSize());
        deliveryDto.setDeliveredAt(delivery.getDeliveredAt());
        deliveryDto.setAcceptedAt(delivery.getAcceptedAt());
        deliveryDto.setAcceptedBy(delivery.getAcceptedBy());
        return deliveryDto;
    }
}
