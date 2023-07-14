package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.jta.WebSphereUowTransactionManager;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        // 중복 아니면 저장
        if(item.getId() == null) {
            em.persist(item);
        } else {
            // id있으면 merge(업데이트와 흡사)
            Item merge = em.merge(item); // merge가 영속성 컨텍스트에서 관리되는 객체이고, item은 영속성 상태로 변하지 않는다
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // JPQL 써야 함
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
