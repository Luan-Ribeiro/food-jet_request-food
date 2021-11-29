package org.br.foodjet.resource.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.br.foodjet.resource.request.OrderRequest;
import org.br.foodjet.resource.response.OrderResponse;

@ApplicationScoped
public class OrderMapperImpl implements OrderMapper {

    @Override
    public List<OrderResponse> toResponse(List<OrderRequest> orderRequests) {
        if (orderRequests == null) {
            return null;
        }

        List<OrderResponse> list = new ArrayList<OrderResponse>(orderRequests.size());
        for (OrderRequest orderRequest : orderRequests) {
            list.add(orderRequestToOrderResponse(orderRequest));
        }

        return list;
    }

    private OrderResponse orderRequestToOrderResponse(OrderRequest orderRequest) {
        if (orderRequest == null) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(orderRequest.id);
        orderResponse.setNameFood(orderRequest.nameFood);
        orderResponse.setClientName(orderRequest.clientName);
        orderResponse.setValue(orderRequest.value);
        orderResponse.setCreateDate(orderRequest.createDate);
        return orderResponse;
    }
}
