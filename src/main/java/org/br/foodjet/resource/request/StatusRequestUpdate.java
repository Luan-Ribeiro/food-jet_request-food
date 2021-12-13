package org.br.foodjet.resource.request;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.br.foodjet.resource.common.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequestUpdate {

    @Enumerated(EnumType.STRING)
    public OrderStatus status;
}
