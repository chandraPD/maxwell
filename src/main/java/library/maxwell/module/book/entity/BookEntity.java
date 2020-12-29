package library.maxwell.module.book.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import library.maxwell.module.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Integer BookId;
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private CategoryEntity categoryEntity;
	
	@Column(name = "title", length = 100)
	private String title;
	
	@Column(name = "description", columnDefinition = "TEXT")
	@Type(type = "text")
	private String description;
	
	@Column(name = "img_banner")
	private String imgBanner;
	
	@Column(name = "img_detail")
	private String imgDetail;
	
	@Column(name = "qty")
	private Integer qty;
	
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private UserEntity createdByEntity;
    
    @ManyToOne
    @JoinColumn(name = "update_by", referencedColumnName = "user_id")
	private UserEntity updatedByEntity;
	
	@Column(name = "status_book", length = 50)
	private String statusBook;
	
	@Column(name = "publish_date")
	private Date publishDate;
	
	@Column(name = "author", length = 50)
	private String author;
	
	@Column(name = "status")
	private Boolean status = true;
	
}
