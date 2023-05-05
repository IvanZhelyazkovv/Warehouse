package demo.warehouse.controller;

import demo.warehouse.dto.UserDto;
import demo.warehouse.dto.WarehouseDto;
import demo.warehouse.entity.User;
import demo.warehouse.entity.Warehouse;
import demo.warehouse.service.UserService;
import demo.warehouse.service.WarehouseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    private UserService userService;
    private WarehouseService warehouseService;

    public DefaultController(UserService userService, WarehouseService warehouseService) {
        this.userService = userService;
        this.warehouseService = warehouseService;
    }

    @GetMapping("defaultConfig")
    public String defaultConfig() {
        User user = userService.findByEmail("admin@admin.com");
        Warehouse warehouse = warehouseService.findByName("demo");
        if (warehouse == null) {
            createWarehouse();
        }

        if (user == null) {
            defaultUser();
        }

        return "index";
    }

    public void defaultUser() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("admin");
        userDto.setLastName("admin");
        userDto.setEmail("admin@admin.com");
        userDto.setPassword("123qwe");
        userDto.setRole("ROLE_ADMIN");

        userService.saveUser(userDto);
    }

    public void createWarehouse() {
        WarehouseDto warehouseDto = new WarehouseDto();
        warehouseDto.setName("demo");
        warehouseDto.setCashbox(200000);

        warehouseService.createWarehouse(warehouseDto);
    }
}
