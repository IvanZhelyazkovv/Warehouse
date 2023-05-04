package demo.warehouse.service;

import demo.warehouse.dto.UserDto;
import demo.warehouse.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
