package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.List;
import lombok.Data;
import org.br.foodjet.constant.OrderStatusEnum;

@Data
@RegisterForReflection
public class OrderRequestTO {

    private OrderStatusEnum status;

    private List<ItemTO> items;
}
