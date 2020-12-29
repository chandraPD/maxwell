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
import library.maxwell.module.user.service.UserService;
import library.maxwell.module.user.service.UserServiceImpl;

@RestController
@RequestMapping("/top_up")
@CrossOrigin(origins = "http://localhost:3000")
public class TopUp {
	@Autowired
	private HistoryBalanceService service;
	
	@PostMapping("/post")
	public ResponseEntity<?> post(@CurrentUser UserPrincipal userprincipal, @RequestBody HistoryBalanceDto Dto){	
		StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();
		HistoryBalanceEntity historyBalanceEntity=service.post(userprincipal,Dto);
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Berhasil Insert");
		result.setData(historyBalanceEntity);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/accept/{id}")
	public ResponseEntity<?> accept(@RequestBody HistoryBalanceDto Dto,@PathVariable Integer id){
		StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();
		HistoryBalanceEntity historyBalanceEntity=service.accept(Dto, id);
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Berhasil Accept");
		result.setData(historyBalanceEntity);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/cancel/{id}")
	public ResponseEntity<?> cancel(@RequestBody HistoryBalanceDto Dto,@PathVariable Integer id){
		StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();
		HistoryBalanceEntity historyBalanceEntity=service.cancel(Dto, id);
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Berhasil Cancel");
		result.setData(historyBalanceEntity);
		return ResponseEntity.ok(result);
	}
}
