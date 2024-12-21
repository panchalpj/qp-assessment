package co.questionpro.transformer;

import co.questionpro.model.dto.GroceryItemDTO;
import co.questionpro.model.entity.GroceryItem;
import org.springframework.stereotype.Service;

@Service("groceryTransformer")
public class GroceryTransformer implements Transformer<GroceryItemDTO, GroceryItem> {

  @Override
  public GroceryItem toEntity(GroceryItemDTO groceryItemDTO) {
    if (groceryItemDTO == null) {
      return null;
    }

    GroceryItem groceryItem = new GroceryItem();
    groceryItem.setId(groceryItemDTO.getId());
    groceryItem.setName(groceryItemDTO.getName());
    groceryItem.setPrice(groceryItemDTO.getPrice());
    groceryItem.setInventory(groceryItemDTO.getInventory());
    return groceryItem;
  }

  @Override
  public GroceryItemDTO toDTO(GroceryItem groceryItem) {
    if (groceryItem == null) {
      return null;
    }

    return new GroceryItemDTO(
        groceryItem.getId(),
        groceryItem.getName(),
        groceryItem.getPrice(),
        groceryItem.getInventory(),
        groceryItem.getModifiedTime());
  }
}
