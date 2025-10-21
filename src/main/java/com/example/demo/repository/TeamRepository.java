package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>
{

	List<Team> findByNameContainingIgnoreCase(String name);

}
