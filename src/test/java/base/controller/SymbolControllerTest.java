package base.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import base.command.SymbolAdd;
import base.command.SymbolMod;
import base.domain.Symbol;
import base.dto.SymbolDto;
import base.repository.SymbolRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SymbolControllerTest {

    private static final String PATH = "/api/symbols";
    private static final String SYMBOL1 = "no";
    private static final String SYMBOL2 = "en";
    private static final String SYMBOL3 = "ab";
    private static final String SYMBOL_DIGRAM3 = "ddi";
    private static final String SYMBOL_DIGRAM4 = "ddin";
    private static final String SYMBOL_DIGRAM_FIRST1 = "q-";
    private static final String SYMBOL_DIGRAM_FIRST2 = "en-";
    private static final String SYMBOL_DIGRAM_FIRST3 = "dis-";
    private static final String SYMBOL_DIGRAM_FIRST4 = "entw-";
    private static final String SYMBOL_DIGRAM_LAST1 = "-t";
    private static final String SYMBOL_DIGRAM_LAST2 = "-ed";
    private static final String SYMBOL_DIGRAM_LAST3 = "-sed";
    private static final String SYMBOL_DIGRAM_LAST4 = "-rest";
    private static final String SYMBOL_DIGRAM_EITHEROR1 = "q/m";
    private static final String SYMBOL_DIGRAM_EITHEROR1_2 = "q/mo";
    private static final String SYMBOL_DIGRAM_EITHEROR2_1 = "qu/m";
    
    private static final String EMPTY_STRING = "";
    private static final int SCORE1 = 1;
    private static final int SCORE2 = 2;
    private static final int SCORE3 = 3;
    private static final int ZEROSCORE = 0;
    private static final int NEGATIVESCORE = -1;
    private static final long WRONG_ID = Long.MAX_VALUE;

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

    private String symbolAddAsString(String symbol, int score) throws JsonProcessingException {
        SymbolAdd newSymbol = new SymbolAdd(symbol, score);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newSymbol);
        return content;
    }
    
    @Test
    public void addingSymbolReturnsLocationHeaderAndDto() throws Exception {
        String content = symbolAddAsString(SYMBOL3, SCORE3);
        MvcResult result = mockMvc
                                   .perform(
                                           post(PATH)
                                           .contentType(MediaType.APPLICATION_JSON_UTF8)
                                           .content(content))
                                   .andExpect(status().isCreated())
                                   .andExpect(header().string("Location", containsString(PATH + "/")))
                                   .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SymbolDto dto = mapper.readValue(result.getResponse().getContentAsString(), SymbolDto.class);
        assertTrue("Didn't return correct value", dto.getValue().equals(SYMBOL3));
        assertTrue("Didn't return correct score", dto.getScore() == SCORE3);
    }
    
    @Test
    public void addingSymbolThatExistsFails() throws Exception {
        String content = symbolAddAsString(SYMBOL1, SCORE1);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isLocked());
    }
    
    @Test
    public void addingSymbolWithoutValueFails() throws Exception {
        String content = symbolAddAsString(EMPTY_STRING, SCORE1);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addingSymbolWithZeroScoreFails() throws Exception {
        String content = symbolAddAsString(SYMBOL3, ZEROSCORE);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void addingSymbolWithNegativeScoreFails() throws Exception {
        String content = symbolAddAsString(SYMBOL3, NEGATIVESCORE);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void fetchSingleSymbol() throws Exception {
        MvcResult result = mockMvc
                .perform(get(PATH + "/" + s1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        assertFalse("Contents cannot be empty!", result.getResponse().getContentAsString().isEmpty());
        ObjectMapper mapper = new ObjectMapper();
        SymbolDto dto = mapper.readValue(result.getResponse().getContentAsString(), SymbolDto.class);
        assertTrue(dto.getValue().equals(s1.getValue()));
        assertTrue(dto.getScore() == s1.getScore());
    }
    
    @Test
    public void fetchSingleSymbolWithWrongIdFails() throws Exception {
        mockMvc
                .perform(get(PATH + "/" + WRONG_ID))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void deleteSymbolOK() throws Exception {
        mockMvc.perform(
                delete(PATH + "/" + s2.getId()))
                .andExpect(status().isOk());
    }
    
    @Test
    public void deleteSymbolWithWrongIdFails() throws Exception {
        mockMvc.perform(
                delete(PATH + "/" + WRONG_ID))
                .andExpect(status().isNotFound());
    }
    
    private String symbolModAsString(long id, String symbol, int score) throws JsonProcessingException {
        SymbolMod newSymbol = new SymbolMod(id, symbol, score);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newSymbol);
        return content;
    }
    
    @Test
    public void modifySymbolOKandReturnsChangedContent() throws Exception {   
        String content = symbolModAsString(s2.getId(), SYMBOL3, SCORE3);
        MvcResult result = mockMvc
                                   .perform(
                                           put(PATH + "/" + s2.getId())
                                           .contentType(MediaType.APPLICATION_JSON_UTF8)
                                           .content(content))
                                   .andExpect(status().isOk())
                                   .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SymbolDto dto = mapper.readValue(result.getResponse().getContentAsString(), SymbolDto.class);
        assertTrue("Value to changed correctly!", dto.getValue().equals(SYMBOL3));
        assertTrue("Score not changed correctly!", dto.getScore() == SCORE3);
    }
    
    @Test
    public void modifyingSymbolWithWrongIdFails() throws Exception {
        String content = symbolModAsString(s2.getId(), SYMBOL3, SCORE3);
        mockMvc
                .perform(
                        put(PATH + "/" + WRONG_ID)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isNotFound());
    }

    @Test
    public void modifyingSymbolWithEmptyContentsFails() throws Exception {
        String content = symbolModAsString(s2.getId(), EMPTY_STRING, SCORE3);
        mockMvc
                .perform(
                        put(PATH + "/" + s2.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void modifyingSymbolWithZeroScoreFails() throws Exception {
        String content = symbolModAsString(s2.getId(), SYMBOL3, ZEROSCORE);
        mockMvc
                .perform(
                        put(PATH + "/" + s2.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void modifyingSymbolWithNegativeScoreFails() throws Exception {
        String content = symbolModAsString(s2.getId(), SYMBOL3, NEGATIVESCORE);
        mockMvc
                .perform(
                        put(PATH + "/" + s2.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void modifyingSymbolWithExistingValueFails() throws Exception {
        String content = symbolModAsString(s2.getId(), SYMBOL1, SCORE3);
        mockMvc
                .perform(
                        put(PATH + "/" + s2.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isLocked());
    }
    
    @Test
    public void modifyingOnlyScoreOK() throws Exception {
        String content = symbolModAsString(s2.getId(), SYMBOL2, SCORE3);
        MvcResult result = mockMvc
                                   .perform(
                                           put(PATH + "/" + s2.getId())
                                           .contentType(MediaType.APPLICATION_JSON_UTF8)
                                           .content(content))
                                   .andExpect(status().isOk())
                                   .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SymbolDto dto = mapper.readValue(result.getResponse().getContentAsString(), SymbolDto.class);
        assertTrue("Value changed incorrectly!", dto.getValue().equals(SYMBOL2));
        assertTrue("Score not changed correctly!", dto.getScore() == SCORE3);
    }

    @Test 
    public void addingSymbolDigramWith3LettersOK() throws Exception {
        String content = symbolAddAsString(SYMBOL_DIGRAM3, SCORE3);
        MvcResult result = mockMvc
                                   .perform(
                                           post(PATH)
                                           .contentType(MediaType.APPLICATION_JSON_UTF8)
                                           .content(content))
                                   .andExpect(status().isCreated())
                                   .andExpect(header().string("Location", containsString(PATH + "/")))
                                   .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        SymbolDto dto = mapper.readValue(result.getResponse().getContentAsString(), SymbolDto.class);
        assertTrue("Didn't return correct value", dto.getValue().equals(SYMBOL_DIGRAM3));
        assertTrue("Didn't return correct score", dto.getScore() == SCORE3);

    }
    
    @Test 
    public void addingSymbolDigramWith4LettersFail() throws Exception {
        String content = symbolAddAsString(SYMBOL_DIGRAM4, SCORE3);
        mockMvc
                .perform(
                        post(PATH)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(content))
                .andExpect(status().isBadRequest());
    }

}
