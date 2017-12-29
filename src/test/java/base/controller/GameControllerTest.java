package base.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.command.GameReq;
import base.service.SolvedGameDto;
import base.service.SolvedWord;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GameControllerTest {

    private static final String PATH = "/api/games"; 
    private static final List<String> GAME1 = Arrays.asList("d", "a", "a", "p", "e", "l", "o", "a", "a", "l", "a", "a", "a", "a", "a", "e");
    private static final int GAME1_SCORE = 32;
    private static final List<Integer> GAME1_PATH = Arrays.asList(3, 6, 9, 5, 4, 0);
    private static final List<String> GAME2 = Arrays.asList("u", "b", "i", "q", "a", "a", "a", "u", "a", "a", "a", "i", "s", "u", "o", "t");
    private static final int GAME2_SCORE = 92;
    private static final List<Integer> GAME2_PATH = Arrays.asList(0, 1, 2, 3, 7, 11, 15, 14, 13, 12);
    private static final List<String> GAME3 = Arrays.asList("i", "l", "i", "b", "t", "s", "e", "i", "i", "p", "r", "s", "e", "s", "o", "n");
    private static final int GAME3_SCORE = 90;
    private static final List<Integer> GAME3_PATH = Arrays.asList(10, 6, 5, 9, 14, 15, 11, 7, 3, 2, 1, 0, 4, 8, 12, 13);
    private static final List<String> GAME4 = Arrays.asList("a", "a", "a", "a", "a", "e", "d", "a", "a", "o", "ll", "a", "p", "a", "a", "a");
    private static final int GAME4_SCORE = 36;
    private static final List<Integer> GAME4_PATH = Arrays.asList(12, 9, 10, 5, 6);
    private static final List<String> GAME5 = Arrays.asList("a", "a", "a", "a", "a", "b", "a", "a", "a", "o", "in-", "a", "a", "a", "x", "a");
    private static final int GAME5_SCORE = 42;
    private static final List<Integer> GAME5_PATH = Arrays.asList(10, 5, 9, 14);
    private static final List<String> GAME6 = Arrays.asList("-est", "a", "a", "a", "a", "t", "a", "a", "r", "e", "a", "a", "g", "a", "a", "a");
    private static final int GAME6_SCORE = 57;
    private static final List<Integer> GAME6_PATH = Arrays.asList(12, 8, 9, 10, 5, 0);
    private static final List<String> GAME7 = Arrays.asList("a", "a", "c", "a", "a", "i/m", "a", "h", "a", "r", "a", "e", "b", "f", "e", "t");
    private static final int GAME7_SCORE1 = 74;
    private static final List<Integer> GAME7_PATH1 = Arrays.asList(13, 8, 12, 9, 5, 2);
    private static final int GAME7_SCORE2 = 66;
    private static final List<Integer> GAME7_PATH2 = Arrays.asList(5, 6, 2, 7, 11, 15, 14);
    private static final List<String> GAME8 = Arrays.asList("a", "a", "t", "s", "a", "a", "t", "e", "a", "e", "-est", "a", "a", "r", "g", "a");
    private static final int GAME8_SCORE = 57;
    private static final List<Integer> GAME8_PATH = Arrays.asList(14, 13, 9, 5, 6, 10);
    private static final List<String> GAME9 = Arrays.asList("g", "a", "r", "g", "r", "a", "e", "a", "a", "e", "s", "t", "t", "e", "t", "-est");
    private static final int GAME9_SCORE = 57;
    private static final List<Integer> GAME9_PATH = Arrays.asList(3, 2, 6, 7, 11, 15);
    private static final List<String> GAME10 = Arrays.asList("n", "a", "b", "a", "a", "a", "b", "a", "b", "b", "b", "a", "a", "a", "a", "a");
    private static final int GAME10_SCORE = 9;
    private static final List<Integer> GAME10_PATH = Arrays.asList(0, 1, 2);
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

    private Optional<SolvedWord> findWordFromResult(MvcResult result, String word) throws JsonParseException, JsonMappingException, UnsupportedEncodingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        return dto.getWords()
                  .stream()
                  .filter(w -> w.getValue().equals(word))
                  .findFirst();
    }
    
    private long countWordInstances(MvcResult result, String word) throws JsonParseException, JsonMappingException, UnsupportedEncodingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        SolvedGameDto dto = mapper.readValue(result.getResponse().getContentAsString(), SolvedGameDto.class);
        return dto.getWords().stream()
                  .filter(w -> w.getValue().equals(word))
                  .count();
    }
    
    private <E> boolean isMatchingWordPaths(List<E> expected, List<E> actual) {
        if (null == expected || null == actual || expected.isEmpty() || actual.isEmpty() ||
            expected.size() != actual.size()) {
            return false;
        }
        for (int i=0,j=0; i < expected.size(); i++,j++) {
            if (!expected.get(i).equals(actual.get(j))) {
                return false;
            }
        }
        return true;
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
        Optional<SolvedWord> solved = findWordFromResult(result, "polled");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME1_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME1_PATH, solved.get().getPath()));
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
        Optional<SolvedWord> solved = findWordFromResult(result, "ubiquitous");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME2_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME2_PATH, solved.get().getPath()));
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
        Optional<SolvedWord> solved = findWordFromResult(result, "responsibilities");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME3_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME3_PATH, solved.get().getPath()));
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
        Optional<SolvedWord> solved = findWordFromResult(result, "polled");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME4_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME4_PATH, solved.get().getPath()));
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
        Optional<SolvedWord> solved = findWordFromResult(result, "inbox");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME5_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME5_PATH, solved.get().getPath()));
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
        Optional<SolvedWord> solved = findWordFromResult(result, "greatest");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME6_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME6_PATH, solved.get().getPath()));
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
        Optional<SolvedWord> solved = findWordFromResult(result, "fabric");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME7_SCORE1, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME7_PATH1, solved.get().getPath()));
        solved = findWordFromResult(result, "machete");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME7_SCORE2, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME7_PATH2, solved.get().getPath()));
    }

    @Test
    public void word_best_better_scoring_instance_replaces_worse_scoring_same_origin() throws Exception {
        String content = getGameAreaInJson(GAME8);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        Optional<SolvedWord> solved = findWordFromResult(result, "greatest");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME8_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME8_PATH, solved.get().getPath()));
    }

    @Test
    public void word_best_better_scoring_instance_replaces_worse_scoring() throws Exception {
        String content = getGameAreaInJson(GAME9);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        Optional<SolvedWord> solved = findWordFromResult(result, "greatest");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME9_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME9_PATH, solved.get().getPath()));
    }
    
    @Test
    public void word_nab_multi_instance_only_one_reported() throws Exception {
        String content = getGameAreaInJson(GAME10);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isOk())
                .andReturn();
        Optional<SolvedWord> solved = findWordFromResult(result, "nab");
        assertTrue("Word not found!", solved.isPresent());
        assertEquals("Score not correct", GAME10_SCORE, solved.get().getPoints());
        assertTrue("Path not correct!", isMatchingWordPaths(GAME10_PATH, solved.get().getPath()));
        assertTrue("Word should only appear once!", 1 == countWordInstances(result, "nab"));
    }
}
