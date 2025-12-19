package com.ecommerce.project.DTO;

import java.util.List;

// UserResponse - data transfer object for paginated user list response
public class UserResponse {

	// List of users on current page
	private List<UserDTO> content;
	// Current page number (0-indexed)
	private Integer pageNumber;
	// Number of items per page
	private Integer pageSize;
	// Total number of users across all pages
	private Long totalElements;
	// Total number of pages
	private Integer totalPages;
	// Whether this is the last page
	private boolean lastPage;

	// Default constructor
	public UserResponse() {
	}

	// Constructor with all fields
	public UserResponse(List<UserDTO> content, Integer pageNumber, Integer pageSize, Long totalElements, Integer totalPages, boolean lastPage) {
		this.content = content;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.lastPage = lastPage;
	}

	// Get user list
	public List<UserDTO> getContent() {
		return content;
	}

	// Set user list
	public void setContent(List<UserDTO> content) {
		this.content = content;
	}

	// Get page number
	public Integer getPageNumber() {
		return pageNumber;
	}

	// Set page number
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	// Get page size
	public Integer getPageSize() {
		return pageSize;
	}

	// Set page size
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	// Get total elements count
	public Long getTotalElements() {
		return totalElements;
	}

	// Set total elements count
	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	// Get total pages count
	public Integer getTotalPages() {
		return totalPages;
	}

	// Set total pages count
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	// Check if this is the last page
	public boolean isLastPage() {
		return lastPage;
	}

	// Set last page flag
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

}
