package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.math.BigDecimal;
import lombok.Data;
import org.br.foodjet.constant.OrderStatusEnum;

@Data
@RegisterForReflection
public class OrderResponseTO {

    private OrderStatusEnum status;

    private BigDecimal valueTotal;

    private String reason;
}
