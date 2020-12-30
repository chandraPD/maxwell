package library.maxwell.module.invoice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.service.InvoiceDetailServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("invoice-detail")
public class InvoiceDetailController {

	@Autowired
	InvoiceDetailServiceImpl invoiceDetailService;
	
	@GetMapping("/get-by-invoice-id/{invoiceId}")
	public ResponseEntity<?> getByInvoiceId(@PathVariable Integer invoiceId){
		System.out.println(invoiceId);
		StatusMessageDto<?> result = invoiceDetailService.getByInvoiceId(invoiceId);
		return ResponseEntity.ok(result);
	}
}
