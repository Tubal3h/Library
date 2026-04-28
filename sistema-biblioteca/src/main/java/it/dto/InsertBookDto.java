package it.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InsertBookDto {
	
	@NotBlank
	private String title;
	
	
	@NotBlank
	private String isbn;
	
	
	@NotBlank
	private String authorName;
	
	
	@NotBlank
	private String authorLastName;
	
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	LocalDate localDate;
	
	
	@NotBlank
	private String categoryName;
	
	
	@NotBlank
	private String publisherName;
	
	
	@NotBlank
	private String email;
	
	public InsertBookDto(String title, String isbn, String authorName, String authorLastName, LocalDate localDate,String categoryName,
			String publisherName, String email) {
		
		this.title = title;
		this.isbn = isbn;
		this.authorName = authorName;
		this.authorLastName = authorLastName;
		this.localDate = localDate;
		this.categoryName = categoryName;
		this.publisherName = publisherName;
		this.email = email;
	}
	
	public InsertBookDto() {
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public LocalDate getLocalDate() {
		return localDate;
	}
	
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorLastName() {
		return authorLastName;
	}
	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
