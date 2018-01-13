package base.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import base.command.TileColorAdd;
import base.command.TileColorMod;
import base.domain.TileColor;
import base.dto.TileColorDto;
import base.repository.TileColorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("development")
public class TileColorControllerTest {

    private static final String PATH = "/api/colors";
    private static int RED1 = 100;
    private static int RED2 = 102;
    private static int RED3 = 103;
    private static int GREEN1 = 100;
    private static int GREEN2 = 102;
    private static int GREEN3 = 103;
    private static int BLUE1 = 100;
    private static int BLUE2 = 102;
    private static int BLUE3 = 103;
    private static double ALPHA1 = 0.5;
    private static double ALPHA2 = 1;
    private static int TOO_SMALL_RED = -1;
    private static int TOO_BIG_RED = 256;
    private static int TOO_SMALL_GREEN = -1;
    private static int TOO_BIG_GREEN = 256;
    private static int TOO_SMALL_ALPHA = -1;
    private static int TOO_BIG_ALPHA = 256;
    private static long WRONG_ID = Long.MAX_VALUE;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TileColorRepository tileColorRepository;
    
    private MockMvc mockMvc;
    private TileColor tc1, tc2;
    private long tc3Id = -1;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        tc1 = new TileColor(RED2, GREEN2, BLUE2, ALPHA1);
        tc1 = tileColorRepository.saveAndFlush(tc1);
        tc2 = new TileColor(RED3, GREEN3, BLUE3, ALPHA1);
        tc2 = tileColorRepository.saveAndFlush(tc2);
    }

    @After
    public void tearDown() throws Exception {
        tileColorRepository.delete(tc1);
        tileColorRepository.delete(tc2);
        if (-1 != tc3Id) {
            tileColorRepository.delete(tc3Id);
        }
    }

    @Test
    public void addingColorReturnsLocationHeaderAndDto() throws Exception {
        TileColorAdd newColor = new TileColorAdd(RED1, GREEN1, BLUE1, ALPHA1);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newColor);
        MvcResult result = mockMvc
                                .perform(
                                        post(PATH)
                                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                                        .content(content))
                                .andExpect(status().isCreated())
                                .andExpect(header().string("Location", containsString(PATH + "/")))
                                .andReturn();
        TileColorDto dto = mapper.readValue(result.getResponse().getContentAsString(), TileColorDto.class);
        assertTrue("Red not correct", dto.getRed() == RED1);
        assertTrue("Green not correct", dto.getGreen() == GREEN1);
        assertTrue("Blue not correct", dto.getBlue() == BLUE1);
        assertTrue("Alpha not correct", dto.getAlpha() == ALPHA1);
        tc3Id = dto.getId();
    }

    @Test
    public void addingColorWithTooSmallRedValue() throws Exception {
        TileColorAdd newColor = new TileColorAdd(TOO_SMALL_RED, GREEN1, BLUE1, ALPHA1);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newColor);
        mockMvc
            .perform(
                    post(PATH)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void addingColorWithTooBigRedValue() throws Exception {
        TileColorAdd newColor = new TileColorAdd(TOO_BIG_RED, GREEN1, BLUE1, ALPHA1);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newColor);
        mockMvc
            .perform(
                    post(PATH)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void addingColorWithTooSmallAlphaValue() throws Exception {
        TileColorAdd newColor = new TileColorAdd(RED1, GREEN1, BLUE1, TOO_SMALL_ALPHA);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newColor);
        mockMvc
            .perform(
                    post(PATH)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void addingColorWithTooBigAlphaValue() throws Exception {
        TileColorAdd newColor = new TileColorAdd(RED1, GREEN1, BLUE1, TOO_BIG_ALPHA);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(newColor);
        mockMvc
            .perform(
                    post(PATH)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void deleteColorOK() throws Exception {
        mockMvc.perform(
                delete(PATH + "/" + tc2.getId()))
                .andExpect(status().isOk());
    }
    
    @Test
    public void deleteColorWithWrongIdFails() throws Exception {
        mockMvc.perform(
                delete(PATH + "/" + WRONG_ID))
                .andExpect(status().isNotFound());
    }

    
    @Test
    public void modifyColorOKandReturnsChangedContent() throws Exception {
        TileColorMod color = new TileColorMod(tc2.getId(), RED1, GREEN1, BLUE1, ALPHA2);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(color);
        MvcResult result = mockMvc
                .perform(
                        put(PATH + "/" + tc2.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isOk())
                 .andReturn();
        TileColorDto dto = mapper.readValue(result.getResponse().getContentAsString(), TileColorDto.class);
        assertTrue("Red not correct", dto.getRed() == RED1);
        assertTrue("Green not correct", dto.getGreen() == GREEN1);
        assertTrue("Blue not correct", dto.getBlue() == BLUE1);
        assertTrue("Alpha not correct", dto.getAlpha() == ALPHA2);
    }

    @Test
    public void modifyColorWithTooSmallGreenValue() throws Exception {
        TileColorMod color = new TileColorMod(tc2.getId(), RED1, TOO_SMALL_GREEN, BLUE1, ALPHA2);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(color);
        mockMvc
            .perform(
                    put(PATH + "/" + tc2.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isBadRequest());
    }
        
    @Test
    public void modifyColorWithTooBigGreenValue() throws Exception {
        TileColorMod color = new TileColorMod(tc2.getId(), RED1, TOO_BIG_GREEN, BLUE1, ALPHA2);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(color);
        mockMvc
            .perform(
                    put(PATH + "/" + tc2.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void modifyColorWithTooSmallAlphaValue() throws Exception {
        TileColorMod color = new TileColorMod(tc2.getId(), RED1, GREEN1, BLUE1, TOO_SMALL_ALPHA);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(color);
        mockMvc
            .perform(
                    put(PATH + "/" + tc2.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void modifyColorWithTooBigAlphaValue() throws Exception {
        TileColorMod color = new TileColorMod(tc2.getId(), RED1, GREEN1, BLUE1, TOO_BIG_ALPHA);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(color);
        mockMvc
            .perform(
                    put(PATH + "/" + tc2.getId())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void modifyColorWithWrongId() throws Exception {
        TileColorMod color = new TileColorMod(WRONG_ID, RED1, GREEN1, BLUE1, ALPHA1);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(color);
        mockMvc
            .perform(
                    put(PATH + "/" + WRONG_ID)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(content))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void getSingleColorOk() throws Exception {
        MvcResult result = mockMvc
                                .perform(
                                        get(PATH + "/" + tc1.getId())
                                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(status().isOk())
                                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        TileColorDto dto = mapper.readValue(result.getResponse().getContentAsString(), TileColorDto.class);
        assertTrue("Red not correct", dto.getRed() == RED2);
        assertTrue("Green not correct", dto.getGreen() == GREEN2);
        assertTrue("Blue not correct", dto.getBlue() == BLUE2);
        assertTrue("Alpha not correct", dto.getAlpha() == ALPHA1);
    }
    
    @Test
    public void getSingleColorWithWrongId() throws Exception {
        mockMvc
            .perform(
                    get(PATH + "/" + WRONG_ID)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());
    }
    
    @Test
    public void listAllColorsHasColors1and2() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get(PATH)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        List<TileColorDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<TileColorDto>>() { });
        assertEquals(2, dtos.stream()
                            .filter(t -> t.getId() == tc1.getId() || t.getId() == tc2.getId())
                            .count());
    }
}
