package library.maxwell.module.log.dto;

import lombok.Data;

@Data
public class StatusMessageDto<T> {
	private Integer status;
	private String message;
	private T data;
}
