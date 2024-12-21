package co.questionpro.controller;

import co.questionpro.model.dto.GroceryItemDTO;
import co.questionpro.model.dto.PaginationRequest;
import co.questionpro.model.entity.GroceryItem;
import co.questionpro.services.AdminService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

  private final AdminService adminService;
  Logger logger = LoggerFactory.getLogger(AdminController.class);

  @Autowired
  public AdminController(AdminService adminService){
      this.adminService = adminService;
  }

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("<h1>Welcome To Report Service v2!!!</h1>");
  }

  @PostMapping("/grocery")
  public ResponseEntity<GroceryItemDTO> addGroceryItem(@RequestBody GroceryItemDTO item) {
    return ResponseEntity.ok(adminService.addGroceryItem(item));
  }

  @GetMapping("/grocery")
  public ResponseEntity<List<GroceryItem>> getAllItems() {
    return ResponseEntity.ok(adminService.getAllGroceryItems());
  }

  @PutMapping("/grocery/{id}")
  public ResponseEntity<GroceryItemDTO> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItemDTO item) {
    return ResponseEntity.ok(adminService.updateGroceryItem(id, item));
  }

  @PostMapping("/grocery-items")
  public ResponseEntity<List<GroceryItemDTO>> getPaginatedGroceryItems(@RequestBody PaginationRequest paginationRequest) {
    List<GroceryItemDTO> groceryItems = adminService.getPaginatedGroceryItems(paginationRequest);
    return ResponseEntity.ok(groceryItems);
  }

  @DeleteMapping("/grocery/{id}")
  public ResponseEntity<Void> deleteGroceryItem(@PathVariable Long id) {
    adminService.deleteGroceryItem(id);
    return ResponseEntity.noContent().build();
  }
}
