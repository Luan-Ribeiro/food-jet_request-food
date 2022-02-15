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
import org.br.foodjet.constant.OrderStatusEnum;

@Data
@Entity
@Table(name = "order_request")
public class OrderRequest extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @JsonIgnore
    private double value;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private OrderStatusEnum status;

    @JsonIgnore
    @Column(name = "create_date")
    private String createDate;

    @JsonIgnore
    @Column(name = "last_update_date")
    private String lastUpdateDate;

    @ManyToMany
    @JoinColumn(name = "item_id")
    private List<Item> items;


}
