package co.questionpro.services;

import co.questionpro.model.dto.OrderDTO;
import co.questionpro.model.dto.OrderRequest;
import co.questionpro.model.dto.OrderedItemsDTO;
import co.questionpro.model.entity.GroceryItem;
import co.questionpro.model.entity.Order;
import co.questionpro.model.entity.OrderItem;
import co.questionpro.model.entity.User;
import co.questionpro.repository.GroceryItemRepository;
import co.questionpro.repository.OrderItemRepository;
import co.questionpro.repository.OrderRepository;
import co.questionpro.repository.UserRepository;
import co.questionpro.transformer.GroceryTransformer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final GroceryItemRepository groceryItemRepository;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(
      GroceryItemRepository groceryItemRepository,
      OrderRepository orderRepository,
      OrderItemRepository orderItemRepository,
      GroceryTransformer groceryTransformer,
      UserRepository userRepository) {
    this.groceryItemRepository = groceryItemRepository;
    this.orderRepository = orderRepository;
    this.orderItemRepository = orderItemRepository;
    this.userRepository = userRepository;
  }

  @Override
  public OrderDTO placeOrder(OrderRequest request) {
    Map<Long, Integer> items = request.getItems();
    Double totalPrice = 0.0;
    List<OrderedItemsDTO> orderedItemsDTOS = new ArrayList<>();

    // Validate and calculate total price
    for (Map.Entry<Long, Integer> entry : items.entrySet()) {
      GroceryItem item =
          groceryItemRepository
              .findById(entry.getKey())
              .orElseThrow(() -> new RuntimeException("Grocery item not found: " + entry.getKey()));

      if (item.getInventory() < entry.getValue()) {
        throw new RuntimeException("Insufficient inventory for item: " + item.getName());
      }

      totalPrice += item.getPrice() * entry.getValue();

      // Reduce inventory
      item.setInventory(item.getInventory() - entry.getValue());
      groceryItemRepository.save(item);

      // Create OrderedItemsDTO for the response
        new OrderedItemsDTO();
        OrderedItemsDTO itemDTO =
          OrderedItemsDTO.builder().name(item.getName()).price(item.getPrice()).build();
      orderedItemsDTOS.add(itemDTO);
    }

    // Retrieve or create the user
    User user =
        userRepository
            .findByEmail(request.getUserEmail())
            .orElseGet(
                () -> {
                  User newUser = new User();
                  newUser.setEmail(request.getUserEmail());
                  return userRepository.save(newUser);
                });

    // Save the order with the userId
    Order order = new Order();
    order.setUserId(user.getId()); // Save userId instead of userEmail
    order.setTotalPrice(totalPrice);

    // Persist the order
    Order savedOrder = orderRepository.save(order);

    // Save the items in the order_items table
    for (Map.Entry<Long, Integer> entry : items.entrySet()) {
        new OrderItem();
        OrderItem orderItem =
          OrderItem
              .builder()
              .orderId(savedOrder.getId())
              .itemId(entry.getKey())
              .quantity(entry.getValue())
              .build();
      orderItemRepository.save(orderItem);
    }

    // Create and return the OrderDTO
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setId(savedOrder.getId());
    orderDTO.setUserEmail(user.getEmail()); // Include the email for response
    orderDTO.setItems(items);
    orderDTO.setOrderedItemDTOS(orderedItemsDTOS);
    orderDTO.setTotalPrice(totalPrice);
    orderDTO.setModifiedTime(savedOrder.getModifiedTime());

    return orderDTO;
  }

  @Override
  public List<OrderDTO> getOrdersByUser(Long userId) {
    // Fetch all orders for the user
    List<Order> orders = orderRepository.findByUserId(userId);

    // Transform each order into an OrderDTO
    return orders.stream()
        .map(
            order -> {
              // Fetch items associated with this order
              List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

              // Create OrderedItemsDTO for each order item and calculate total price
              AtomicReference<Double> calculatedTotalPrice = new AtomicReference<>(0.0);
              List<OrderedItemsDTO> orderedItemsDTOS =
                  orderItems.stream()
                      .map(
                          orderItem -> {
                            GroceryItem groceryItem =
                                groceryItemRepository
                                    .findById(orderItem.getItemId())
                                    .orElseThrow(
                                        () ->
                                            new RuntimeException(
                                                "Grocery item not found: "
                                                    + orderItem.getItemId()));

                            // Update the total price using the item's price and quantity
                            calculatedTotalPrice.updateAndGet(
                                v -> v + (groceryItem.getPrice() * orderItem.getQuantity()));

                              new OrderedItemsDTO();
                              return OrderedItemsDTO
                                  .builder()
                                .name(groceryItem.getName())
                                .price(groceryItem.getPrice())
                                .build();
                          })
                      .collect(Collectors.toList());

              // Use the total price from the Order entity or the calculated one
              Double totalPrice =
                  order.getTotalPrice() != null
                      ? order.getTotalPrice()
                      : calculatedTotalPrice.get();

              // Create and return OrderDTO
              return new OrderDTO(
                  order.getId(),
                  null, // User email can be included if needed by fetching from the User entity
                  null, // Map<Long, Integer> items can be added if needed by iterating through
                        // orderItems
                  orderedItemsDTOS,
                  totalPrice,
                  order.getModifiedTime());
            })
        .collect(Collectors.toList());
  }
}
