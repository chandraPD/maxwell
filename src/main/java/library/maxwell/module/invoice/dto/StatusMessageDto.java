package library.maxwell.module.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

//Gunakan lombok untuk generate constructor, ini akan memudahkan sekaligus supaya code rapi
@Data //Untuk generate getter dan setter
@NoArgsConstructor // untuk generate empty param constructor
@AllArgsConstructor // untuk generate constructor semua param
@Builder //untuk inisialisasi object, bisa gunakan builder pattern juga
//https://jeffryanto-wijaya.medium.com/mengenal-builder-pattern-java-460932419b75
//contoh implementasinya ada dibawah
public class StatusMessageDto<T> {

	private Integer status;

	private String message;

	private T data;

	// good practice juga kalau bikin static factory method (tidak harus)
   // https://medium.com/javarevisited/static-factory-methods-an-alternative-to-public-constructors-73cbe8b9fda
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
