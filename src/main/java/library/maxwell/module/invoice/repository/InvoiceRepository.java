package library.maxwell.module.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.invoice.entity.InvoiceEntity;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Integer>{
	
	List<InvoiceEntity> findAllByStatusIsTrueOrderByInvoiceIdDesc();
	List<InvoiceEntity> findAllByBorrowerEntity_UserIdIs(int userId);


	List<InvoiceEntity> findAllByStatusIsTrueAndBorrowerEntity_UserIdIsOrderByInvoiceIdDesc(int userId);
	List<InvoiceEntity> findAllByStatusIsTrueAndBorrowerEntity_UserIdIsAndStatusInvoiceIsOrderByInvoiceIdDesc(int userId, String statusInvoice);
	
	List<InvoiceEntity> findAllByStatusIsTrueAndStatusInvoiceIs(String statusInvoice);

	InvoiceEntity findByInvoiceId(Integer invoiceId);


	@Query(value = "select no_invoice from invoice where EXTRACT(YEAR FROM invoice_date) = ? order by invoice_id DESC limit 1  ", nativeQuery = true)
	String getLastInvoice(Integer year);
	
}
