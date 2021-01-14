package library.maxwell.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusMessageDto<T> {


	private Integer status;

    private String messages;

    private T data;

    public static <T> StatusMessageDto error(String message) {
        return StatusMessageDto.builder()
                .status(HttpStatus.BAD_GATEWAY.value())
                .messages(message)
                .build();
    }

    public static <T> StatusMessageDto success(String message, T data) {
        return StatusMessageDto.builder()
                .status(HttpStatus.OK.value())
                .messages(message)
                .data(data)
                .build();
    }
}
