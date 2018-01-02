package base.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import base.command.TileColorAdd;
import base.service.TileColorDto;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TileColorControllerTest {

    private static final String PATH = "/api/colors";
    private static int RED1 = 100;
    private static int GREEN1 = 100;
    private static int BLUE1 = 100;
    private static double ALPHA1 = 0.5;
    private static int TOO_SMALL_RED = -1;
    private static int TOO_BIG_RED = 256;
    private static int TOO_SMALL_ALPHA = -1;
    private static int TOO_BIG_ALPHA = 256;
    
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
    


}
