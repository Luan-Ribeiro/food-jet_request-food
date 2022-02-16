package org.br.foodjet.service;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.constant.OrderStatusEnum;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.exception.GenericException;
import org.br.foodjet.integration.BurguerResource;
import org.br.foodjet.integration.InventoryResource;
import org.br.foodjet.repository.Repository;
import org.br.foodjet.repository.entity.Item;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.resource.mapper.Mapper;
import org.br.foodjet.resource.response.ErrorDetailTO;
import org.br.foodjet.resource.response.OrderResponse;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class Service {

    private final Mapper mapper;
    private final Repository repository;
    private final InventoryResource inventoryResource;
    private final BurguerResource burguerResource;
    Map<String, Double> resultsOnCache = new HashMap<>();

    public List<OrderResponse> listAllOrder() {
        return mapper.toResponseList(repository.listAll());
    }

    public List<OrderResponse> findByName(String clientName) {
        if (clientName == null) {
            return null;
        }

        List<OrderRequest> listName = repository.findByName(clientName);
        if (listName.size() == 0) {
            throw new BusinessException("Resources not found");
        }

        return mapper.toResponseList(listName);
    }

    public OrderResponse findById(Long id) {
        if (id == null) {
            return null;
        }

        OrderRequest order = repository.findById(id);
        if (order == null) {
            throw new BusinessException("OrderResource not found");
        }
        return mapper.requestToResponse(order);
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest order) {
        if (order == null) {
            return null;
        }

        Instant dateNow = Instant.now();
        OrderStatusEnum statusVerified = inventoryResource.verifyOrderAndFlushIngredients(mapper.toRequestTO(order))
            .getStatus();
        if (Objects.isNull(statusVerified) || statusVerified.equals(OrderStatusEnum.RECUSED)) {
            throw new BusinessException("Order was refused by lack of ingredients");
        }

        order.setCreateDate(dateNow.toString());
        order.setLastUpdateDate(dateNow.toString());
        order.setValue(calculateValueToOrder(order.getItems()));
        order.setStatus(statusVerified);
        repository.save(order);

        return mapper.requestToResponse(order);
    }

    private double calculateValueToOrder(List<Item> items) {
        if (items == null) {
            return 0;
        }

        double valueFinal = 0.0;

        for (Item item : items) {
            Double valueBurguer;
            var nameBurguer = item.getNameFood();

            if (resultsOnCache.isEmpty() || Objects.isNull(resultsOnCache.get(nameBurguer))) {
                valueBurguer =
                    Objects.nonNull(burguerResource.findByName(nameBurguer)) ? burguerResource.findByName(nameBurguer)
                        .getValue() : null;
                if (Objects.isNull(valueBurguer)) {
                    throw new BusinessException("Burguer name not found: {}", nameBurguer);
                }
                resultsOnCache.put(nameBurguer, valueBurguer);
            } else {
                valueBurguer = resultsOnCache.get(nameBurguer);
            }

            valueFinal = valueFinal + (valueBurguer * (item.getQuantity()));
        }
        return valueFinal;
    }

    private void handleHttpResponse(HttpResponse<Buffer> response, String path) {
        if (response.statusCode() != 200) {
            var errorDetailTO = response.bodyAsJson(ErrorDetailTO.class);

            throw new GenericException(
                "Integration failed with inventoryfood for " + path,
                errorDetailTO
            );
        }
    }

    //    @Transactional
//    public OrderResponse updateOrder(OrderStatusEnum status, Long id) {
//        Instant dateNow = Instant.now();
//        if (status == null || id == null) {
//            return null;
//        }
//
//        OrderRequest order = OrderRequest.findById(id);
//        if (order == null) {
//            throw new BusinessException("OrderResource not found");
//        }
//
//        order.setStatus(OrderStatusEnum.FINALIZED);
//        order.setLastUpdateDate(dateNow.toString());
//        repository.update(order);
//
//        return mapper.requestToResponse(order);
//    }
}
