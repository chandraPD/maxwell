package library.maxwell.module.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.invoice.entity.InvoiceEntity;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer>{
	
	@Query(value = "select ud.*, i.*  from invoice as i join user_detail as ud on ud.user_id = i.borrower where i.status = true", nativeQuery = true)
	List<InvoiceEntity> getdataAll();
	
	@Query(value = "select i.*, ud.* from invoice i join user_detail as ud on ud.user_id=i.borrower where i.status = true and i.invoice_id = ?", nativeQuery = true)
	InvoiceEntity getById(Integer invoiceId);
	
	@Query(value = "select no_invoice from invoice where invoice_id = ?", nativeQuery = true)
	String findNoinvoiceById(Integer invoiceId);
	
}
