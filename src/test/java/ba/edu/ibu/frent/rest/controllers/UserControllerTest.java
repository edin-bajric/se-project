package ba.edu.ibu.frent.rest.controllers;

import ba.edu.ibu.frent.core.service.JwtService;
import ba.edu.ibu.frent.core.service.UserService;
import ba.edu.ibu.frent.rest.configuration.SecurityConfiguration;
import ba.edu.ibu.frent.rest.dto.RentalDTO;
import ba.edu.ibu.frent.rest.dto.UserDTO;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@Import(SecurityConfiguration.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationProvider authenticationProvider;

    @WithMockUser(authorities = "ADMIN")
    @Test
    void shouldReturnAllUsers() throws Exception {
        UserDTO user = new UserDTO();
        user.setId("id");
        user.setUsername("username");
        user.setEmail("email@example.com");
        user.setName("Name");

        Mockito.when(userService.getUsers()).thenReturn(List.of(user));

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals(1, (Integer) JsonPath.read(response, "$.length()"));
        assertEquals("username", JsonPath.read(response, "$.[0].username"));

    }
}