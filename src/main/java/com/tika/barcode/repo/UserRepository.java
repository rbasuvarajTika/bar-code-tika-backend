package com.tika.barcode.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.tika.barcode.entity.User;

/**
 * Repository interface for managing {@link UserRepository}.request
 * 
 * @author Raghu
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String email);

	Optional<User> findByUserId(int id);

	User findByUserMail(String email);

	boolean existsByUserName(String userName);

	boolean existsByUserMail(String email);

	@Query(nativeQuery = true, value = "SELECT * from DIM_USER ORDER BY USER_ID DESC")
	Page<User> findAllCustom(PageRequest page);
	
	@Query(nativeQuery = true, value = "SELECT * from DIM_USER WHERE USER_ID = :userId ORDER BY USER_ID DESC")
	List<User> findAllCustomByUserId(@PathVariable(value = "userId") Integer userId);
	
	@Query(nativeQuery = true, value = "SELECT * from DIM_USER WHERE USER_NAME = :userName ORDER BY USER_ID DESC")
	List<User> findAllUsersByUserName(@PathVariable(value = "userName") String userName);
	
	@Query(nativeQuery = true, value = "SELECT Distinct USER_STATUS_FLAG from DIM_USER where USER_STATUS_FLAG is not null")
	List<String>findUserStatus();
	
	
}
