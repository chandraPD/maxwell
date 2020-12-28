package library.maxwell.module.topup.dto;

public class StatusMessageDto<T> {
	private String Message;
	private Integer status;
	private T data;
	public StatusMessageDto() {
		super();
	}
	public StatusMessageDto(String message, Integer status, T data) {
		super();
		Message = message;
		this.status = status;
		this.data = data;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}


}
