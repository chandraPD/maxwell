package library.maxwell.module.log.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.log.dto.LogDto;
import library.maxwell.module.log.dto.StatusMessageDto;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.service.LogServiceImpl;

@RestController
@RequestMapping("/log")
@CrossOrigin
public class LogController {
	@Autowired
	private LogServiceImpl logService;
	
	//GET ALL
	@GetMapping("/get-all-log")
	public ResponseEntity<?> getAllLog(){
		List<LogEntity> logEntities = logService.getAllLog();
		return ResponseEntity.ok(logEntities);
	}
	
	//GET BY ID
	@GetMapping("/get-log-byId/{idLog}")
	public ResponseEntity<?> getLogById(@PathVariable Integer idLog){
		LogEntity logEntity = logService.getLogById(idLog);
		if(logEntity == null) {
			StatusMessageDto<LogEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("DATA TIDAK DITEMUKAN!");
			result.setData(logEntity);
			return ResponseEntity.badRequest().body(result);
		}
		StatusMessageDto<LogEntity> result = new StatusMessageDto<>();
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("DATA TELAH DITEMUKAN!");
		result.setData(logEntity);
		return ResponseEntity.ok(result);
		
	}
	
	//GET LAST ACTIVITY
	@GetMapping("/get-log-lastActivity")
	public ResponseEntity<?> getLastActivity(){
		return logService.getLogLastActivity();
	}
	
	//POST
	@PostMapping("/add-log")
	public ResponseEntity<?> addLog(@CurrentUser UserPrincipal userPrincipal, @RequestBody LogDto dto){
		LogEntity logEntity = logService.addLog(userPrincipal, dto);
		if(logEntity == null) {
			StatusMessageDto<LogEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.BAD_REQUEST.value());
			result.setMessage("ID SUDAH DIGUNAKAN!");
			result.setData(logEntity);
			return ResponseEntity.badRequest().body(result);
		} else {
			StatusMessageDto<LogEntity> result = new StatusMessageDto<>();
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data berhasil diinputkan!");
			result.setData(logEntity);
			return ResponseEntity.ok(result);
		}
	}
	
	//GET LOG USER
	@GetMapping("/get-log-user")
	public ResponseEntity<?> getLogUser(@CurrentUser UserPrincipal userPrincipal){
		return logService.getLogUser(userPrincipal);
	}
	
}









