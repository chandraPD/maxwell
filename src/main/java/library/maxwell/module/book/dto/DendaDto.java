package library.maxwell.module.book.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DendaDto {
    private String title;
    private Integer lateBy;
    private Double fine;
}
