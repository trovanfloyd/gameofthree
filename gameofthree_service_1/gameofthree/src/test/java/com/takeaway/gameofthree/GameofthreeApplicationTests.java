package com.takeaway.gameofthree;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.takeaway.gameofthree.endpoint.GameEndpoint;
import com.takeaway.gameofthree.service.GameService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameofthreeApplicationTests {
	
	private MockMvc mockMvc;
	
	@Autowired
	private GameEndpoint gameEndpoint;
	
	@Autowired
	private GameService gameService;
	
	
	
	@Before
    public void setUp() throws Exception {
      mockMvc = MockMvcBuilders.standaloneSetup(gameEndpoint)
                .build();
        
	}
	

	@Test
	public void testtStartGameUp() {
		 if (gameService.discoverSecondPlayer()) {
			 try {
					mockMvc.perform(MockMvcRequestBuilders.get("/start/true"))
					        .andExpect(MockMvcResultMatchers.content().string("The game has been started!"))
					        .andExpect(MockMvcResultMatchers.status().isOk());
				} catch (Exception e) {
					e.printStackTrace();
				}
		 }
	}
	
	@Test
	public void testStartGameDown() {
		if (!gameService.discoverSecondPlayer()) {
			 try {
					mockMvc.perform(MockMvcRequestBuilders.get("/start/true"))
					        .andExpect(MockMvcResultMatchers.content().string("There is no second player active!")) 
					        .andExpect(MockMvcResultMatchers.status().isOk());
				} catch (Exception e) {
					e.printStackTrace();
				}
		 }
	}
	
	@Test
	public void testPlayGameWithInvalidPlayer() {
		
		 try {
			 //Some invalid Json
			 String json = "{\n" +
		                "  \"title\": \"Greetings\",\n" +
		                "  \"value\": \"Hello World\"\n" +
		                "}";
				mockMvc.perform(MockMvcRequestBuilders.post("/play").contentType(MediaType.APPLICATION_JSON).content(json))
				        .andExpect(MockMvcResultMatchers.status().isBadRequest());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testPlayGameWithSecondPlayerDown() {
		
		 try {
			 if (!gameService.discoverSecondPlayer()) {
				 
				 String json = "{\n" +
						 "  \"id\": 1,\n" +
						 "  \"number\": 9\n" +
						 "}";
				 mockMvc.perform(MockMvcRequestBuilders.post("/play").contentType(MediaType.APPLICATION_JSON).content(json))
				 .andExpect(MockMvcResultMatchers.status().isNotFound());
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}
	
	@Test
	public void testPlayGameWinnerPlayer() {
		
		 try {
			 String json = "{\n" +
		                "  \"id\": 1,\n" +
		                "  \"number\": 3\n" +
		                "}";
				mockMvc.perform(MockMvcRequestBuilders.post("/play").contentType(MediaType.APPLICATION_JSON).content(json))
				        .andExpect(MockMvcResultMatchers.status().isOk());
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Test
	public void testHealth() {
		 try {
				mockMvc.perform(MockMvcRequestBuilders.get("/health"))
				        .andExpect(MockMvcResultMatchers.status().isOk()) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}

