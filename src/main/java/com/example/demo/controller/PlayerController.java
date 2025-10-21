package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.service.PlayerService;
import com.example.demo.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/player")
public class PlayerController 
{
	@Autowired
	private PlayerService playerService;
	@Autowired
	private TeamService teamService;
	
	@GetMapping("/")
	public List<Player> playerHome()
	{
		return playerService.findAll();
		
	}
	
	@GetMapping("/add")
	public List<Team> getTeamsAndPlayer() 
	{
		return teamService.findAll();
       
    }

	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/Upload";//"/uploads";
	
	
	
	
	@Operation(summary = "Add player with image",
	           description = "Upload a player object and a file")
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public Player addPlayerProcess(
//	        @RequestPart(name = "player") String playerJson,
//	        @RequestPart(name = "file") MultipartFile file) throws IOException 
	public Player addPlayerProcess(@RequestPart("player") Player player, @RequestPart("file") MultipartFile file) throws IOException
	{
		
//		ObjectMapper mapper = new ObjectMapper();
//	    Player player = mapper.readValue(playerJson, Player.class);
//	
		if (player == null) 
		{
	        throw new IllegalArgumentException("Player object is null!");
	    }

	    if (file == null || file.isEmpty()) 
	    {
	        throw new IllegalArgumentException("File is missing!");
	    }
	    
		String getFileName = file.getOriginalFilename();
		
		Path filePath = Paths.get(uploadDirectory, getFileName);
		Files.write(filePath, file.getBytes());
		player.setImage(getFileName);

		return playerService.save(player);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deletePlayer(@PathVariable Integer id)
	{
		return playerService.deleteById(id);
	
	}
	
	@GetMapping("/update")
	public List<Team> updatePlayer()
	{
		
		return teamService.findAll();
	}
	
	@PostMapping("/update")
	public Player updatePlayerProcess(@RequestPart("player") Player player, @RequestPart("file") MultipartFile file) throws IOException
	{
		String getFileName = file.getOriginalFilename();
		
		Path filePath = Paths.get(uploadDirectory, getFileName);
		Files.write(filePath, file.getBytes());
		player.setImage(getFileName);
		return playerService.update(player);
		
	}
	
	@Operation(summary = "(search by string)")
	@PostMapping("/search")
	public List<Player> searchPlayer(@RequestParam String keyword)
	{
		
		 return playerService.searchPlayers(keyword);
		
	}
	
}
