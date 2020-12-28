package library.maxwell.module.topup.dto;

import java.time.LocalDateTime;

public class HistoryBalanceDto {
	private Integer acc_by;
	private Integer user_balance_id;
	private LocalDateTime dateTopup;
	private Double nominal;
	private String paymentMethod;
	private String statusPayment;
	private LocalDateTime dateAcc;
	private Boolean status;
	public Integer getAcc_by() {
		return acc_by;
	}
	public void setAcc_by(Integer acc_by) {
		this.acc_by = acc_by;
	}
	public Integer getUser_balance_id() {
		return user_balance_id;
	}
	public void setUser_balance_id(Integer user_balance_id) {
		this.user_balance_id = user_balance_id;
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
	public HistoryBalanceDto(Integer acc_by, Integer user_balance_id, LocalDateTime dateTopup, Double nominal,
			String paymentMethod, String statusPayment, LocalDateTime dateAcc, Boolean status) {
		super();
		this.acc_by = acc_by;
		this.user_balance_id = user_balance_id;
		this.dateTopup = dateTopup;
		this.nominal = nominal;
		this.paymentMethod = paymentMethod;
		this.statusPayment = statusPayment;
		this.dateAcc = dateAcc;
		this.status = status;
	}
	public HistoryBalanceDto() {
		super();
	}
}
