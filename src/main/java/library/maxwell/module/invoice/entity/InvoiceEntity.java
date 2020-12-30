package library.maxwell.module.invoice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import library.maxwell.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice")
@Data
public class InvoiceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private Integer invoiceId;
	
	@ManyToOne
	@JoinColumn(name = "checked_by", referencedColumnName = "user_id")
	private UserEntity checkedByEntity;

	@ManyToOne
	@JoinColumn(name = "borrower", referencedColumnName = "user_id")
	private UserEntity borrowerEntity;
	
	@Column(name = "no_invoice", unique = true, length = 50, nullable = false)
	private String noInvoice;
	
    @CreatedDate
	@Column(name = "invoice_date", nullable = false)
	private LocalDateTime invoiceDate;
	
	@Column(name = "grand_total", nullable = false)
	private Double grandTotal;
	
	@Column(name = "payment_date", nullable = true)
	private LocalDateTime paymentDate;
	
	@Column(name = "status_invoice", nullable = false)
	private String statusInvoice;
	
	@Column(name = "status")
	private Boolean status = true;
	
}	
