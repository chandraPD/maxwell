package library.maxwell.module.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "address")
	private String address;

	@Column(name = "img")
	private String img;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "status")
	private Boolean status = true;

}
