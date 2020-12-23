package library.maxwell.module.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import library.maxwell.module.book.entity.BorrowedBookEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_detail")
@Data
public class InvoiceDetailEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "invoice_detail_id")
	private Integer invoiceDetailId;
	
	@ManyToOne
	@JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
	private InvoiceEntity invoiceEntity;
	
	@ManyToOne
	@JoinColumn(name = "borrowed_book_id", referencedColumnName = "borrowed_book_id")
	private BorrowedBookEntity borrowedBookEntity;
	
	@Column(name = "type", length = 30, nullable = false)
	private String type;
	
	@Column(name = "total", nullable = false)
	private Double total;
	
	@Column(name = "status", nullable = false)
	private Boolean status = false;
}
