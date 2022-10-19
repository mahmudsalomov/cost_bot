package uz.maniac4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.maniac4j.model.Item;
import uz.maniac4j.model.Request;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findAllByRequest(Request request);
}
