package org.br.foodjet.service;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.constant.OrderStatusEnum;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.integration.InventoryResource;
import org.br.foodjet.repository.ItemRepository;
import org.br.foodjet.repository.OrderRepository;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.resource.mapper.OrderMapper;
import org.br.foodjet.resource.response.OrderResponse;
import org.br.foodjet.resource.response.OrderResponseTO;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final InventoryResource inventoryResource;

    public List<OrderResponse> listAllOrder() {
        return orderMapper.toResponseList(orderRepository.listAll());
    }

    public List<OrderResponse> findByName(String clientName) {
        List<OrderRequest> listName = orderRepository.findByName(clientName);
        if (listName.isEmpty()) {
            throw new BusinessException("Resources not found");
        }

        return orderMapper.toResponseList(listName);
    }

    public OrderResponse findById(Long id) {

        OrderRequest order = orderRepository.findById(id);
        if (Objects.isNull(order)) {
            throw new BusinessException("Order resource not found");
        }
        return orderMapper.requestToResponse(order);
    }

    public Response createOrder(OrderRequest order) {
        Instant dateNow = Instant.now();

        OrderResponseTO orderVerified = inventoryResource.verifyOrderAndFlushIngredients(
            orderMapper.toRequestTO(order));

        OrderStatusEnum orderStatus = orderVerified.getStatus();
        if (orderStatus.equals(OrderStatusEnum.RECUSED)) {
            OrderResponse orderResponse = OrderResponse.builder()
                .clientName(order.getClientName())
                .items(order.getItems())
                .status(OrderStatusEnum.RECUSED)
                .reason(orderVerified.getReason())
                .build();
            return Response.status(Status.BAD_REQUEST).entity(orderResponse).build();
        }

        var valueOrder = orderVerified.getValueTotal();

        order.setCreateDate(dateNow.toString());
        order.setValue(valueOrder);
        order.setStatus(orderStatus);
        orderRepository.save(order);
        order.getItems().forEach(itemRepository::save);

        OrderResponse orderResponse = orderMapper.requestToResponse(order);

        return Response.created(URI.create("/requestfood" + orderResponse.getId())).entity(orderResponse).build();
    }
}
