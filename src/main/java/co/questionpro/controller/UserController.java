package co.questionpro.controller;

import co.questionpro.model.dto.OrderDTO;
import co.questionpro.model.dto.OrderRequest;
import co.questionpro.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/order")
  public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderRequest request) {
    return ResponseEntity.ok(userService.placeOrder(request));
  }

  @GetMapping("/find-order/{id}")
  public ResponseEntity<List<OrderDTO>> findOrder(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getOrdersByUser(id));
  }
}
