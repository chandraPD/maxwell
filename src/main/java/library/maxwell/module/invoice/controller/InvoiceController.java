package library.maxwell.module.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.config.security.auth.CurrentUser;
import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.service.InvoiceService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("invoice")
public class InvoiceController {

	@Autowired
    private InvoiceService invoiceService;
	
	@GetMapping("/user/get-all")
	public ResponseEntity<?> getAllUser(@CurrentUser UserPrincipal userprincipal){
		StatusMessageDto<?> result = invoiceService.getAll(userprincipal,"");
		return ResponseEntity.ok(result);
	}

	@GetMapping("/admin/get-all")
	public ResponseEntity<?> getAll(){
		StatusMessageDto<?> result = invoiceService.getAll();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/user/get-all-need-paid")
	public ResponseEntity<?> getAllUserByStatusInvoice(@CurrentUser UserPrincipal userprincipal){
		StatusMessageDto<?> result = invoiceService.getAll(userprincipal,"Waiting For Payment");
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get-by-id/{invoiceId}")
	public ResponseEntity<?> getById(@PathVariable Integer invoiceId){
		StatusMessageDto<?> result = invoiceService.getById(invoiceId);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/pay/{invoiceId}")
	public StatusMessageDto<?> pay(@CurrentUser UserPrincipal userPrincipal, @PathVariable Integer invoiceId){
		return invoiceService.pay(userPrincipal, invoiceId);
	}

}
