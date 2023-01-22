package ch.heig.items.api.repositories;

import ch.heig.items.api.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    ItemEntity findById(int id);
    List<ItemEntity> findByNameLike(String pattern);
//    ItemEntity update
}