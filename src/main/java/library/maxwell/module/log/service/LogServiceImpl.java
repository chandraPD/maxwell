package library.maxwell.module.log.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.log.dto.LogDto;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;
import library.maxwell.module.user.dto.UserDetailDto;
import library.maxwell.module.user.entity.UserDetailEntity;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.repository.UserDetailRepository;
import library.maxwell.module.user.repository.UserRepository;

@Service
@Transactional
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailRepository userDetailRepository;
	
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
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String dateUpdate = today.format(date);
		logEntity.setDateTime(LocalDateTime.parse(dateUpdate, date));
		logEntity.setAction(dto.getAction());
		logEntity.setDescription(dto.getDescription());
		return logEntity;

}

	@Override
	public ResponseEntity<?> getLogLastActivity() {
		// TODO Auto-generated method stub
		List<LogEntity> logEntities = logRepository.findLastActivity();
		List<LogDto> logDto = new ArrayList<>();
		for(LogEntity logEntity : logEntities) {
			LogDto logDto2 = new LogDto();
			Integer userId = logEntity.getUserEntity().getUserId();
			
			UserEntity userEntity = userRepository.findById(userId).get();
			UserDetailEntity userDetail = userDetailRepository.findByUserEntityUserId(userId);
			
			logDto2.setLogId(logEntity.getLogId());
			logDto2.setAction(logEntity.getAction());
			logDto2.setDescription(logEntity.getDescription());
			logDto2.setDateTime(logEntity.getDateTime());
			logDto2.setName(userDetail.getFirstName() + " " + userDetail.getLastName());
			logDto2.setEmail(userEntity.getEmail());
			logDto2.setUserId(userId);
			
			logDto.add(logDto2);
		
		}
		return ResponseEntity.ok(logDto);
	}

	//MENAMPILKAN LOG USER DI USER PROFILE
	@Override
	public ResponseEntity<?> getLogUser(UserPrincipal userPrincipal) {
		// TODO Auto-generated method stub
		return null;
	}

}
