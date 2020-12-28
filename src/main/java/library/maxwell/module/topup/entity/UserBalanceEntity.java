package library.maxwell.module.topup.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import library.maxwell.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_balance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_balance_id")
	private Integer userBalanceId;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserEntity userEntity;
	
	public Integer getUserBalanceId() {
		return userBalanceId;
	}

	public void setUserBalanceId(Integer userBalanceId) {
		this.userBalanceId = userBalanceId;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Double getNominal() {
		return nominal;
	}

	public void setNominal(Double nominal) {
		this.nominal = nominal;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public UserBalanceEntity(Integer userBalanceId, UserEntity userEntity, Double nominal, Boolean status) {
		super();
		this.userBalanceId = userBalanceId;
		this.userEntity = userEntity;
		this.nominal = nominal;
		this.status = status;
	}

	public UserBalanceEntity() {
		super();
	}

	@Column(name="nominal")
	private Double nominal;
	
	@Column(name="status")
	private Boolean status=true;
}
