package org.br.foodjet.resource.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.foodjet.repository.entity.Item;
import org.br.foodjet.constant.OrderStatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private List<Item> items;
    private String clientName;
    private double value;
    private OrderStatusEnum status;
    private String unity;
    private String createDate;
    private String lastUpdateDate;
}
