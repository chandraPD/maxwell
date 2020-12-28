package library.maxwell.module.topup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StatusMessageDto<T> {
	private String Message;
	private Integer status;
	private T data;
	


}
