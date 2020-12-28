package library.maxwell.module.topup.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.topup.dto.HistoryBalanceDto;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.topup.repository.HistoryBalanceRepository;
import library.maxwell.module.topup.repository.UserBalanceRepository;

@Service
@Transactional
public class HistoryBalanceImp implements HistoryBalanceService{
	@Autowired
	HistoryBalanceRepository repo;
	
	@Autowired
	UserBalanceRepository repo2;

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
		List<HistoryBalanceEntity> historyBalanceEntities= repo.findAll();
		return historyBalanceEntities;
	}

	@Override
	public HistoryBalanceEntity post(HistoryBalanceDto Dto) {
		LocalDateTime now = LocalDateTime.now();
		HistoryBalanceEntity historyBalanceEntity=convertToHistoryBalanceEntity(Dto);
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
	public HistoryBalanceEntity accept(HistoryBalanceDto Dto,Integer id) {
		LocalDateTime now = LocalDateTime.now();
		HistoryBalanceEntity historyBalanceEntity=repo.findById(id).get();		
		historyBalanceEntity.setStatusPayment("Success");
		historyBalanceEntity.setDateAcc(now);
		repo.save(historyBalanceEntity);
		return historyBalanceEntity;
	}

	@Override
	public HistoryBalanceEntity cancel(HistoryBalanceDto Dto,Integer id) {
		LocalDateTime now = LocalDateTime.now();
		HistoryBalanceEntity historyBalanceEntity=repo.findById(id).get();		
		historyBalanceEntity.setStatusPayment("Cancelled");
		historyBalanceEntity.setDateAcc(now);
		repo.save(historyBalanceEntity);
		return historyBalanceEntity;
	}

}
