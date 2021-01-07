package library.maxwell.module.topup.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.topup.dto.HistoryBalanceDto;
import library.maxwell.module.topup.dto.StatusMessageDto;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.topup.entity.UserBalanceEntity;
import library.maxwell.module.topup.service.HistoryBalanceService;
import library.maxwell.module.user.entity.UserEntity;
import library.maxwell.module.user.service.UserService;
import library.maxwell.module.user.service.UserServiceImpl;

@RestController
@RequestMapping("/top_up")
@CrossOrigin(origins = "http://localhost:3000")
public class TopUp {
	@Autowired
	private HistoryBalanceService service;
	
	@Autowired
	private UserService service2;
	
	@PostMapping("/post")
	public ResponseEntity<?> post(@CurrentUser UserPrincipal userprincipal, @RequestBody HistoryBalanceDto Dto){	
		Integer id=userprincipal.getId();
		String role=service2.getRole(id).toString();
		StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();
		if (role.equals("ROLE_ADMIN")) {
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setMessage("Role Admin");
			result.setData(null);
			return ResponseEntity.ok(result);
		} else {
			if (Dto.getNominal().equals(0)) {			
				result.setStatus(HttpStatus.BAD_REQUEST.value());
				result.setMessage("Nominal Tidak boleh kosong");
				result.setData(null);
				return ResponseEntity.ok(result);
			} else if (Dto.getNominal().isNaN()) {
				result.setStatus(HttpStatus.BAD_REQUEST.value());
				result.setMessage("Nominal Bukan Angka");
				result.setData(null);
				return ResponseEntity.ok(result);
			} else if (Dto.getPaymentMethod().isEmpty()) {			
				result.setStatus(HttpStatus.BAD_REQUEST.value());
				result.setMessage("Payment Method Kosong");
				result.setData(null);
				return ResponseEntity.ok(result);
			} else {
				HistoryBalanceEntity historyBalanceEntity=service.post(userprincipal,Dto);
				result.setStatus(HttpStatus.OK.value());
				result.setMessage("Berhasil Insert");
				result.setData(historyBalanceEntity);
				return ResponseEntity.ok(result);
			}
		}			
	}
	
	@PostMapping("/post2")
	public ResponseEntity<?> post2(@CurrentUser UserPrincipal userprincipal, @RequestBody HistoryBalanceDto Dto){		
		StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();
		Integer id=userprincipal.getId();
		String role=service2.getRole(id).toString();				
		if (role.equals("ROLE_ADMIN")) {					
			if (Dto.getUser_balance_id().equals("")) {			
				result.setStatus(HttpStatus.BAD_REQUEST.value());
				result.setMessage("User ID Tidak boleh kosong");
				result.setData(null);
				return ResponseEntity.ok(result);
			} else if (Dto.getNominal().isNaN()) {
				result.setStatus(HttpStatus.BAD_REQUEST.value());
				result.setMessage("Nominal Bukan Angka");
				result.setData(null);
				return ResponseEntity.ok(result);
			}else if (Dto.getNominal().isNaN()) {
				result.setStatus(HttpStatus.BAD_REQUEST.value());
				result.setMessage("Nominal Bukan Angka");
				result.setData(null);
				return ResponseEntity.ok(result);
			} else if (Dto.getPaymentMethod().isEmpty()) {			
				result.setStatus(HttpStatus.BAD_REQUEST.value());
				result.setMessage("Payment Method Kosong");
				result.setData(null);
				return ResponseEntity.ok(result);
			} else {				
				HistoryBalanceEntity historyBalanceEntity=service.post2(Dto);
				result.setStatus(HttpStatus.OK.value());
				result.setMessage("Berhasil Insert");
				result.setData(historyBalanceEntity);
				return ResponseEntity.ok(result);
			}			
		}
		else {				
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setMessage("Role bukan Admin");
			result.setData(null);
			return ResponseEntity.ok(result);
		}
		
	}
	
	@PutMapping("/accept/{id}")
	public ResponseEntity<?> accept(@CurrentUser UserPrincipal userprincipal,@RequestBody HistoryBalanceDto Dto,@PathVariable Integer id){
		Integer id2=userprincipal.getId();
		String role=service2.getRole(id2).toString();
		if (role.equals("ROLE_ADMIN")) {			
			StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();
			HistoryBalanceEntity historyBalanceEntity=service.accept(userprincipal,Dto, id);
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Berhasil Accept");
			result.setData(historyBalanceEntity);
			return ResponseEntity.ok(result);
		} else {
			StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();			
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setMessage("Role bukan Admin");
			result.setData(null);
			return ResponseEntity.ok(result);
		}
		
	}
	
	@PutMapping("/cancel/{id}")
	public ResponseEntity<?> cancel(@CurrentUser UserPrincipal userprincipal,@RequestBody HistoryBalanceDto Dto,@PathVariable Integer id){
		Integer id2=userprincipal.getId();
		String role=service2.getRole(id2).toString();			
		if (role.equals("ROLE_ADMIN")) {
			StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();
			HistoryBalanceEntity historyBalanceEntity=service.cancel(userprincipal,Dto, id);
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Berhasil Cancel");
			result.setData(historyBalanceEntity);
			return ResponseEntity.ok(result);
		} else {
			StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();			
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setMessage("Role bukan Admin");
			result.setData(null);
			return ResponseEntity.ok(result);
		}
		
	}
}
