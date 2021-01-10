package library.maxwell.module.log.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.log.dto.LogDto;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;
	@Autowired
	private UserRepository userRepository;
	
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
	public LogEntity addLog(UserPrincipal userPrincipal, LogDto dto) {
		// TODO Auto-generated method stub
		Integer userId = userPrincipal.getId();
		UserEntity userEntity = userRepository.findById(userId).get();
		LogEntity logEntity = convertToLogEntity(dto);
		logEntity.setUserEntity(userEntity);
		logRepository.save(logEntity);
		return logEntity;
	}


	@Override
	public LogEntity updateLog(Integer idLog, LogDto dto) {
		// TODO Auto-generated method stub
		LogEntity logEntity = logRepository.findById(idLog).get();
		logEntity.setDateTime(LocalDateTime.now());
		logEntity.setAction(dto.getAction());
		logEntity.setDescription(dto.getDescription());
		logRepository.save(logEntity);
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

	@Override
	public ResponseEntity<?> getLogLastActivity() {
		// TODO Auto-generated method stub
		List<LogEntity> logEntities = logRepository.findLastActivity();
		return ResponseEntity.ok(logEntities);
	}


}
