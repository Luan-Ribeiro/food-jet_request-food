package org.br.foodjet.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.conf.Constants;
import org.br.foodjet.resource.request.OrderRequest;
import org.br.foodjet.resource.mapper.OrderMapper;
import org.br.foodjet.resource.request.NameFood;
import org.br.foodjet.resource.response.OrderResponse;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

    @Inject OrderMapper orderMapper;

    public List<OrderResponse> listAllOrder(){
        List<OrderRequest> listAll = OrderRequest.findAll().list();
        return listAll.stream().map(find -> {
            return new OrderResponse(find.id,find.nameFood,find.clientName,find.value,find.createDate);
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<OrderResponse> createOrder(List<OrderRequest> items) {
        Instant dateNow = Instant.now();
        try {
            for(OrderRequest item  : items) {
                item.nameFood = NameFood.valueOf(item.nameFood.toString());
                item.createDate = dateNow.toString();
                item.value = valueToOrder(item);

                item.persist();
            }
        }catch(Exception ex){
            log.info(ex.getMessage());
        }

        return orderMapper.toResponse(items);
    }

    private double valueToOrder(OrderRequest item){
        switch (item.nameFood){
            case CHESSE_COMPLETED:
                return Constants.CHESSE_COMPLETED;
            case CHESSE_SALAD:
                return Constants.CHESSE_SALAD;
            case CHESSE_BACON:
                return Constants.CHESSE_BACON;
            default:
                return 0;
        }
    }
}
