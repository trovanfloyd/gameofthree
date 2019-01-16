package com.takeaway.gameofthree.endpoint;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.gameofthree.model.Player;
import com.takeaway.gameofthree.service.GameService;

@RestController
public class GameEndpoint {
	
	@Autowired
	GameService gameService;

	//start//optin generate number manual ou auto ? auto or ? manual&&number=10
	@GetMapping("/start/{automaticoInput}")
	public String startGame(@PathVariable boolean automaticoInput,
							@RequestParam(value = "number") Optional<Integer> numberStart) {
		return gameService.prepareGame(automaticoInput, numberStart.orElse(null));
	}
	
	//play
	@PostMapping("/play")
	public void play(@RequestBody Player player) {
		gameService.play(player);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/health")
	public ResponseEntity<String> health() {
		return new ResponseEntity(HttpStatus.OK);
	}
}
