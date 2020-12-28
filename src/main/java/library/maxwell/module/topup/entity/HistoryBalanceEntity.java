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
	
	public Integer getHistoryBalanceId() {
		return historyBalanceId;
	}

	public void setHistoryBalanceId(Integer historyBalanceId) {
		this.historyBalanceId = historyBalanceId;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public UserBalanceEntity getUserBalanceEntity() {
		return userBalanceEntity;
	}

	public void setUserBalanceEntity(UserBalanceEntity userBalanceEntity) {
		this.userBalanceEntity = userBalanceEntity;
	}

	public LocalDateTime getDateTopup() {
		return dateTopup;
	}

	public void setDateTopup(LocalDateTime dateTopup) {
		this.dateTopup = dateTopup;
	}

	public Double getNominal() {
		return nominal;
	}

	public void setNominal(Double nominal) {
		this.nominal = nominal;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatusPayment() {
		return statusPayment;
	}

	public void setStatusPayment(String statusPayment) {
		this.statusPayment = statusPayment;
	}

	public LocalDateTime getDateAcc() {
		return dateAcc;
	}

	public void setDateAcc(LocalDateTime dateAcc) {
		this.dateAcc = dateAcc;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public HistoryBalanceEntity(Integer historyBalanceId, UserEntity userEntity, UserBalanceEntity userBalanceEntity,
			LocalDateTime dateTopup, Double nominal, String paymentMethod, String statusPayment, LocalDateTime dateAcc,
			Boolean status) {
		super();
		this.historyBalanceId = historyBalanceId;
		this.userEntity = userEntity;
		this.userBalanceEntity = userBalanceEntity;
		this.dateTopup = dateTopup;
		this.nominal = nominal;
		this.paymentMethod = paymentMethod;
		this.statusPayment = statusPayment;
		this.dateAcc = dateAcc;
		this.status = status;
	}

	public HistoryBalanceEntity() {
		super();
	}

	@Column(name = "nominal")
	private Double nominal;

	@Column(name = "paymentMethod")
	private String paymentMethod;

	@Column(name = "status_payment")
	private String statusPayment;

	@Column(name = "date_acc")
	private LocalDateTime dateAcc;

	@Column(name = "status")
	private Boolean status = true;
}
