package library.maxwell.module.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.user.entity.UserDetailEntity;
import library.maxwell.module.user.entity.UserEntity;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Integer>{
	
//	@Query(value = "select * from user_detail where user_id = ?",  )
//	UserDetailEntity getByUserId(Integer userId);
//	
	UserDetailEntity findByUserEntityUserId(Integer userId);
	
	@Query(value="Select first_name from user_detail where user_id=?",nativeQuery = true)
	String findFirst(Integer id);
	
	@Query(value="Select last_name from user_detail where user_id=?",nativeQuery = true)
	String findLast(Integer id);
}
