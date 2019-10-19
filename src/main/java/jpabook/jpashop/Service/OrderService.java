package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.ItemRepository;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     *  주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        /*
        // Order에 배송정보와 주문상품이 cascade = CascadeType.ALL로 되어 있어서 order 하나만 저장해도 다 저장된다.
        // CascadeType를 어디까지 써야 하는지 고민이 있다.
        // 현재 Order에서 Delivery와 OrderItems를 참조하는 상황이고 다른 곳에서 Delivery, OrderItems를 참조하는 곳은 없다.
        // 다른 엔티티에서도 Delivery와 OrderItems를 참조하는 상황이면 CascadeType를 쓰지 말고 별도의 리파지토리를 만들어서
        // persist해야한다.
         */
        orderRepository.save(order);
        return order.getId();
    }

    /**
     *  주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     *  검색
     */
    /*public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.finAll(orderSearch);
    }*/
}
