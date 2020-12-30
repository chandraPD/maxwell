package library.maxwell.module.invoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.service.InvoiceService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("invoice")
public class InvoiceController {

	@Autowired
    private InvoiceService invoiceService;

	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(){
		StatusMessageDto<?> result = invoiceService.getAll();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/get-by-id/{invoiceId}")
	public ResponseEntity<?> getById(@PathVariable Integer invoiceId){
		System.out.println(invoiceId);
		StatusMessageDto<?> result = invoiceService.getById(invoiceId);
		return ResponseEntity.ok(result);
	}
	
}