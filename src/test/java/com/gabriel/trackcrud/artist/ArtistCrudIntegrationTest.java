package com.gabriel.trackcrud.artist;

import com.gabriel.trackcrud.TestcontainersConfiguration;
import com.gabriel.trackcrud.repository.ArtistRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class ArtistCrudIntegrationTest {
    private final JsonMapper JSON = JsonMapper.builder().build();

    @Autowired
    WebApplicationContext context;

    @Autowired
    ArtistRepository artistRepository;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        artistRepository.deleteAll();
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    void missingMandatoryFieldIsRejected() throws Exception {
        mockMvc.perform(post("/api/artists")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"country": "no title"}
                        """))
                .andExpect(status().isBadRequest());
    }



    @Test
    void updateChangesBasicData() throws Exception {
        String id = create("edit");
        mockMvc.perform(put("/api/artists/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"name": "name edit",
                        "country": "seed"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name edit"))
                .andExpect(jsonPath("$.updatedBy").value("admin"));
    }

    @Test
    void createHasHeaderLocation() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + "Artist Example" + "\",\"country\":\"seed\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        String location = mvcResult.getResponse().getHeader("Location");
        String id = JSON.readTree(body).get("id").asString();
        assertThat(location).isEqualTo("/api/artists/" + id);
    }

    String create(String name) throws Exception {
        String body = mockMvc.perform(post("/api/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + name + "\",\"country\":\"seed\"}"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return JSON.readTree(body).get("id").asString();
    }
}
