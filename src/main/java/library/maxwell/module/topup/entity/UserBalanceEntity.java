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
	@Column(name = "user_balance_id")
	private Integer userBalanceId;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserEntity userEntity;

	@Column(name = "nominal")
	private Double nominal;

	@Column(name = "status")
	private Boolean status = true;
}
