package library.maxwell.module.topup.service;

import java.util.List;

import library.maxwell.module.topup.dto.HistoryBalanceDto;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;

public interface HistoryBalanceService {
	HistoryBalanceEntity getById(Integer id);
	
	List<HistoryBalanceEntity> getAll();
	
	HistoryBalanceEntity post(HistoryBalanceDto Dto);
	
	HistoryBalanceEntity update(HistoryBalanceDto Dto,Integer id);
	
	HistoryBalanceEntity delete(Integer id);
}
