package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity

public class Player 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String number;
	private Integer age;
	private String position;
	private String nationality;
	private Integer height;
	private Integer weight;
	private String image;
	
	@ManyToOne
	private Team team;
	
	

	public Player(Integer id, String name, String number, Integer age, String position, String nationality,
			Integer height, Integer weight, Team team, String image) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.age = age;
		this.position = position;
		this.nationality = nationality;
		this.height = height;
		this.weight = weight;
		this.team = team;
		this.image=image;
	}

	public Player()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNumber() 
	{
		return number;
	}

	public void setNumber(String number) 
	{
		this.number = number;
	}

	public Integer getAge() 
	{
		return age;
	}

	public void setAge(Integer age) 
	{
		this.age = age;
	}

	public String getPosition() 
	{
		return position;
	}

	public void setPosition(String position) 
	{
		this.position = position;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality) 
	{
		this.nationality = nationality;
	}

	public Integer getHeight() 
	{
		return height;
	}

	public void setHeight(Integer height) 
	{
		this.height = height;
	}

	public Integer getWeight() 
	{
		return weight;
	}

	public void setWeight(Integer weight) 
	{
		this.weight = weight;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	

	
	
	
	
	
	
	
	
	
	
	
}
