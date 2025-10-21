package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Team;
import com.example.demo.repository.TeamRepository;

@Service
public class TeamService 
{
	@Autowired
	private TeamRepository teamRepository;

	public List<Team> findAll() 
	{
		return teamRepository.findAll()
				.stream()
				.map(team -> new Team(
						team.getId(),
						team.getName(),
						team.getCountry(),
						team.getLeague(),
						team.getImage())
						)
				.collect(Collectors.toList());	
	}

	public Team save(Team team) 
	{
		// TODO Auto-generated method stub
		return teamRepository.save(team);
	}

	public boolean deleteById(Integer id)
	{
		if (teamRepository.existsById(id))
		{
			teamRepository.deleteById(id);
			return true;
		}
		else
		{
			return false;
		}
	}

	public Team findById(Integer id) 
	{
		return teamRepository.findById(id).get();
	}

	public Team update(Team team) 
	{
		Team existTeam = teamRepository.findById(team.getId()).get();
		existTeam.setName(team.getName());
		existTeam.setCountry(team.getCountry());
		existTeam.setLeague(team.getLeague());
		
		return teamRepository.save(existTeam);	
	
	}


	public List<Team> searchName(String keyword) 
	{
		return teamRepository.findByNameContainingIgnoreCase(keyword);
	
	}

	

}
