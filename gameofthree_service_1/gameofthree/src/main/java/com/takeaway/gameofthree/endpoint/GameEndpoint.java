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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = {"gameofthree"})
public class GameEndpoint {
	
	@Autowired
	GameService gameService;

	@GetMapping("/start/{automaticoInput}")
	@ApiOperation("Start the game")
	public String startGame(@ApiParam("true (automatic) or false (manual)") @PathVariable boolean automaticoInput,
			 @ApiParam("Number to start in case of manual type") @RequestParam(value = "number") Optional<Integer> numberStart) {
		return gameService.prepareGame(automaticoInput, numberStart.orElse(null));
	}
	
	@PostMapping("/play")
	@ApiOperation("Play the game")
	public void play(@RequestBody Player player) {
		gameService.play(player);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("Health check")
	@GetMapping("/health")
	public ResponseEntity<String> health() {
		return new ResponseEntity(HttpStatus.OK);
	}
}
