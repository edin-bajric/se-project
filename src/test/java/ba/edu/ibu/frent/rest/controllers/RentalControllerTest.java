package ba.edu.ibu.frent.rest.controllers;

import ba.edu.ibu.frent.core.service.JwtService;
import ba.edu.ibu.frent.core.service.RentalService;
import ba.edu.ibu.frent.core.service.UserService;
import ba.edu.ibu.frent.rest.configuration.SecurityConfiguration;
import ba.edu.ibu.frent.rest.dto.RentalDTO;
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
@WebMvcTest(RentalController.class)
@Import(SecurityConfiguration.class)
class RentalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RentalService rentalService;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationProvider authenticationProvider;

    @WithMockUser(authorities = "ADMIN")
    @Test
    void shouldReturnAllRentals() throws Exception {
        RentalDTO rental = new RentalDTO();
        rental.setId("id");
        rental.setUsername("username");
        rental.setMovieId("movieId");
        rental.setRentalPrice(5.99);

        Mockito.when(rentalService.getRentals()).thenReturn(List.of(rental));

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/rentals/")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals(1, (Integer) JsonPath.read(response, "$.length()"));
        assertEquals("username", JsonPath.read(response, "$.[0].username"));

    }
}