package ch.heig.items.api.services;

import ch.heig.items.api.entities.SoundEntity;
import org.openapitools.model.Sound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.heig.items.api.repositories.SoundRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoundsService {
    @Autowired
    private SoundRepository soundRepository;

    public List<Sound> getAll() {
        List<SoundEntity> allSounds = soundRepository.findAll();
        List<Sound> result= new ArrayList<>();
        for(SoundEntity soundEntity : allSounds){
            result.add(soundEntity.toSound());
        }
        return result;
    }

}
