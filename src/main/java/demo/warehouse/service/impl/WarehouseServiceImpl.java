package demo.warehouse.service.impl;

import demo.warehouse.entity.Warehouse;
import demo.warehouse.repository.WarehouseRepository;
import demo.warehouse.service.WarehouseService;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private WarehouseRepository warehouseRepository;

    public WarehouseServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public Warehouse findByName(String name) {
        return warehouseRepository.findByName(name);
    }
}
