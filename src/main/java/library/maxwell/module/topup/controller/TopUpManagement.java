package library.maxwell.module.topup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.topup.service.HistoryBalanceImp;

@RestController
@RequestMapping("/top_up_management")
@CrossOrigin(origins = "http://localhost:3000")
public class TopUpManagement {
	@Autowired
	HistoryBalanceImp service;
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(){
		List<HistoryBalanceEntity> historyBalanceEntities=service.getAll();
		return ResponseEntity.ok(historyBalanceEntities);
	}

	@GetMapping("/getId/{id}")
	public ResponseEntity<?> getId(@PathVariable Integer id){
		HistoryBalanceEntity historyBalanceEntity=service.getById(id);
		return ResponseEntity.ok(historyBalanceEntity);
	}
}
