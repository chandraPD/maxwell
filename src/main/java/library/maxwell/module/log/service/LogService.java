package library.maxwell.module.log.service;

import java.util.List;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.log.dto.LogDto;
import library.maxwell.module.log.entity.LogEntity;

public interface LogService {
	List<LogEntity> getAllLog();
	LogEntity getLogById(Integer idLog);
	LogEntity addLog(UserPrincipal userPrincipal, LogDto dto);
	LogEntity updateLog(Integer idLog, LogDto dto);
	LogEntity deleteLog(Integer idLog);
	
}
