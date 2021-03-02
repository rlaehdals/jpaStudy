package jpabook.jpashop.api;

import jpabook.jpashop.Address;
import jpabook.jpashop.Order;
import jpabook.jpashop.OrderItem;
import jpabook.jpashop.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;


    @GetMapping("/api/v1/orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach((o->o.getItem().getName()));
        }
        return all;
    }
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        return orders.stream()
                .map(o-> new OrderDto(o))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders= orderRepository.findAllWithItem();
        return orders.stream()
                .map(o-> new OrderDto(o))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders= orderRepository.findAllWithMemberDelivery(offset, limit);
        return orders.stream()
                .map(o-> new OrderDto(o))
                .collect(Collectors.toList());
    }

    @Data
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDto(Order order){
            orderId=order.getId();
            name= order.getMember().getName();
            orderDate=order.getOrderDate();
            orderStatus=order.getStatus();
            address=order.getDelivery().getAddress();
            orderItems=order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }
    }
    @Data
    static class OrderItemDto{
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName=orderItem.getItem().getName();
            orderPrice=orderItem.getOrderPrice();
            count=orderItem.getCount();
        }
    }
}
