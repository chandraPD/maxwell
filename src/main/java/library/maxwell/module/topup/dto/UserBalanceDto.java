package library.maxwell.module.topup.dto;

import lombok.Data;

@Data
public class UserBalanceDto {
	private Double nominal;
	private Integer user_id;
	private Boolean status;
	public Double getNominal() {
		return nominal;
	}
	public void setNominal(Double nominal) {
		this.nominal = nominal;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public UserBalanceDto(Double nominal, Integer user_id, Boolean status) {
		super();
		this.nominal = nominal;
		this.user_id = user_id;
		this.status = status;
	}
	public UserBalanceDto() {
		super();
	}
}
