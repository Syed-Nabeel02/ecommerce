package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.DAO.CategoryDAO;
import com.ecommerce.project.DTO.CategoryDto;
import com.ecommerce.project.DTO.CategoryResponse;
import com.ecommerce.project.errorHandler.APIErrorHandler;
import com.ecommerce.project.errorHandler.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.Interface.ICategoryService;

/**
 * Service implementation for category operations
 * Business logic: Manages product categories with validation for unique names
 */
@Service
public class ICategoryServiceImpl implements ICategoryService {

    private final CategoryDAO categoryDAO;
    private final ModelMapper objectMapper;

    public ICategoryServiceImpl(CategoryDAO categoryDAO, ModelMapper objectMapper) {
        this.categoryDAO = categoryDAO;
        this.objectMapper = objectMapper;
    }

    // Get all categories with pagination and sorting
    @Override
    public CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortingCriteria = buildSortCriteria(sortBy, sortOrder);
        Pageable paginationDetails = PageRequest.of(pageNumber, pageSize, sortingCriteria);
        Page<Category> paginatedCategories = categoryDAO.findAll(paginationDetails);

        List<Category> categoriesList = paginatedCategories.getContent();
        validateCategoriesExist(categoriesList);

        List<CategoryDto> categoryDataList = transformCategoriesToDTO(categoriesList);
        return buildCategoryResponse(paginatedCategories, categoryDataList);
    }

    // Create new category (validates name is unique)
    @Override
    public CategoryDto createCategory(CategoryDto categoryDTO) {
        Category newCategoryEntity = convertDTOToEntity(categoryDTO);
        validateCategoryNameNotExists(newCategoryEntity.getCategoryName());

        Category persistedCategory = categoryDAO.save(newCategoryEntity);
        return convertEntityToDTO(persistedCategory);
    }

    // Delete a category by ID
    @Override
    public CategoryDto removeCategory(Long categoryId) {
        Category categoryToDelete = fetchCategoryOrThrowException(categoryId);
        categoryDAO.delete(categoryToDelete);
        return convertEntityToDTO(categoryToDelete);
    }

    // Update existing category details
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDTO, Long categoryId) {
        Category existingCategory = fetchCategoryOrThrowException(categoryId);
        Category updatedCategoryData = convertDTOToEntity(categoryDTO);
        updatedCategoryData.setCategoryId(categoryId);

        Category modifiedCategory = categoryDAO.save(updatedCategoryData);
        return convertEntityToDTO(modifiedCategory);
    }

    private Sort buildSortCriteria(String sortBy, String sortOrder) {
        return sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }

    private void validateCategoriesExist(List<Category> categoriesList) {
        if (categoriesList.isEmpty()) {
            throw new APIErrorHandler("No categories have been created yet");
        }
    }

    private List<CategoryDto> transformCategoriesToDTO(List<Category> categories) {
        return categories.stream()
                .map(this::convertEntityToDTO)
                .toList();
    }

    private CategoryResponse buildCategoryResponse(Page<Category> paginatedCategories, List<CategoryDto> categoryDataList) {
        CategoryResponse responsePayload = new CategoryResponse();
        responsePayload.setContent(categoryDataList);
        responsePayload.setPageNumber(paginatedCategories.getNumber());
        responsePayload.setPageSize(paginatedCategories.getSize());
        responsePayload.setTotalElements(paginatedCategories.getTotalElements());
        responsePayload.setTotalPages(paginatedCategories.getTotalPages());
        responsePayload.setLastPage(paginatedCategories.isLast());
        return responsePayload;
    }

    private Category convertDTOToEntity(CategoryDto categoryDTO) {
        return objectMapper.map(categoryDTO, Category.class);
    }

    private CategoryDto convertEntityToDTO(Category category) {
        return objectMapper.map(category, CategoryDto.class);
    }

    private void validateCategoryNameNotExists(String categoryName) {
        Category existingCategoryInDb = categoryDAO.findByCategoryName(categoryName);
        if (existingCategoryInDb != null) {
            throw new APIErrorHandler("A category named " + categoryName + " already exists");
        }
    }

    private Category fetchCategoryOrThrowException(Long categoryId) {
        return categoryDAO.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    }
}
