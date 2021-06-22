package com.springtutorial.springsecurityjpa.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springtutorial.springsecurityjpa.models.InvalidJwt;
@Repository("invalidJwtRepository")
public interface InvalidJwtRepository extends JpaRepository<InvalidJwt, Integer>{
//	@Query("SELECT n FROM invalidJwt n WHERE n.jwt=:jwt")
//	List<InvalidJwt> getInvalidTokensByJwt(@Param("jwt") String jwt);
	
//	@Query(value = "SELECT * FROM invalid_jwt n WHERE n.jwt=:jwt", nativeQuery=true)
//	List<InvalidJwt> getInvalidTokensByJwt(@Param("jwt") String jwt);

}
