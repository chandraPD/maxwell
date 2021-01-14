package library.maxwell.module.topup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import library.maxwell.module.topup.entity.HistoryBalanceEntity;

public interface HistoryBalanceRepository extends JpaRepository<HistoryBalanceEntity, Integer> {
	@Query(value = "select user_balance_id from history_balance where history_balance_id=?", nativeQuery = true)
	Integer findIdBalance(Integer id);

	@Query(value = "select nominal from history_balance where history_balance_id=?", nativeQuery = true)
	Double findNominal2(Integer id);

	@Query(value = "SELECT history_balance.* ,user_entity.email FROM history_balance INNER JOIN user_balance ON history_balance.user_balance_id=user_balance.user_balance_id INNER JOIN user ON user_balance.user_id=user_entity.user_id ORDER BY history_balance_id DESC", nativeQuery = true)
	List<HistoryBalanceEntity> findAll2();

	@Query(value = "SELECT history_balance.* ,user_entity.email FROM history_balance INNER JOIN user_balance ON history_balance.user_balance_id=user_balance.user_balance_id INNER JOIN user ON user_balance.user_id=user_entity.user_id WHERE user_entity.user_id=? ORDER BY history_balance.history_balance_id DESC", nativeQuery = true)
	List<HistoryBalanceEntity> findall3(Integer id);
}
