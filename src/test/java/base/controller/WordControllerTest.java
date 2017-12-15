package base.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

}
