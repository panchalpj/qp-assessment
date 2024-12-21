package co.questionpro.model.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItemDTO {

  private Long id;
  private String name;
  private Double price;
  private Integer inventory;
  private Date modifiedTime;
}
