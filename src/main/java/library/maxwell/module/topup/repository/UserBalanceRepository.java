package library.maxwell.module.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.topup.entity.UserBalanceEntity;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalanceEntity, Integer>{
	@Query(value="select nominal from user_balance where user_id=?",nativeQuery = true)
	Double findNominal(Integer id);
	
	UserBalanceEntity findByUserEntity_UserIdIs(Integer userId);
	
	UserBalanceEntity findByUserBalanceId(Integer id);

	UserBalanceEntity findByUserBalanceId(UserPrincipal userPrincipal);

	UserBalanceEntity findByUserEntityUserId(Integer id);
}
