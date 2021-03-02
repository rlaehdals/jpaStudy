package jpabook.jpashop.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 비즈니스 로직//

    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }
    public void removeStock(int quantity) throws NotEnoughStockException {
        if(stockQuantity-quantity<0){
            throw new NotEnoughStockException("더 필요해");
        }
        this.stockQuantity-=quantity;

    }
}
