package demo.warehouse.service;

import demo.warehouse.dto.WarehouseDto;
import demo.warehouse.entity.Warehouse;

public interface WarehouseService {
    void createWarehouse(WarehouseDto userDto);  //needed for the cashbox functionality

    Warehouse findByName(String name);
}
