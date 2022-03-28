package org.br.foodjet.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.br.foodjet.constant.OrderStatusEnum;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Entity
@Transactional
@Table(name = "order_request")
@Schema(example = """
    {
      "clientName": "Pedrinho",
      "items": [
        {
          "nameFood": "CHESSE_BURGUER",
          "quantity": 1
        },
        {
          "nameFood": "CHESSE_BAURU",
          "quantity": 1
        }
      ]
    }
    """)
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @NotBlank
    @Column(name = "client_name")
    private String clientName;

    @JsonIgnore
    private BigDecimal value;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private OrderStatusEnum status;

    @JsonIgnore
    @Column(name = "create_date")
    private String createDate;

    @Valid
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private List<Item> items;

}
