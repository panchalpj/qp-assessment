package co.questionpro.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {
  private int page;
  private int size;
  private String sortField;
  private String sortDirection;
}
