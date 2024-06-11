package ba.edu.ibu.frent.core.repository;

import ba.edu.ibu.frent.core.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnAllUsers() {
        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
        assertEquals("jon", users.get(0).getUsername());
    }

    @Test
    public void shouldFindUserByEmail() {
        Optional<User> user = userRepository.findByEmail("pacdoe1@gmail.com");
        assertNotNull(user.orElse(null));
    }
}