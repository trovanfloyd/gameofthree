package com.takeaway.gameofthree.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.takeaway.gameofthree.model.Player;

@Service
public class GameService {
	
	private final RestTemplate restTemplate;
	
	@Value("${number.random.max}")
	private Integer limitRangeNumber;
	
	@Value("${uri.second.player}")
	private String uriSecondPlayer;
	
	@Value("${uri.play.game}")
	private String uriPlayGame;
	
	Logger logger = LoggerFactory.getLogger(GameService.class);
	
	@Autowired
	ConfigurableApplicationContext context;
	
	public GameService(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	public String prepareGame(boolean automaticoInput, Integer numberStart) {
		if (discoverSecondPlayer()) {
			Player player = new Player();
			if (automaticoInput) {
				player.setNumber(getRandomNumber());
			} else {
				player.setNumber(numberStart);
			}
			
			JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
			jmsTemplate.convertAndSend("start", player);
			
			logger.info("The game has been started!");
			return "The game has been started!";
		} else {
			logger.error("There is no second player active!");
			return "There is no second player active!";
		}
		
	}
	
	@JmsListener(destination = "start")
	public void startGame(Player player) {
		logger.info("Sending number: " + player.getNumber());
		restTemplate.postForObject(uriPlayGame, player, Player.class);
	}
	
	public boolean discoverSecondPlayer()  {
		logger.info("Discovering second player...");
		try {
			restTemplate.getForObject(uriSecondPlayer, String.class);
		} catch (Exception ex) {
			logger.error("There is no second player active. The game has been ended");
			return false;
		}
		logger.info("Second player discovered!");
		return true;
	}
	
	private int getRandomNumber() {
		
		Random r = new Random();
		return r.nextInt(limitRangeNumber);
		
	}

	public void play(Player player) {
		int currentNumber = player.getNumber();
		
		logger.info("Received number: " + currentNumber);

        if (currentNumber <= 0) {
        	logger.error("Error, something went wrong.");
        } else {
        	
        	if (currentNumber % 3 == 0) {
        		logger.info("Value added: 0");
        		player.setNumber(currentNumber / 3);
        	} else if ( (currentNumber + 1) % 3 == 0) {
        		logger.info("Value added: 1");
        		player.setNumber((currentNumber + 1) / 3);
        	} else {
        		logger.info("Value added: -1");
        		player.setNumber((currentNumber - 1) / 3);
        	}
        	
        	if (player.getNumber() == 1) {
        		logger.info("You are the winner!!");
        	} else {
        		
        		logger.info("Sending number: " + player.getNumber());
        		
        		restTemplate.postForObject(uriPlayGame, player, Player.class);
        	}
        	
        }

	}

}
