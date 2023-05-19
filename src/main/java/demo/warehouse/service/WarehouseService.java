package demo.warehouse.service;

import demo.warehouse.entity.Warehouse;

public interface WarehouseService {
    Warehouse findByName(String name);
}
