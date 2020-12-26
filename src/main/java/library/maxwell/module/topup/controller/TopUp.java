package library.maxwell.module.topup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.module.topup.dto.HistoryBalanceDto;
import library.maxwell.module.topup.dto.StatusMessageDto;
import library.maxwell.module.topup.entity.HistoryBalanceEntity;
import library.maxwell.module.topup.service.HistoryBalanceImp;

@RestController
@RequestMapping("/top_up")
public class TopUp {
	@Autowired
	HistoryBalanceImp service;
	
	@PostMapping("/post")
	public ResponseEntity<?> post(@RequestBody HistoryBalanceDto Dto){
		StatusMessageDto<HistoryBalanceEntity> result=new StatusMessageDto<>();
		HistoryBalanceEntity historyBalanceEntity=service.post(Dto);
		result.setStatus(HttpStatus.OK.value());
		result.setMessage("Berhasil Insert");
		result.setData(historyBalanceEntity);
		return ResponseEntity.ok(result);
	}
}
