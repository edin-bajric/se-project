package ba.edu.ibu.frent.core.model;

import ba.edu.ibu.frent.core.model.enums.UserType;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateNewUser() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void shouldSetAndGetValues() {
        User user = new User();

        user.setId("id");
        user.setUserType(UserType.MEMBER);
        user.setFirstName("Name");
        user.setLastName("Surname");
        user.setEmail("email@example.com");
        user.setUsername("username");
        user.setPassword("password");
        user.setCart(Set.of("movieId1", "movieId2"));
        user.setWishlist(Set.of("movieId1", "movieId2"));
        Date creationDate = new Date();
        user.setCreationDate(creationDate);

        assertEquals("id", user.getId());
        assertEquals(UserType.MEMBER, user.getUserType());
        assertEquals("Name", user.getFirstName());
        assertEquals("Surname", user.getLastName());
        assertEquals("email@example.com", user.getEmail());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(Set.of("movieId1", "movieId2"), user.getCart());
        assertEquals(Set.of("movieId1", "movieId2"), user.getWishlist());
        assertEquals(creationDate, user.getCreationDate());
    }

}