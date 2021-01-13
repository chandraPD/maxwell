package library.maxwell.module.topup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.topup.dto.UserBalanceDto;
import library.maxwell.module.topup.entity.UserBalanceEntity;
import library.maxwell.module.topup.repository.UserBalanceRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class UserBalanceImp implements UserBalanceService{
	@Autowired
	UserBalanceRepository repo;
	
	@Autowired
	UserRepository repo2;

	public UserBalanceEntity convertToUserBalanceEntity(UserBalanceDto Dto) {
		UserBalanceEntity userBalanceEntity=new UserBalanceEntity();
		userBalanceEntity.setNominal(Dto.getNominal());		
		return userBalanceEntity;
	}
	
	@Override
	public UserBalanceEntity getById(Integer id) {
		UserBalanceEntity userBalanceEntity=repo.findById(id).get();
		return userBalanceEntity;
	}

	@Override
	public List<UserBalanceEntity> getAll() {
		List<UserBalanceEntity> userBalanceEntities=repo.findAll();
		return userBalanceEntities;
	}

	@Override
	public UserBalanceEntity post(UserBalanceDto Dto) {	
		UserBalanceEntity userBalanceEntity=new UserBalanceEntity();
		UserEntity userEntity=repo2.findById(Dto.getUser_id()).get();
		userBalanceEntity.setStatus(true);		
		userBalanceEntity.setNominal((double) 0);
		userBalanceEntity.setUserEntity(userEntity);
		repo.save(userBalanceEntity);
		return userBalanceEntity;
	}

	@Override
	public UserBalanceEntity update(UserBalanceDto Dto, Integer id) {
		Double nominal=repo.findNominal(id);
		UserBalanceEntity userBalanceEntity= repo.findById(id).get();
		userBalanceEntity.setNominal(Dto.getNominal()+nominal);
		repo.save(userBalanceEntity);
		return userBalanceEntity;
	}

	@Override
	public UserBalanceEntity delete(Integer id) {
		UserBalanceEntity userBalanceEntity= repo.findById(id).get();
		userBalanceEntity.setStatus(false);
		repo.save(userBalanceEntity);
		return userBalanceEntity;
	}

	@Override
	public Double getSaldo(UserPrincipal userPrincipal) {
		// TODO Auto-generated method stub
		Integer userId = userPrincipal.getId();
		Double balance = repo.findByUserEntity_UserIdIs(userId).getNominal();
		return balance;
	}

}
