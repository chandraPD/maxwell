package library.maxwell.module.invoice.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import library.maxwell.module.invoice.dto.InvoiceDto;
import library.maxwell.module.invoice.dto.StatusMessageDto;
import library.maxwell.module.invoice.entity.InvoiceEntity;
import library.maxwell.module.invoice.repository.InvoiceRepository;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService{

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Override
	public StatusMessageDto<List<InvoiceEntity>> getAll() {
		// TODO Auto-generated method stub
      // Jangan pakai <?>, selalu spesifikin class apa yang direturn supaya gampang nanti kalau misalnya
      // mau modify datanya
		StatusMessageDto<List<InvoiceEntity>> result = new StatusMessageDto<>();
		// Spring JPA telah menyediakan beberapa method default untuk get data, salah satunya get all data,
        // cukup invoiceRepository.findAll();
		List<InvoiceEntity> invoiceEntity = invoiceRepository.getdataAll();

		if(invoiceEntity != null) {
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data Invoice telah ditemukan");
			result.setData(invoiceEntity);
		}else {
			result.setMessage("Data belum ada");
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			// tidak tepat kalau data tidak ditemukan itu Bad Gateway, walaupun tidak ditemukan harusnya tetap 200
          // karena error 5xx itu dibalikin kalau ada error diservernya
			result.setData(null);
		}
		return result;

		// alternative, code diatas bisa disimplify menjadi
//         List<InvoiceEntity> invoiceEntity = invoiceRepository.findAllByStatusIsTrue();
//         return Optional.ofNullable(invoiceEntity)
//             .map(invoiceEntities -> StatusMessageDto.success("Data Invoice telah ditemukan", invoiceEntities))
//             .orElse(StatusMessageDto.error("Data Invoice telah ditemukan"));
	}
	
	
	@Override
	public StatusMessageDto<?> saveMulti(InvoiceDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public StatusMessageDto<?> getById(Integer invoiceId) {
		// TODO Auto-generated method stub
		InvoiceEntity invoiceEntity = invoiceRepository.getById(invoiceId);
		System.out.println(invoiceEntity);
		StatusMessageDto<InvoiceEntity> result = new StatusMessageDto<>();
		if(invoiceEntity != null) {
			result.setStatus(HttpStatus.OK.value());
			result.setMessage("Data ditemukan");
			result.setData(invoiceEntity);
		}else {
			result.setMessage("Data belum ada");
			result.setStatus(HttpStatus.BAD_GATEWAY.value());
			result.setData(null);
		}
		return result;
	}

	
//	method
	
	public InvoiceEntity convertToInvoiceEntity(InvoiceDto dto) {
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		invoiceEntity.setNoInvoice(dto.getNoInvoice());
		invoiceEntity.setInvoiceDate(dto.getInvoiceDate());
		invoiceEntity.setGrandTotal(dto.getGrandTotal());
		return invoiceEntity;
	}

}
