package library.maxwell.module.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.maxwell.module.user.entity.UserDetailEntity;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Integer>{
	
//	@Query(value = "select * from user_detail where user_id = ?",  )
//	UserDetailEntity getByUserId(Integer userId);
//	
	UserDetailEntity findByUserEntityUserId(Integer userId);
}
