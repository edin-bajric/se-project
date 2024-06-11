package ba.edu.ibu.frent.core.service;

import ba.edu.ibu.frent.core.model.User;
import ba.edu.ibu.frent.core.repository.UserRepository;
import ba.edu.ibu.frent.rest.dto.UserDTO;
import ba.edu.ibu.frent.rest.dto.UserRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void shouldReturnUserWhenCreated() {
        User user = new User();
        user.setId("id");
        user.setEmail("email@example.com");
        user.setPassword("password");
        user.setUsername("username");

        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        UserDTO savedUser = userService.addUser(new UserRequestDTO(user));
        Assertions.assertThat(user.getUsername()).isEqualTo(savedUser.getUsername());
        Assertions.assertThat(user.getPassword()).isNotNull();
    }

    @Test
    public void shouldReturnUserById() {
        User user = new User();
        user.setId("id");
        user.setEmail("email@example.com");
        user.setPassword("password");
        user.setUsername("username");

        Mockito.when(userRepository.findById("id")).thenReturn(Optional.of(user));
        UserDTO foundUser = userService.getUserById("id");
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("username");
    }
}