package library.maxwell.module.slideshow.entity;

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
@Table(name = "slideshow")
@Data
public class SlideshowEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "slideshow_id")
	private Integer slideShowId;

	@Column(name = "title", length = 50, nullable = false)
	private String title;

	@Column(name = "sub_title", length = 50, nullable = false)
	private String subTitle;

	@Column(name = "img", length = 255, nullable = false)
	private String img;

	@Column(name = "status_show", nullable = false)
	private Boolean statusShow = true;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	// relation
	@ManyToOne
	@JoinColumn(name = "created_by", referencedColumnName = "user_id")
	private UserEntity userEntity;

	@Column(name = "status")
	private Boolean status = true;
}
