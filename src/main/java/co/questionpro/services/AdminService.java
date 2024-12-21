package co.questionpro.services;

import co.questionpro.model.dto.GroceryItemDTO;
import co.questionpro.model.dto.PaginationRequest;
import co.questionpro.model.entity.GroceryItem;
import java.util.List;

public interface AdminService {
  GroceryItemDTO addGroceryItem(GroceryItemDTO item);

  List<GroceryItem> getAllGroceryItems();

  GroceryItemDTO updateGroceryItem(Long id, GroceryItemDTO item);

  void deleteGroceryItem(Long id);

  List<GroceryItemDTO> getPaginatedGroceryItems(PaginationRequest paginationRequest);
}
