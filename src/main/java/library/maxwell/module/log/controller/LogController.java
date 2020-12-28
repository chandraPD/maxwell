package library.maxwell.module.log.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.module.log.dto.LogDto;
import library.maxwell.module.log.entity.LogEntity;
import library.maxwell.module.log.service.LogServiceImpl;

@RestController
@RequestMapping("/log")
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
		return ResponseEntity.ok(logEntity);
		
	}
	
	//POST
	@PostMapping("/add-log")
	public ResponseEntity<?> addLog(@RequestBody LogDto dto){
		LogEntity logEntity = logService.addLog(dto);
		return ResponseEntity.ok(logEntity);
	}
	
	//UPDATE
	@PutMapping("/update-log/{idLog}")
	public ResponseEntity<?> updateLog(@PathVariable Integer idLog,
			@RequestBody LogDto dto){
		LogEntity logEntity = logService.updateLog(idLog, dto);
		return ResponseEntity.ok(logEntity);
	}
	
	//DELETE
	@PutMapping("/delete-log/{idLog}")
	public ResponseEntity<?> deleteLog(@PathVariable Integer idLog){
		LogEntity logEntity = logService.deleteLog(idLog);
		return ResponseEntity.ok(logEntity);
	}
}
