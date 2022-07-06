package org.br.foodjet.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.OrderRequest;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class OrderRepository implements PanacheRepository<OrderRequest> {

    public List<OrderRequest> listAll() {
        return findAll().list();
    }

    public List<OrderRequest> findByName(String clientName) {
        log.info("Loading Order Name: {}", clientName);
        return find("client_name", clientName).list();
    }

    public OrderRequest findById(Long id) {
        log.info("Loading Order ID: {}", id);
        return findById(id);
    }

    public void save(OrderRequest order) {
        log.info("Save Order : {}", order);
        persist(order);
    }
}
