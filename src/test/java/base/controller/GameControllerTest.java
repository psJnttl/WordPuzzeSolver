package base.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.command.GameReq;
import base.service.SolvedGameDto;
import base.service.SolvedWord;
import base.service.WordDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GameControllerTest {

    private static final String PATH = "/api/games"; 
    private static final List<String> GAME1 = Arrays.asList("d", "a", "a", "p", "e", "l", "o", "a", "a", "l", "a", "a", "a", "a", "a", "e");
    private static final List<String> GAME2 = Arrays.asList("u", "b", "i", "q", "a", "a", "a", "u", "a", "a", "a", "i", "s", "u", "o", "t");
    private static final List<String> GAME3 = Arrays.asList("i", "l", "i", "b", "t", "s", "e", "i", "i", "p", "r", "s", "e", "s", "o", "n");
    private static final List<String> GAME4 = Arrays.asList("a", "a", "a", "a", "a", "e", "d", "a", "a", "o", "ll", "a", "p", "a", "a", "a");
    private static final List<String> GAME5 = Arrays.asList("a", "a", "a", "a", "a", "b", "a", "a", "a", "o", "in-", "a", "a", "a", "x", "a");
    private static final List<String> GAME6 = Arrays.asList("-est", "a", "a", "a", "a", "t", "a", "a", "r", "e", "a", "a", "g", "a", "a", "a");
    private static final List<String> GAME7 = Arrays.asList("a", "a", "c", "a", "a", "i/m", "a", "h", "a", "r", "a", "e", "b", "f", "e", "t");
    private static final List<String> GAME_TOO_SHORT = Arrays.asList("d", "a", "a", "p", "e", "a", "o", "a", "a", "l", "a", "a", "a", "a", "a");
    private static final List<String> GAME_TOO_LONG = Arrays.asList("d", "a", "a", "p", "e", "a", "o", "a", "a", "l", "a", "a", "a", "a", "a", "e", "h");
    private static final List<String> GAME_WITH_NUMBER0 = Arrays.asList("d", "a", "a", "p", "e", "a", "0", "a", "a", "l", "a", "a", "a", "a", "a", "e");

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
    }

    private String getGameAreaInJson(List<String> gameArea) throws JsonProcessingException {
        GameReq game = new GameReq(gameArea);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(game);
    }

    @Test
    public void fails_with_too_short_game_area() throws Exception {
        String content = getGameAreaInJson(GAME_TOO_SHORT);
        mockMvc
                .perform(
                        post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void fails_with_too_long_game_area() throws Exception {
        String content = getGameAreaInJson(GAME_TOO_LONG);
        mockMvc
                .perform(
                        post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void fails_with_number_0_in_game_area() throws Exception {
        String content = getGameAreaInJson(GAME_WITH_NUMBER0);
        mockMvc
                .perform(
                        post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void word_polled_is_found() throws Exception {
        String content = getGameAreaInJson(GAME1);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        assertTrue(dto.getWords().stream()
                        .filter(w -> w.getValue().equals("polled"))
                        .findFirst().isPresent());
    }
    
    @Test
    public void word_ubiquitous_is_found() throws Exception {
        String content = getGameAreaInJson(GAME2);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        assertTrue(dto.getWords().stream()
                        .filter(w -> w.getValue().equals("ubiquitous"))
                        .findFirst().isPresent());
    }

    @Test
    public void word_responsibilities_is_found() throws Exception {
        String content = getGameAreaInJson(GAME3);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        assertTrue(dto.getWords().stream()
                        .filter(w -> w.getValue().equals("responsibilities"))
                        .findFirst().isPresent());
    }
    
    @Test
    public void word_polled_with_digram_is_found() throws Exception {
        String content = getGameAreaInJson(GAME4);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        assertTrue(dto.getWords().stream()
                        .filter(w -> w.getValue().equals("polled"))
                        .findFirst().isPresent());
    }

    @Test
    public void word_inbox_with_digram_first_is_found() throws Exception {
        String content = getGameAreaInJson(GAME5);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        assertTrue(dto.getWords().stream()
                        .filter(w -> w.getValue().equals("inbox"))
                        .findFirst().isPresent());
    }
    
    @Test
    public void word_greatest_with_digram_last_is_found() throws Exception {
        String content = getGameAreaInJson(GAME6);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        assertTrue(dto.getWords().stream()
                        .filter(w -> w.getValue().equals("greatest"))
                        .findFirst().isPresent());
    }
    
    @Test
    public void words_machete_fabric_with_digram_eitheror_are_found() throws Exception {
        String content = getGameAreaInJson(GAME7);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        assertEquals(2, dto.getWords().stream()
                        .filter(w -> w.getValue().equals("fabric") || w.getValue().equals("machete"))
                        .count());
    }


}
