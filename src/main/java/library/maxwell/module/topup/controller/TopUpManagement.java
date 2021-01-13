package library.maxwell.module.topup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import library.maxwell.module.topup.dto.HistoryBalanceDto;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;

import library.maxwell.module.topup.service.HistoryBalanceService;
import library.maxwell.module.topup.service.UserBalanceService;
import library.maxwell.module.user.service.UserService;

@RestController
@RequestMapping("/top_up_management")
@CrossOrigin
public class TopUpManagement {
	@Autowired
	private HistoryBalanceService service;

	@Autowired
	private UserBalanceService userBalanceService;

	@Autowired
	private UserService user;

	@GetMapping("/getRole")
	public ResponseEntity<?> getRole(@CurrentUser UserPrincipal userprincipal) {
		Integer id = userprincipal.getId();
		String role = user.getRole(id).toString();
		return ResponseEntity.ok(role);
	}

	@PostMapping("/getPass")
	public ResponseEntity<?> getpass(@CurrentUser UserPrincipal userprincipal, @RequestBody HistoryBalanceDto dto) {
		Boolean isBoolean = service.getPass(userprincipal, dto);
		return ResponseEntity.ok(isBoolean);
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll(@CurrentUser UserPrincipal userprincipal) {
		Integer id = userprincipal.getId();
		String role = user.getRole(id).toString();		
		if (role.equals("ROLE_ADMIN")) {
			List<HistoryBalanceEntity> historyBalanceEntities = service.getAll();
			return ResponseEntity.ok(historyBalanceEntities);
		} else {
			List<HistoryBalanceEntity> historyBalanceEntities = service.getAll2(id);
			return ResponseEntity.ok(historyBalanceEntities);
		}
	}

	@GetMapping("/getId/{id}")
	public ResponseEntity<?> getId(@PathVariable Integer id) {
		HistoryBalanceEntity historyBalanceEntity = service.getById(id);
		return ResponseEntity.ok(historyBalanceEntity);
	}

	@GetMapping("/getBalance")
	public Double getBalance(@CurrentUser UserPrincipal userPrincipal) {
		Double balance = userBalanceService.getSaldo(userPrincipal) == null ? 0
				: userBalanceService.getSaldo(userPrincipal);

		if (balance != 0) {
			return balance;
		} else {

			return balance = (double) 0;
		}

	}

}
