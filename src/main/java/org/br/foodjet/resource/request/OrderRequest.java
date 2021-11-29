package org.br.foodjet.resource.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_request")
public class OrderRequest extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    public Long id;

    @Enumerated(EnumType.STRING)
    public NameFood nameFood;

    public String clientName;

    @JsonIgnore
    public double value;

    @JsonIgnore
    public String createDate;
}
