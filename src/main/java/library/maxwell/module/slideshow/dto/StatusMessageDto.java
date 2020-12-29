package library.maxwell.module.slideshow.dto;

import lombok.Data;

@Data
public class StatusMessageDto<T> {
	private Integer status;
	private String message;
	private T data;
}
