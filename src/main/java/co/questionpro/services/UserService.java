package co.questionpro.services;

import co.questionpro.model.dto.OrderDTO;
import co.questionpro.model.dto.OrderRequest;
import java.util.List;

public interface UserService {
  List<OrderDTO> getOrdersByUser(Long userId);

  OrderDTO placeOrder(OrderRequest request);
}
