package library.maxwell.module.invoice.controller;


import library.maxwell.module.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.service.InvoiceServiceImpl;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("invoice")
public class InvoiceController {

  // Best practice untuk autowired interfacenya bukan class implementationnya
  // https://stackoverflow.com/a/12899432/8424202
	@Autowired
    private InvoiceService invoiceService;
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(){
	  //Tidak perlu bungkus classnya ke dalam StatusMessageDto kalau misalnya di controllernya pakai ResponseEntity
      // jadinya tidak konsisten, di http code yang didapat tetep dapat 200 meski isi StatusMessageDtonya 502
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
