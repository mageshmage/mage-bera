package com.cargotracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cargotracker.domain.User;
import com.cargotracker.domain.UserExtra;
import com.cargotracker.domain.Vendor;

/**
 * Spring Data  repository for the Vendor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long>  {
	
	
	public UserExtra findAllByUser(@Param("user") User user);
	
	Optional<UserExtra> findOneByUser(User user);
	
	@Query(value="select ue.id from user_extra as ue where ue.user_id = :user_id", nativeQuery= true)
	public Long findExtraUserByUserId( @Param("user_id") Long userId);

}
