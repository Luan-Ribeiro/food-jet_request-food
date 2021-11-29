package org.br.foodjet.resource.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.br.foodjet.resource.request.NameFood;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    public Long id;
    public NameFood nameFood;
    public String clientName;
    public double value;
    public String createDate;
}
