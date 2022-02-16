package org.br.foodjet.resource.mapper;

import java.util.List;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;
import org.br.foodjet.resource.to.OrderRequestTO;

@org.mapstruct.Mapper
public interface Mapper {

    OrderResponse requestToResponse(OrderRequest orderRequest);

    List<OrderResponse> toResponseList(List<OrderRequest> orderRequests);

    OrderRequestTO toRequestTO(OrderRequest orderRequest);
}
