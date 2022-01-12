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
import org.br.foodjet.resource.common.Item;
import org.br.foodjet.resource.common.OrderStatus;
import org.br.foodjet.resource.mapper.OrderMapper;
import org.br.foodjet.resource.request.OrderRequest;
import org.br.foodjet.resource.response.ErrorDetailTO;
import org.br.foodjet.resource.response.OrderResponse;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    public List<OrderResponse> listAllOrder() {
        List<OrderRequest> listAll = OrderRequest.findAll().list();
        return orderMapper.toResponseList(listAll);
    }

    public List<OrderResponse> findByName(String clientName) {
        if (clientName == null) {
            return null;
        }

        List<OrderRequest> listName = OrderRequest.find("client_name", clientName).list();
        if (listName.size() == 0) {
            throw new BusinessException("Resources not found");
        }

        return orderMapper.toResponseList(listName);
    }

    public OrderResponse findById(Long id) {
        if (id == null) {
            return null;
        }

        OrderRequest order = OrderRequest.findById(id);
        if (order == null) {
            throw new BusinessException("Resource not found");
        }
        return orderMapper.toResponse(order);
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
            order.persist();
            persistItems(order.items);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return orderMapper.toResponse(order);
    }

    public OrderResponse updateOrder(OrderStatus status, Long id) {
        Instant dateNow = Instant.now();
        if (status == null || id == null) {
            return null;
        }

        OrderRequest order = OrderRequest.findById(id);
        if (order == null) {
            throw new BusinessException("Resource not found");
        }

        order.setStatus(OrderStatus.FINALIZED);
        order.setLastUpdateDate(dateNow.toString());
        order.persist();

        return orderMapper.toResponse(order);
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

    private void persistItems(List<Item> items) {
        try {
            if (items == null) {
                return;
            }

            for (Item item : items) {
                item.persist();
            }
        }catch (Exception ex){
//            handleHttpResponse();
        }
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
