package org.br.foodjet.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.br.foodjet.constant.NameFoodEnum;

@Data
@Entity
@Table(name = "item")
public class Item extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name = "name_food")
    @Enumerated(EnumType.STRING)
    private NameFoodEnum nameFood;

    @Column(name = "quantity")
    private Long quantity;
}
