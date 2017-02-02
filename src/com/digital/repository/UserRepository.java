package com.digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.digital.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findByPId(Long id);

	@Modifying
	@Transactional
	@Query("delete from User u " + "where id=:id")
	public int deleteUser(@Param("id") Long id);

	
	

}
