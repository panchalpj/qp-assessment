package co.questionpro.model.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

  private Long id;
  private String userEmail;
  private Map<Long, Integer> items;
  private List<OrderedItemsDTO> orderedItemDTOS;
  private Double totalPrice;
  private Date modifiedTime;
}
