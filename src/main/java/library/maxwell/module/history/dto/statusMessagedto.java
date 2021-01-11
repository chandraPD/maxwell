package library.maxwell.module.history.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class statusMessagedto <T> {

    private Integer status;

    private String messages;

    private T data;
}
