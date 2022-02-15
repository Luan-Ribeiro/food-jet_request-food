package org.br.foodjet.resource.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.foodjet.constant.OrderStatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequestUpdate {

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;
}
