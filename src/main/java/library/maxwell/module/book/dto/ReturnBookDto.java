package library.maxwell.module.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBookDto {
    private Integer borrowBookId;
    private DendaDto denda;
}
