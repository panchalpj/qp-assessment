package co.questionpro.model.dto;

import java.util.Map;
import lombok.Data;

@Data
public class OrderRequest {

  private String userEmail;
  private Map<Long, Integer> items;
}
