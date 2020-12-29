package library.maxwell.module.donate.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import library.maxwell.module.user.entity.UserEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "donate")
@Data
@EntityListeners(AuditingEntityListener.class)
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
