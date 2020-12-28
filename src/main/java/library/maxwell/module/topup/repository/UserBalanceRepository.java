package library.maxwell.module.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.topup.entity.UserBalanceEntity;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalanceEntity, Integer>{
	@Query(value="select nominal where user_id=?",nativeQuery = true)
	Double findNominal(Integer id);
}
