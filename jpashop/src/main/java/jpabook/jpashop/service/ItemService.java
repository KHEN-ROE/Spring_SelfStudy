package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId); // 영속상태
        findItem.setName(name); // 세터 지양하는 것이 좋다
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity); // itemRepository.save 없어도 트랜잭셔널에 의해 커밋된다.
                                                                // 그러면 jpa는 flush()를 날린다. 즉 영속성 컨텍스트 내에서 변경된 값을 다 찾는다. 이게 변경감지
    }


}
