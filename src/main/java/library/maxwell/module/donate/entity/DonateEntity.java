package library.maxwell.module.donate.entity;

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
@Table(name = "donate")
@Data
public class DonateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "donate_id")
	private Integer donateId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private UserEntity userEntity;

	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Column(name = "donation_type", length = 30, nullable = false)
	private String donationType;

	@Column(name = "total_book", nullable = false)
	private Integer totalBook;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "status_donate", length = 15, nullable = false)
	private String statusDonate;

	@Column(name = "status")
	private Boolean status = true;
}
