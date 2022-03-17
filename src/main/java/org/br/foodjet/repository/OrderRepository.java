package org.br.foodjet.repository;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.Item;
import org.br.foodjet.repository.entity.OrderRequest;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@Transactional
public class OrderRepository {

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

    public void saveOrder(OrderRequest order) {
        log.info("Save Order : {}", order);
        order.persist();
    }

    public void saveItemsOrder(List<Item> items) {
        if (items == null) {
            return;
        }

        items.forEach(item -> item.persist());
    }
}
