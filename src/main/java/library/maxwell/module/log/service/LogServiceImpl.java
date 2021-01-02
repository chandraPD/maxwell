package library.maxwell.module.log.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.log.dto.LogDto;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;

@Service
@Transactional
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;
	
	@Override
	public List<LogEntity> getAllLog() {
		// TODO Auto-generated method stub
		List<LogEntity> logEntities = logRepository.findAll();
		return logEntities;
	}

	@Override
	public LogEntity getLogById(Integer idLog) {
		// TODO Auto-generated method stub
		LogEntity logEntity = logRepository.findById(idLog).get();
		return logEntity;
	}

	@Override
	public LogEntity addLog(LogDto dto) {
		// TODO Auto-generated method stub
		LogEntity logEntity = convertToLogEntity(dto);
		logRepository.save(logEntity);
		return logEntity;
	}


	@Override
	public LogEntity updateLog(Integer idLog, LogDto dto) {
		// TODO Auto-generated method stub
		LogEntity logEntity = logRepository.findById(idLog).get();
		LogEntity updateLog = convertToLogEntity(dto);
		logRepository.save(updateLog);
		return logEntity;
	}

	@Override
	public LogEntity deleteLog(Integer idLog) {
		// TODO Auto-generated method stub
		LogEntity logEntity = logRepository.findById(idLog).get();
		logEntity.setStatus(false);
		logRepository.save(logEntity);
		return logEntity;
	}
	
	
	public LogEntity convertToLogEntity(LogDto dto) {
		LogEntity logEntity = new LogEntity();
		logEntity.setDateTime(LocalDateTime.now());
		logEntity.setAction(dto.getAction());
		logEntity.setDescription(dto.getDescription());
		return logEntity;

}


}
