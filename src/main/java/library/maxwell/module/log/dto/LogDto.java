package library.maxwell.module.log.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDto {
	private Integer logId;
	private String action;
	private String description;
	private LocalDateTime dateTime;
	private Boolean status = true;
}
