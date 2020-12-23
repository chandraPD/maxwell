package library.maxwell.module.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_akses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAksesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_akses_id")
	private Integer userAksesId;

	@ManyToOne
	@JoinColumn(name = "level_id", referencedColumnName = "level_id")
	private LevelEntity levelEntity;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserEntity userEntity;

	@Column(name = "status")
	private Boolean status = true;

}
