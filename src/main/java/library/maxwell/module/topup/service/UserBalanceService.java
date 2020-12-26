package library.maxwell.module.topup.service;

import java.util.List;

import library.maxwell.module.topup.dto.UserBalanceDto;
import library.maxwell.module.topup.entity.UserBalanceEntity;

public interface UserBalanceService {
	UserBalanceEntity getById(Integer id);
	
	List<UserBalanceEntity> getAll();
	
	UserBalanceEntity post(UserBalanceDto Dto);
	
	UserBalanceEntity update(UserBalanceDto Dto,Integer id);
	
	UserBalanceEntity delete(Integer id);
}
