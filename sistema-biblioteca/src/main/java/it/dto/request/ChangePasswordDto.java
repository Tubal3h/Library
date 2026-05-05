package it.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDto {
	@NotBlank(message = "il campo vecchia password e' obbligatorio")
	private String oldPassword;
	
	@NotBlank(message = "il campo nuova password e' obbligatorio")
	private String newPassword;
	
	@NotBlank(message = "il campo conferma password e' obbligatorio")
	private String confirmPassword;
	
	public ChangePasswordDto(String oldPassword, String newPassword, String confirmPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}
	
	public ChangePasswordDto() {
		
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
}
