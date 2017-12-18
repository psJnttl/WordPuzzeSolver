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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import base.domain.Symbol;
import base.repository.SymbolRepository;
import base.service.SymbolDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SymbolControllerTest {

    private static final String PATH = "/api/symbols";
    private static final String SYMBOL1 = "nutrvdtfng";
    private static final String SYMBOL2 = "glp8kuirey";
    private static final String SYMBOL3 = "jgefgvedcg";
    private static final String EMPTY_STRING = "";
    private static final int SCORE1 = 1;
    private static final int SCORE2 = 2;
    private static final int SCORE3 = 3;
    private static final int ZEROSCORE = 0;
    private static final int NEGATIVESCORE = -1;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SymbolRepository symbolRepository;
    
    private MockMvc mockMvc;
    private Symbol s1, s2;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        s1 = new Symbol(SYMBOL1, SCORE1);
        s1 = symbolRepository.saveAndFlush(s1);
        s2 = new Symbol(SYMBOL2, SCORE2);
        s2 = symbolRepository.saveAndFlush(s2);
    }

    @After
    public void tearDown() throws Exception {
        symbolRepository.delete(s1);
        symbolRepository.delete(s2);
        Symbol s3 = symbolRepository.findByValue(SYMBOL3);
        if (null != s3) {
            symbolRepository.delete(s3);
        }
    }

    @Test
    public void listSymbolsHasResponseStatusOKandContentTypeJsonUtf8() throws Exception {
        mockMvc
                .perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void listContainsExpectedSymbols() throws Exception {
        MvcResult res = mockMvc
                                .perform(get(PATH))
                                .andReturn();
        String content = res.getResponse().getContentAsString();
        assertFalse("Project list must not be empty.", content.equals("[]"));
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked") // Ctrl only returns SymbolDto objects
        List<SymbolDto> dtos = mapper.readValue(res.getResponse().getContentAsString(), new TypeReference<List<SymbolDto>>() { });
        assertTrue(dtos.stream()
                       .filter(s -> s.getValue().equals(SYMBOL1) && s.getScore() == SCORE1)
                       .findFirst().isPresent());
        assertTrue(dtos.stream()
                .filter(s -> s.getValue().equals(SYMBOL2) && s.getScore() == SCORE2)
                .findFirst().isPresent());
    }

    @Test
    public void addingSymbolReturnsLocationHeaderAndDto() throws Exception {
        SymbolAdd newSymbol = new SymbolAdd(SYMBOL3, SCORE3);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newSymbol);
        MvcResult result = mockMvc
                                   .perform(
                                           post(PATH)
                                           .contentType(MediaType.APPLICATION_JSON_UTF8)
                                           .content(content))
                                   .andExpect(status().isCreated())
                                   .andExpect(header().string("Location", containsString(PATH + "/")))
                                   .andReturn();
        SymbolDto dto = mapper.readValue(result.getResponse().getContentAsString(), SymbolDto.class);
        assertTrue("Didn't return correct value", dto.getValue().equals(SYMBOL3));
        assertTrue("Didn't return correct score", dto.getScore() == SCORE3);
    }
    
    @Test
    public void addingSymbolThatExistsFails() throws Exception {
        SymbolAdd newSymbol = new SymbolAdd(SYMBOL1, SCORE1);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newSymbol);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isLocked());
    }
    
    @Test
    public void addingSymbolWithoutValueFails() throws Exception {
        SymbolAdd newSymbol = new SymbolAdd(EMPTY_STRING, SCORE1);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newSymbol);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addingSymbolWithZeroScoreFails() throws Exception {
        SymbolAdd newSymbol = new SymbolAdd(SYMBOL3, ZEROSCORE);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newSymbol);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void addingSymbolWithNegativeScoreFails() throws Exception {
        SymbolAdd newSymbol = new SymbolAdd(SYMBOL3, NEGATIVESCORE);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newSymbol);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isBadRequest());
    }
}
