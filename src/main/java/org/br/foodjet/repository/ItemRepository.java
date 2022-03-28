package org.br.foodjet.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.br.foodjet.repository.entity.Item;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@Transactional
public class ItemRepository implements PanacheRepository<Item> {

    public void save(Item item) {
        log.info("Save Item : {}", item);
        persist(item);
    }
}
