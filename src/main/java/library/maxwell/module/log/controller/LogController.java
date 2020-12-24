package library.maxwell.module.log.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.module.log.dto.LogDto;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.repository.LogRepository;

@RestController
@Transactional
public class LogController {
	@Autowired
	private LogRepository logRepository;
	
	//GET ALL
	@GetMapping("/get-all-log")
	public List<LogEntity> getAllLog(){
		List<LogEntity> logEntities = logRepository.findAll();
		return logEntities;
	}
	
	//GET BY ID
	@GetMapping("/get-log-byId/{idLog}")
	public ResponseEntity<?> getLogById(@PathVariable Integer idLog){
		LogEntity logEntity = logRepository.findById(idLog).get();
		return ResponseEntity.ok(logEntity);
		
	}
	
	//POST
	@PostMapping("/add-log")
	public ResponseEntity<?> addLog(@RequestBody LogDto dto){
		LogEntity logEntity = convertToLogEntity(dto);
		return ResponseEntity.ok(logEntity);
	}
	//UPDATE
	@PutMapping("/update-log/{idLog}")
	public ResponseEntity<?> updateLog(@PathVariable Integer idLog,
			@RequestBody LogDto dto){
		LogEntity logEntity = logRepository.findById(idLog).get();
		LogEntity updateLog = convertToLogEntity(dto);
		logRepository.save(updateLog);
		return ResponseEntity.ok(logEntity);
	}
	
	//DELETE
	@PutMapping("/delete-log/{idLog}")
	public ResponseEntity<?> deleteLog(@PathVariable Integer idLog){
		LogEntity logEntity = logRepository.findById(idLog).get();
		logEntity.setStatus(false);
		logRepository.save(logEntity);
		return ResponseEntity.ok(logEntity);
	}
	
	public LogEntity convertToLogEntity(LogDto dto) {
		LogEntity logEntity = new LogEntity();
		logEntity.setDateTime(dto.getDateTime());
		logEntity.setAction(dto.getAction());
		logEntity.setDescription(dto.getDescription());
		return logEntity;
	}
}
