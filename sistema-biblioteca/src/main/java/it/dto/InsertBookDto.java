package it.dto;

import jakarta.annotation.Nonnull;

public class InsertBookDto {
	@Nonnull
	private String title;
	@Nonnull
	private String isbn;
	@Nonnull
	private String authorName;
	@Nonnull
	private String authorLastName;
	@Nonnull
	private String categoryName;
	@Nonnull
	private String publisherName;
	@Nonnull
	private String email;
	
	public InsertBookDto(String title, String isbn, String authorName, String authorLastName, String categoryName,
			String publisherName, String email) {
		
		this.title = title;
		this.isbn = isbn;
		this.authorName = authorName;
		this.authorLastName = authorLastName;
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
