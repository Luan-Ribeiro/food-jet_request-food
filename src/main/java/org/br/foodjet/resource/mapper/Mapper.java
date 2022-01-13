package org.br.foodjet.resource.mapper;

import java.util.List;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;

@org.mapstruct.Mapper
public interface Mapper {

   OrderResponse toResponse(OrderRequest orderRequest);

   List<OrderResponse> toResponseList(List<OrderRequest> orderRequests);
}
