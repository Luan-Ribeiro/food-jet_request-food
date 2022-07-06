package org.br.foodjet.resource.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.foodjet.constant.OrderStatusEnum;
import org.br.foodjet.repository.entity.Item;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    @JsonInclude(Include.NON_NULL)
    private Long id;

    @JsonInclude(Include.NON_NULL)
    private List<Item> items;

    private String clientName;

    @JsonInclude(Include.NON_NULL)
    private BigDecimal value;

    @JsonInclude(Include.NON_NULL)
    private OrderStatusEnum status;

    @JsonInclude(Include.NON_EMPTY)
    private String createDate;

    @JsonInclude(Include.NON_EMPTY)
    private String reason;
}
