package library.maxwell.module.log.service;


import java.time.LocalDateTime;
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
		Boolean findLogEntity = logRepository.existsByLogId(idLog);
		
		if(findLogEntity) {
			LogEntity logEntity = logRepository.findById(idLog).get();
			return logEntity;
		}
		
		return null;
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
	
	public LogEntity convertToLogEntity(LogDto dto) {
		LogEntity logEntity = new LogEntity();
		LocalDateTime today = LocalDateTime.now();
		logEntity.setDateTime(today);
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
			
			//Untuk Mendapatkan Email
			UserEntity userEntity = userRepository.findById(userId).get();
			//Untuk mendapatkan name (firstName + lastName)
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
		Integer userId = userPrincipal.getId();
		List<LogEntity> logEntities = logRepository.findLogUser(userId);
		List<LogDto> logDto = new ArrayList<>();
		for(LogEntity logEntity : logEntities) {
			LogDto logDtoUser = new LogDto();
			Integer id = logEntity.getUserEntity().getUserId();
			
			UserDetailEntity userDetail = userDetailRepository.findByUserEntityUserId(id);
			logDtoUser.setAction(logEntity.getAction());
			logDtoUser.setDescription(logEntity.getDescription());
			logDtoUser.setDateTime(logEntity.getDateTime());
			logDtoUser.setName(userDetail.getFirstName() + " " + userDetail.getLastName());
			logDtoUser.setUserId(id);
			
			logDto.add(logDtoUser);
		}
		return ResponseEntity.ok(logDto);
	}
	


}
