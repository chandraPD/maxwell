package library.maxwell.module.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import library.maxwell.module.topup.entity.HistoryBalanceEntity;


public interface HistoryBalanceRepository extends JpaRepository<HistoryBalanceEntity, Integer> {
	@Query(value="select user_balance_id from history_balance where history_balance_id=?",nativeQuery = true)
	Integer findIdBalance(Integer id);

	@Query(value="select nominal from history_balance where history_balance_id=?",nativeQuery = true)
	Double findNominal2(Integer id);
}
