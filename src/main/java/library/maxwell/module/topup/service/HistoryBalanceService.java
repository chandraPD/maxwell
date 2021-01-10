package library.maxwell.module.topup.service;

import java.util.List;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.topup.dto.HistoryBalanceDto;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.user.entity.UserEntity;

public interface HistoryBalanceService {
	HistoryBalanceEntity getById(Integer id);
	
	List<HistoryBalanceEntity> getAll(UserPrincipal usPrincipal);
	
	List<HistoryBalanceEntity> getAll2(Integer id);
	
	HistoryBalanceEntity post(UserPrincipal userPrincipal,HistoryBalanceDto Dto);
	
	HistoryBalanceEntity post2(UserPrincipal userPrincipal,HistoryBalanceDto Dto);
	
	HistoryBalanceEntity accept(UserPrincipal userPrincipal,HistoryBalanceDto Dto,Integer id);
	
	HistoryBalanceEntity cancel(UserPrincipal userPrincipal,HistoryBalanceDto Dto,Integer id);
	
	HistoryBalanceEntity update(HistoryBalanceDto Dto,Integer id);
	
	HistoryBalanceEntity delete(Integer id);
	
	Boolean getPass(UserPrincipal userPrincipal,HistoryBalanceDto dto);
		


}
