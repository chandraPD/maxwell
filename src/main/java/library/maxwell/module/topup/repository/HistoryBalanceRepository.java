package library.maxwell.module.topup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import library.maxwell.module.topup.entity.HistoryBalanceEntity;


public interface HistoryBalanceRepository extends JpaRepository<HistoryBalanceEntity, Integer> {
	

}
