package org.br.foodjet.resource.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.br.foodjet.resource.request.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;

@ApplicationScoped
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponse toResponse(OrderRequest orderRequest) {
        if (orderRequest == null) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderRequest.id);
        orderResponse.setItems(orderRequest.items);
        orderResponse.setClientName(orderRequest.clientName);
        orderResponse.setValue(orderRequest.value);
        orderResponse.setStatus(orderRequest.status);
        orderResponse.setCreateDate(orderRequest.createDate);
        orderResponse.setLastUpdateDate(orderRequest.lastUpdateDate);
        return orderResponse;
    }

    @Override
    public List<OrderResponse> toResponseList(List<OrderRequest> orderRequests) {
        if (orderRequests == null) {
            return null;
        }

        int i = 0;
        List<OrderResponse> orderResponse = new ArrayList<>();
        for (OrderRequest orderRequest : orderRequests) {
            orderResponse.add(i, toResponse(orderRequest));
            i++;
        }

        return orderResponse;
    }
}
