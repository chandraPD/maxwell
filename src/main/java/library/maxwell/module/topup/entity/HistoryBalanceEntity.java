package library.maxwell.module.topup.entity;

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
@Table(name = "history_balance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryBalanceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_balance_id")
	private Integer historyBalanceId;

	@ManyToOne
	@JoinColumn(name = "acc_by", referencedColumnName = "user_id")
	private UserEntity userEntity;

	@ManyToOne
	@JoinColumn(name = "user_balance_id", referencedColumnName = "user_balance_id")
	private UserBalanceEntity userBalanceEntity;
	
	@CreatedDate
	@Column(name = "date_topup")
	private LocalDateTime dateTopup;
	
	@Column(name = "nominal")
	private Double nominal;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "status_payment")
	private String statusPayment;

	@Column(name = "date_acc")
	private LocalDateTime dateAcc;

	@Column(name = "status")
	private Boolean status = true;
}
