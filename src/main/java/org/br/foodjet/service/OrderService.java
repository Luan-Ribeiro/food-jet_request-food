package org.br.foodjet.service;

import java.time.Instant;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.constant.OrderStatusEnum;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.integration.InventoryResource;
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
    private final InventoryResource inventoryResource;

    public List<OrderResponse> listAllOrder() {
        return orderMapper.toResponseList(orderRepository.listAll());
    }

    public List<OrderResponse> findByName(String clientName) {
        if (clientName == null) {
            return null;
        }

        List<OrderRequest> listName = orderRepository.findByName(clientName);
        if (listName.size() == 0) {
            throw new BusinessException("Resources not found");
        }

        return orderMapper.toResponseList(listName);
    }

    public OrderResponse findById(Long id) {
        if (id == null) {
            return null;
        }

        OrderRequest order = orderRepository.findById(id);
        if (order == null) {
            throw new BusinessException("OrderResource not found");
        }
        return orderMapper.requestToResponse(order);
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest order) {
        if (order == null) {
            return null;
        }

        Instant dateNow = Instant.now();
        OrderResponseTO orderVerified = inventoryResource.verifyOrderAndFlushIngredients(
            orderMapper.toRequestTO(order));
        OrderStatusEnum orderStatus = orderVerified.getStatus();
        if (orderStatus.equals(OrderStatusEnum.RECUSED)) {
            throw new BusinessException("Order was refused by lack of ingredients");
        }

        var valueOrder = orderVerified.getValueTotal();

        order.setCreateDate(dateNow.toString());
        order.setLastUpdateDate(dateNow.toString());
        order.setValue(valueOrder);
        order.setStatus(orderStatus);
        orderRepository.saveOrder(order);
        orderRepository.saveItemsOrder(order.getItems());

        return orderMapper.requestToResponse(order);
    }
}
