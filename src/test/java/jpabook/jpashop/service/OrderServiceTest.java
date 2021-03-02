package jpabook.jpashop.service;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.item.Book;
import jpabook.jpashop.item.Item;
import jpabook.jpashop.item.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception, NotEnoughStockException {
        Member member = createMember();
        Book book = createBook("asdf", 10000,10);
        int orderCount=2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order findOrderId = orderRepository.findOne(orderId);

        Assertions.assertThat(orderId).isEqualTo(findOrderId.getId());

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book= new Book();
        book.setName(name);

        book.setPrice(price);

        book.setStockQuantity(stockQuantity);
        em.persist(book);

        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("a","a","a"));
        em.persist(member);
        return member;
    }

    @Test
    public void 주문취소() throws Exception, NotEnoughStockException {
        Member member = createMember();
        Item item = createBook("asdf", 10000,10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);
        Order order = orderRepository.findOne(orderId);

        Assertions.assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
    }
    @Test
    public void 재고수량초과() throws Exception{
        Member member = createMember();
        Item item = createBook("asdf", 10000,10);
        int orderCount=11;
    }

}