package library.maxwell.module.topup.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.topup.dto.HistoryBalanceDto;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.topup.entity.UserBalanceEntity;
import library.maxwell.module.topup.repository.HistoryBalanceRepository;
import library.maxwell.module.topup.repository.UserBalanceRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class HistoryBalanceImp implements HistoryBalanceService{
	@Autowired
	HistoryBalanceRepository repo;
	
	@Autowired
	UserBalanceRepository repo2;
	
	@Autowired
	UserRepository repo3;

	public HistoryBalanceEntity convertToHistoryBalanceEntity (HistoryBalanceDto dto) {
		HistoryBalanceEntity historyBalanceEntity=new HistoryBalanceEntity();
		historyBalanceEntity.setNominal(dto.getNominal());
		historyBalanceEntity.setPaymentMethod(dto.getPaymentMethod());
		return historyBalanceEntity;
	}
	
	@Override
	public HistoryBalanceEntity getById(Integer id) {
		HistoryBalanceEntity historyBalanceEntity = repo.findById(id).get();
		return historyBalanceEntity;
	}

	@Override
	public List<HistoryBalanceEntity> getAll() {		
		List<HistoryBalanceEntity> historyBalanceEntities= repo.findAll2();
		return historyBalanceEntities;
	}
	
	@Override
	public HistoryBalanceEntity post(UserPrincipal userPrincipal,HistoryBalanceDto Dto) {	
		Integer id=userPrincipal.getId();
		LocalDateTime now = LocalDateTime.now();
		HistoryBalanceEntity historyBalanceEntity=convertToHistoryBalanceEntity(Dto);
		UserBalanceEntity userBalanceEntity=repo2.findByUserBalanceId(id);		
		historyBalanceEntity.setUserBalanceEntity(userBalanceEntity);
		historyBalanceEntity.setStatus(true);
		historyBalanceEntity.setStatusPayment("Pending");		
		historyBalanceEntity.setDateTopup(now);
		repo.save(historyBalanceEntity);
		return historyBalanceEntity;
	}

	@Override
	public HistoryBalanceEntity update(HistoryBalanceDto Dto, Integer id) {
		HistoryBalanceEntity historyBalanceEntity=repo.findById(id).get();
		historyBalanceEntity.setNominal(Dto.getNominal());
		historyBalanceEntity.setPaymentMethod(Dto.getPaymentMethod());
		return historyBalanceEntity;
	}

	@Override
	public HistoryBalanceEntity delete(Integer id) {
		HistoryBalanceEntity historyBalanceEntity=repo.findById(id).get();
		historyBalanceEntity.setStatus(false);
		repo.save(historyBalanceEntity);
		return historyBalanceEntity;
	}

	@Override
	public HistoryBalanceEntity accept(UserPrincipal userPrincipal,HistoryBalanceDto Dto,Integer id) {
		Integer id3=userPrincipal.getId();
		Integer idbalance=repo.findIdBalance(id);
		Double nominal=repo2.findNominal(idbalance);
		Double nominal2=repo.findNominal2(id);
		LocalDateTime now = LocalDateTime.now();
		HistoryBalanceEntity historyBalanceEntity=repo.findById(id).get();
		UserBalanceEntity userBalanceEntity= repo2.findById(idbalance).get();
		UserEntity entity=repo3.findById(id3).get();
		userBalanceEntity.setNominal(nominal+nominal2);
		repo2.save(userBalanceEntity);
		historyBalanceEntity.setUserEntity(entity);
		historyBalanceEntity.setStatusPayment("Success");
		historyBalanceEntity.setDateAcc(now);
		repo.save(historyBalanceEntity);
		return historyBalanceEntity;
	}

	@Override
	public HistoryBalanceEntity cancel(UserPrincipal userPrincipal,HistoryBalanceDto Dto,Integer id) {
		Integer id3=userPrincipal.getId();
		LocalDateTime now = LocalDateTime.now();
		HistoryBalanceEntity historyBalanceEntity=repo.findById(id).get();
		UserEntity entity=repo3.findById(id3).get();
		historyBalanceEntity.setUserEntity(entity);
		historyBalanceEntity.setStatusPayment("Cancelled");
		historyBalanceEntity.setDateAcc(now);
		repo.save(historyBalanceEntity);
		return historyBalanceEntity;
	}

	@Override
	public HistoryBalanceEntity post2(HistoryBalanceDto Dto) {		
		LocalDateTime now = LocalDateTime.now();
		HistoryBalanceEntity historyBalanceEntity=convertToHistoryBalanceEntity(Dto);
		UserBalanceEntity userBalanceEntity=repo2.findByUserBalanceId(Dto.getUser_balance_id());		
		historyBalanceEntity.setUserBalanceEntity(userBalanceEntity);
		historyBalanceEntity.setStatus(true);
		historyBalanceEntity.setStatusPayment("Pending");		
		historyBalanceEntity.setDateTopup(now);
		repo.save(historyBalanceEntity);
		return historyBalanceEntity;
	}

	@Override	
	public List<HistoryBalanceEntity> getAll2(Integer userPrincipal) {
		List<HistoryBalanceEntity> historyBalanceEntities= repo.findall3(userPrincipal);
		return historyBalanceEntities;
	}

}
