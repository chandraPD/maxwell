package library.maxwell.module.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

	@Column(name = "status", columnDefinition = "byte(1) default 1")
	private Boolean status;

}
