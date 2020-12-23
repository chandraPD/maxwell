package library.maxwell.module.log.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log")
@Data
public class LogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Integer logId;
	
	@Column (name = "action", length = 50, nullable = false)
	private String action;
	
	@Column (name = "description", nullable = false)
	private String description;
	
    @CreatedDate
	@Column (name = "dateTime", nullable = false)
	private LocalDateTime dateTime;
	
	@Column (name = "status")
	private Boolean status = true;
}
