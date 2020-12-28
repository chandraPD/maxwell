package library.maxwell.module.topup.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBalanceDto {
	private Double nominal;
	private Integer user_id;
	private Boolean status;
	
}
