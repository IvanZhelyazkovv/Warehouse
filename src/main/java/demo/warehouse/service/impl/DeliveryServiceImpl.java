package demo.warehouse.service.impl;

import demo.warehouse.dto.DeliveryDto;
import demo.warehouse.entity.Delivery;
import demo.warehouse.entity.Goods;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.DeliveryRepository;
import demo.warehouse.repository.GoodsRepository;
import demo.warehouse.repository.WarehouseRepository;
import demo.warehouse.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;

    private WarehouseRepository warehouseRepository;

    private GoodsRepository goodsRepository;

    public DeliveryServiceImpl(
            DeliveryRepository deliveryRepository,
            GoodsRepository goodsRepository,
            WarehouseRepository warehouseRepository
    ) {
        this.warehouseRepository = warehouseRepository;
        this.goodsRepository = goodsRepository;
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
    public String updateDelivery(Delivery delivery, Warehouse warehouse, String userName, String status) {
        String message = "success";
        delivery.setStatus(status);
        delivery.setWorkedBy(userName);
        if (!Objects.equals(status, "rejected")) {
            warehouse.setCashbox(warehouse.getCashbox() - delivery.getPrice());
            warehouseRepository.save(warehouse);

            if (warehouse.getCashbox() <= 10000) {
                message = "critical";
            }

            Goods goods = goodsRepository.findByName(delivery.getGoods());
            if (goods != null) {
                goods.setSize(goods.getSize() + delivery.getSize());
            } else {
                goods = new Goods();
                goods.setName(delivery.getGoods());
                goods.setSize(delivery.getSize());
            }

            goodsRepository.save(goods);
        }

        deliveryRepository.save(delivery);

        return message;
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
    public List<Delivery> findByPeriod(Date dateFrom, Date dateTo) {
        return deliveryRepository.findByPeriod(dateFrom, dateTo);
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
        deliveryDto.setWorkedBy(delivery.getWorkedBy());
        deliveryDto.setStatus(delivery.getStatus());
        return deliveryDto;
    }
}
