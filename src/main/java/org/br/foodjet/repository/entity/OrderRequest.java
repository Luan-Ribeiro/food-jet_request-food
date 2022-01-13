package org.br.foodjet.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;
import lombok.Data;
import org.br.foodjet.resource.common.Item;
import org.br.foodjet.resource.common.OrderStatus;

@Entity
@Transactional
@Data
@Table(name = "order_request")
public class OrderRequest extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    public Long id;

    @Column(name = "client_name")
    public String clientName;

    @JsonIgnore
    public double value;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    public OrderStatus status;

    @JsonIgnore
    @Column(name = "create_date")
    public String createDate;

    @JsonIgnore
    @Column(name = "last_update_date")
    public String lastUpdateDate;

    @ManyToMany
    @JoinColumn(name = "item_id")
    public List<Item> items;


}
