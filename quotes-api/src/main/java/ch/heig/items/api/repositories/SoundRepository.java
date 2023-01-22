package ch.heig.items.api.repositories;

import ch.heig.items.api.entities.SoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoundRepository extends JpaRepository<SoundEntity, Integer> {
    SoundEntity findById(int id);
    List<SoundEntity> findBySoundLike(String pattern);
}
