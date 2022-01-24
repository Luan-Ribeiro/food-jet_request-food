package org.br.foodjet.service;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import java.time.Instant;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.conf.Constants;
import org.br.foodjet.exception.BusinessException;
import org.br.foodjet.exception.GenericException;
import org.br.foodjet.repository.Repository;
import org.br.foodjet.resource.common.Item;
import org.br.foodjet.resource.common.OrderStatus;
import org.br.foodjet.resource.mapper.Mapper;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.resource.response.ErrorDetailTO;
import org.br.foodjet.resource.response.OrderResponse;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class Service {

    private final Mapper mapper;

    private final Repository repository;

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
        return mapper.toResponse(order);
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest order) {
        if (order == null) {
            return null;
        }

        Instant dateNow = Instant.now();
        //TODO test transactional
        try {
            //TODO Remove before have the other MS
            OrderStatus statusVerified = verifyOrderStatus(order);
            if (statusVerified == null || statusVerified == OrderStatus.RECUSED) {
                throw new BusinessException("Order was refused by lack of ingredients");
            }

            order.setCreateDate(dateNow.toString());
            order.setLastUpdateDate(dateNow.toString());
            order.setValue(calculateValueToOrder(order.items));
            order.setStatus(statusVerified);
            repository.save(order);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return mapper.toResponse(order);
    }

    @Transactional
    public OrderResponse updateOrder(OrderStatus status, Long id) {
        Instant dateNow = Instant.now();
        if (status == null || id == null) {
            return null;
        }

        OrderRequest order = OrderRequest.findById(id);
        if (order == null) {
            throw new BusinessException("OrderResource not found");
        }

        order.setStatus(OrderStatus.FINALIZED);
        order.setLastUpdateDate(dateNow.toString());
        repository.update(order);

        return mapper.toResponse(order);
    }

    private double calculateValueToOrder(List<Item> items) {
        if (items == null) {
            return 0;
        }

        double valueFinal = 0;
        for (Item item : items) {
            switch (item.nameFood) {
                case CHESSE_COMPLETED:
                    valueFinal = valueFinal + (Constants.CHESSE_COMPLETED * item.quantity);
                    break;
                case CHESSE_SALAD:
                    valueFinal = valueFinal + (Constants.CHESSE_SALAD * item.quantity);
                    break;
                case CHESSE_BACON:
                    valueFinal = valueFinal + (Constants.CHESSE_BACON * item.quantity);
                    break;
                case CHESSE_EGG:
                    valueFinal = valueFinal + (Constants.CHESSE_EGG * item.quantity);
                    break;
                case CHESSE_EGG_SALAD:
                    valueFinal = valueFinal + (Constants.CHESSE_EGG_SALAD * item.quantity);
                    break;
                case CHESSE_PEPPERONI:
                    valueFinal = valueFinal + (Constants.CHESSE_PEPPERONI * item.quantity);
                    break;
                case CHESSE_BAURU:
                    valueFinal = valueFinal + (Constants.CHESSE_BAURU * item.quantity);
                    break;
                default:
                    break;
            }
        }
        return valueFinal;
    }

    private OrderStatus verifyOrderStatus(OrderRequest order) {
        //TODO verify if call to other micro service will be here
        return OrderStatus.ACCEPTED;
    }

    private void handleHttpResponse(HttpResponse<Buffer> response, String path) {
        if (response.statusCode() != 200) {
            var errorDetailTO = response.bodyAsJson(ErrorDetailTO.class);

            throw new GenericException(
                "Integration failed with dlocalbank-pixkey for " + path,
                errorDetailTO
            );
        }
    }
}
