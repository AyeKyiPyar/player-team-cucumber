package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Team;
import com.example.demo.service.TeamService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/team")
public class TeamController 
{
	@Autowired
	private TeamService teamService;
	
	
	@GetMapping("/")
	public List<Team> teamHome()
	{
		
		return teamService.findAll();
		
	}
	
	
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/Upload";
	@PostMapping("/add")
	public Team addTeam(@RequestBody Team team)
	{
		return teamService.save(team);
		
	}
	
	@GetMapping("/delete/{id}")
	public boolean deleteTeam(@PathVariable Integer id)
	{
		return teamService.deleteById(id);
	
	}
	
	@GetMapping("/update/{id}")
	public Team updateTeam(@PathVariable Integer id)
	{
		return teamService.findById(id);
	
	}
	
	@PostMapping("/update")
	public Team updateTeamProcess(@RequestBody Team team)
	{
		return teamService.update(team);
		
	}
	
	@GetMapping("/search")
	public List<Team> searchName(@RequestParam String keyword)
	{
		return teamService.searchName(keyword);
		
	}
}

