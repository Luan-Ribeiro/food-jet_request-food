package org.br.foodjet.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.OrderRequest;
import org.br.foodjet.resource.common.Item;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class Repository {

    public List<OrderRequest> listAll() {
        return OrderRequest.findAll().list();
    }


    public List<OrderRequest> findByName(String clientName) {
        log.info("Loading Order Name: {}", clientName);
        return OrderRequest.find("client_name", clientName).list();
    }

    public OrderRequest findById(Long id) {
        log.info("Loading Order ID: {}", id);
        return OrderRequest.findById(id);
    }

    public void save(OrderRequest order) {
        log.info("Save Order : {}", order);
        order.persistAndFlush();
        persistItems(order.items);
    }

    public void update(OrderRequest order) {
        log.info("Update Order : {}", order);
        order.persist();
    }

    private void persistItems(List<Item> items) {
        try {
            if (items == null) {
                return;
            }

            for (Item item : items) {
                item.persistAndFlush();
            }
        } catch (Exception ex) {
//            handleHttpResponse();
        }
    }
}
