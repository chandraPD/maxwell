package library.maxwell.module.topup.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HistoryBalanceDto {
	private Integer acc_by;
	private Integer user_balance_id;
	private LocalDateTime dateTopup;
	private Double nominal;
	private String paymentMethod;
	private String statusPayment;
	private LocalDateTime dateAcc;
	private Boolean status;

}
