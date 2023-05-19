package demo.warehouse.service;

import demo.warehouse.dto.UserDto;
import demo.warehouse.entity.Role;
import demo.warehouse.entity.User;
import demo.warehouse.repository.RoleRepository;
import demo.warehouse.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest()
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void saveUserTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setEmail("test@test.com");
        userDto.setPassword("123qwe");
        userDto.setRole("ROLE_ADMIN");
        userService.saveUser(userDto);
        assertThat(userDto.getEmail(), equalTo(userRepository.findByEmail(userDto.getEmail()).getEmail()));
    }

    @Test
    public void findByEmailTest() throws Exception {
        User user = new User();
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        user.setName("firstName lastName");
        user.setEmail("test1@test.com");
        user.setPassword("123qwe");
        user.setRoles(Arrays.asList(role));

        roleRepository.save(role);
        userRepository.save(user);
        userService.findByEmail(user.getEmail());
        assertThat(user.getName(), equalTo(userService.findByEmail(user.getEmail()).getName()));
    }
}
