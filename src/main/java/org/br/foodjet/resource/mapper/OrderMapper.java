package org.br.foodjet.resource.mapper;

import java.util.List;
import org.br.foodjet.resource.request.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface OrderMapper {

   OrderResponse toResponse(OrderRequest orderRequest);

   List<OrderResponse> toResponseList(List<OrderRequest> orderRequests);
}
