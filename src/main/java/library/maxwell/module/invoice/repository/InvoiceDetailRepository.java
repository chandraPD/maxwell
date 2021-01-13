package library.maxwell.module.invoice.repository;

import java.util.List;

import library.maxwell.module.invoice.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import library.maxwell.module.invoice.entity.InvoiceDetailEntity;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetailEntity, Integer> {
	
	@Query(value = "select * from invoice_detail where invoice_id = ?", nativeQuery = true)
	List<InvoiceDetailEntity> getByInvoiceId(Integer invoiceId);

	InvoiceDetailEntity findTopByBorrowedBookEntity_BorrowedBookIdOrderByInvoiceDetailIdDesc(Integer borrowedBookId);

	List<InvoiceDetailEntity> findByInvoiceEntityInvoiceId(Integer invoiceId);

}
