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

import base.domain.Symbol;
import base.repository.SymbolRepository;
import base.service.SymbolDto;
import base.service.WordDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SymbolControllerTest {

    private static final String PATH = "/api/symbols";
    private static final String SYMBOL1 = "nutrvdtfng";
    private static final String SYMBOL2 = "glp8kuirey";
    private static final int SCORE1 = 1;
    private static final int SCORE2 = 2;

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

}
