package org.br.foodjet.resource.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.foodjet.resource.common.Item;
import org.br.foodjet.resource.common.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    public Long id;
    public List<Item> items;
    public String clientName;
    public double value;
    public OrderStatus status;
    public String createDate;
    public String lastUpdateDate;
}
