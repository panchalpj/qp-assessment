package co.questionpro.services;

import co.questionpro.exception.GroceryItemException;
import co.questionpro.model.dto.GroceryItemDTO;
import co.questionpro.model.dto.PaginationRequest;
import co.questionpro.model.entity.GroceryItem;
import co.questionpro.repository.GroceryItemRepository;
import co.questionpro.transformer.GroceryTransformer;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

  private final GroceryItemRepository groceryItemRepository;
  private final GroceryTransformer groceryTransformer;

  @Autowired
  public AdminServiceImpl(
      GroceryItemRepository groceryItemRepository, GroceryTransformer groceryTransformer) {
    this.groceryItemRepository = groceryItemRepository;
    this.groceryTransformer = groceryTransformer;
  }

  @Override
  public GroceryItemDTO addGroceryItem(GroceryItemDTO groceryItemDTO) {
    logger.info("Attempting to add a new grocery item: {}", groceryItemDTO);
    try {
      GroceryItem groceryItem = groceryTransformer.toEntity(groceryItemDTO);
      GroceryItem savedItem = groceryItemRepository.save(groceryItem);
      logger.info("Grocery item added successfully with ID: {}", savedItem.getId());
      return groceryTransformer.toDTO(savedItem);
    } catch (Exception e) {
      logger.error("Error adding grocery item: {}", e.getMessage(), e);
      throw new GroceryItemException("Failed to add grocery item. Please try again later.");
    }
  }

  @Override
  public List<GroceryItem> getAllGroceryItems() {
    logger.info("Fetching all grocery items.");
    try {
      List<GroceryItem> items = groceryItemRepository.findAll();
      logger.info("Found {} grocery items.", items.size());
      return items;
    } catch (Exception e) {
      logger.error("Error fetching grocery items: {}", e.getMessage(), e);
      throw new GroceryItemException("Failed to fetch grocery items. Please try again later.");
    }
  }

  @Override
  public GroceryItemDTO updateGroceryItem(Long id, GroceryItemDTO groceryItemDTO) {
    logger.info("Attempting to update grocery item with ID: {}", id);
    try {
      Optional<GroceryItem> existingGroceryItemOpt = groceryItemRepository.findById(id);

      if (existingGroceryItemOpt.isEmpty()) {
        logger.warn("Grocery item not found with ID {}.", id);
        throw new GroceryItemException("Grocery item not found with ID: " + id);
      }

      GroceryItem existingGroceryItem = existingGroceryItemOpt.get();
      GroceryItem groceryItem = groceryTransformer.toEntity(groceryItemDTO);
      groceryItem.setId(existingGroceryItem.getId());

      GroceryItem updatedItem = groceryItemRepository.save(groceryItem);
      logger.info("Grocery item updated successfully with ID: {}", updatedItem.getId());
      return groceryTransformer.toDTO(updatedItem);
    } catch (GroceryItemException e) {
      throw e;
    } catch (Exception e) {
      logger.error("Error updating grocery item with ID {}: {}", id, e.getMessage(), e);
      throw new GroceryItemException("Failed to update grocery item. Please try again later.");
    }
  }

  @Override
  public void deleteGroceryItem(Long id) {
    logger.info("Attempting to delete grocery item with ID: {}", id);
    try {
      if (!groceryItemRepository.existsById(id)) {
        logger.warn("Grocery item with ID {} not found.", id);
        throw new GroceryItemException("Grocery item not found with ID: " + id);
      }

      groceryItemRepository.deleteById(id);
      logger.info("Grocery item deleted successfully with ID: {}", id);
    } catch (GroceryItemException e) {
      throw e;
    } catch (Exception e) {
      logger.error("Error deleting grocery item with ID {}: {}", id, e.getMessage(), e);
      throw new GroceryItemException("Failed to delete grocery item.");
    }
  }

  @Override
  public List<GroceryItemDTO> getPaginatedGroceryItems(PaginationRequest paginationRequest) {
    logger.info("Fetching grocery items with pagination.");
    try {
      Sort sort = Sort.by(Sort.Order.asc(paginationRequest.getSortField()));
      if ("desc".equalsIgnoreCase(paginationRequest.getSortDirection())) {
        sort = Sort.by(Sort.Order.desc(paginationRequest.getSortField()));
      }

      Pageable pageable =
          PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), sort);

      Page<GroceryItem> items = groceryItemRepository.findAll(pageable);
      logger.info(
          "Found {} grocery items on page {}.",
          items.getContent().size(),
          paginationRequest.getPage());

      return getList(items.getContent());
    } catch (Exception e) {
      logger.error("Error fetching paginated grocery items: {}", e.getMessage(), e);
      throw new GroceryItemException("Failed to fetch grocery items. Please try again later.");
    }
  }

  List<GroceryItemDTO> getList(List<GroceryItem> content){
      return content.stream().map(groceryTransformer::toDTO).toList();
  }
}
