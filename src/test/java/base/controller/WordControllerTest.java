package base.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import base.domain.Word;
import base.repository.WordRepository;
import base.service.WordDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WordControllerTest {

    private static final String PATH = "/api/words";
    private static final String WORD1 = "inuyweycweio";
    private static final String WORD2 = "nhnhngignecw";
    private static final String WORD3 = "pokipkiojNdw";
    private static final String EMPTY_STRING = "";
    private static final long WRONG_ID = Long.MAX_VALUE;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private WordRepository wordRepository;
    
    private MockMvc mockMvc;
    private Word w1, w2;
    
    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        w1 = new Word(WORD1);
        wordRepository.saveAndFlush(w1);
        w2 = new Word(WORD2);
        wordRepository.saveAndFlush(w2);
    }

    @After
    public void tearDown() throws Exception {
        wordRepository.delete(w1);
        wordRepository.delete(w2);
    }

    @Test
    public void listWordsHasResponseStatusOKandContentTypeJsonUtf8() throws Exception {
        mockMvc
                .perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void listMustNotBeEmpty() throws Exception {
        MvcResult res = mockMvc
                .perform(get(PATH))
                .andReturn();
        String content = res.getResponse().getContentAsString();
        assertFalse("Project list must not be empty.", content.equals("[]"));
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked") // Ctrl only returns WordDto objects
        List<WordDto> dtos = mapper.readValue(res.getResponse().getContentAsString(), new TypeReference<List<WordDto>>() { });
        assertEquals(2, dtos.stream()
                            .filter(w -> w.getValue().equals(WORD1) || w.getValue().equals(WORD2))
                            .count());
    }

    @Test
    public void addingWordReturnsLocationHeaderAndDto() throws Exception {
        WordAdd word = new WordAdd(WORD3);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(word);
        MvcResult result = mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(PATH + "/")))
                .andReturn();
        WordDto dto = mapper.readValue(result.getResponse().getContentAsString(), WordDto.class);
        assertTrue("Didn't return correct word", dto.getValue().equals(WORD3));
    }
    
    @Test
    public void addingWordWithoutContentsFails() throws Exception {
        WordAdd word = new WordAdd(EMPTY_STRING);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(word);
        mockMvc
                .perform(
                        post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void addingWordThatExistsFails() throws Exception {
        WordAdd word = new WordAdd(WORD1);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(word);
        mockMvc
                .perform(
                        post(PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isLocked());
    }
    
    @Test
    public void fetchSingleWord() throws Exception {
        MvcResult result = mockMvc
                .perform(get(PATH + "/" + w1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertFalse("Contents cannot be empty!", result.getResponse().getContentAsString().isEmpty());
        ObjectMapper mapper = new ObjectMapper();
        WordDto dto = mapper.readValue(result.getResponse().getContentAsString(), WordDto.class);
        assertTrue(dto.getValue().equals(WORD1));
    }
    
    @Test
    public void fetchSingleWordWithWrongIdFails() throws Exception {
        mockMvc
                .perform(get(PATH + "/" + WRONG_ID))
                .andExpect(status().isNotFound());
    }
}
