package library.maxwell.module.topup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.topup.service.HistoryBalanceImp;
import library.maxwell.module.topup.service.HistoryBalanceService;

@RestController
@RequestMapping("/top_up_management")
@CrossOrigin(origins = "http://localhost:3000")
public class TopUpManagement {
	@Autowired
	private HistoryBalanceService service;
	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(@CurrentUser UserPrincipal userprincipal){
		String role=userprincipal.getAuthorities().toString();	
		Integer id=userprincipal.getId();
		System.out.println(role);
		if (role.equals("[ROLE_ADMIN]")) {
			List<HistoryBalanceEntity> historyBalanceEntities=service.getAll();
			return ResponseEntity.ok(historyBalanceEntities);
		} else {
			List<HistoryBalanceEntity> historyBalanceEntities=service.getAll2(id);
			return ResponseEntity.ok(historyBalanceEntities);
		}		
	}

	@GetMapping("/getId/{id}")
	public ResponseEntity<?> getId(@PathVariable Integer id){
		HistoryBalanceEntity historyBalanceEntity=service.getById(id);
		return ResponseEntity.ok(historyBalanceEntity);
	}
}
