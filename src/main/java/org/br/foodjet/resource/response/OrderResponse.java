package org.br.foodjet.resource.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.foodjet.constant.OrderStatusEnum;
import org.br.foodjet.repository.entity.Item;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private List<Item> items;
    private String clientName;
    private BigDecimal value;
    private OrderStatusEnum status;
    private String createDate;
    private String lastUpdateDate;
}
