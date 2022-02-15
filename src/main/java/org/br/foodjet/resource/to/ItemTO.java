package org.br.foodjet.resource.to;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import org.br.foodjet.constant.NameFoodEnum;

@Data
@RegisterForReflection
public class ItemTO {

    @Enumerated(EnumType.STRING)
    private NameFoodEnum nameFoodEnum;

    private Long quantity;
}
