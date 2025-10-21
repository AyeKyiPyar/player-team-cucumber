package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;

@Service
public class PlayerService 
{

	@Autowired
	private PlayerRepository playerRepository;
	
	public List<Player> findAll() 
	{
		return playerRepository.findAll()
				.stream()
				.map(player -> new Player(
					player.getId(),
					player.getName(),
					player.getNumber(),
					player.getAge(),
					player.getPosition(),
					player.getNationality(),
					player.getHeight(),
					player.getWeight(),
					player.getTeam(),
					player.getImage()))
				.collect(Collectors.toList());
		
	}
	public Player save(Player player) 
	{
		return playerRepository.save(player);
	}
	public boolean deleteById(Integer id) 
	{
		if (playerRepository.existsById(id))
		{
			playerRepository.deleteById(id);
			return true;
		}
		else
		{
			return false;
		}
	}
	public Player findById(Integer id) 
	{
		 return playerRepository.findById(id).get();
	}
	public Player update(Player player) 
	{
		Player existPlayer = playerRepository.findById(player.getId()).get();
		
		existPlayer.setName(player.getName());
		existPlayer.setAge(player.getAge());
		existPlayer.setNationality(player.getNationality());
		existPlayer.setHeight(player.getHeight());
		existPlayer.setNumber(player.getNumber());
		existPlayer.setPosition(player.getPosition());
		existPlayer.setWeight(player.getWeight());
		existPlayer.setTeam(player.getTeam());
		existPlayer.setImage(player.getImage());
		
		return playerRepository.save(existPlayer);
		
	}
	public List<Player> searchPlayers(String keyword)
	{
		
		
			return playerRepository.findByNameContainingIgnoreCase(keyword);
		
	}

}
