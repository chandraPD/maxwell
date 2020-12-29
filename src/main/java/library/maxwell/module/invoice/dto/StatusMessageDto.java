package library.maxwell.module.invoice.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusMessageDto<T> {
	
	private Integer status;
	
	private String message;
	
	private T data;
	
	public static <T>StatusMessageDto error(String message) {
	      return StatusMessageDto.builder()
	          .status(HttpStatus.BAD_GATEWAY.value())
	          .message(message)
	          .build();
	    }

	  public static <T>StatusMessageDto success(String message, T data) {
	    return StatusMessageDto.builder()
	        .status(HttpStatus.OK.value())
	        .message(message)
	        .data(data)
	        .build();
	  }
}
