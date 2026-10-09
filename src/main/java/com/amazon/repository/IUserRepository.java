package com.amazon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amazon.entity.User;
import com.amazon.enumeration.Role;


public interface IUserRepository extends JpaRepository<User, Long> {
	@Query("from user u where u.email=:email")
	public User existByEmail(@Param("email")String email);
	
	public Optional<User> findByUserName(String userName);
	
	public List<User> findByRole(Role role);
}
