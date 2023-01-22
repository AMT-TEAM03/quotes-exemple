package ch.heig.items.api.endpoints;

import ch.heig.items.api.entities.ItemEntity;
import ch.heig.items.api.entities.SoundEntity;
import ch.heig.items.api.exceptions.ItemNotFoundException;
import ch.heig.items.api.repositories.ItemRepository;
import org.openapitools.api.ItemsApi;
import org.openapitools.model.Item;
import org.openapitools.model.Sound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemsEndPoint implements ItemsApi {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ResponseEntity<List<Item>> getItems(){
        List<ItemEntity> itemsEntities = itemRepository.findAll();
        List<Item> items = new ArrayList<>();
        for(ItemEntity itemEntity : itemsEntities){
            items.add(itemEntity.toItem());
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addItem(@RequestBody Item item){
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName(item.getName());
        SoundEntity soundFrotte = new SoundEntity();
        soundFrotte.setId(item.getSoundFrotte().getId());
        soundFrotte.setSound(item.getSoundFrotte().getSound());
        itemEntity.setSoundFrotte(soundFrotte);
        List<SoundEntity> soundsTape = new ArrayList<>();
        for(Sound sound : item.getSoundsTape()){
            SoundEntity tmpSound = new SoundEntity();
            tmpSound.setId(sound.getId());
            tmpSound.setSound(sound.getSound());
            soundsTape.add(tmpSound);
        }
        itemEntity.setSoundsTape(soundsTape);
        List<SoundEntity> soundsTombe = new ArrayList<>();
        for(Sound sound : item.getSoundsTombe()){
            SoundEntity tmpSound = new SoundEntity();
            tmpSound.setId(sound.getId());
            tmpSound.setSound(sound.getSound());
            soundsTombe.add(tmpSound);
        }
        itemEntity.setSoundsTombe(soundsTombe);
        ItemEntity itemAdded = itemRepository.save(itemEntity);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itemAdded.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Item> getItem(Integer id) {
        Optional<ItemEntity> opt = itemRepository.findById(id);
        if (opt.isPresent()) {
            ItemEntity itemEntity = opt.get();
            Item item = itemEntity.toItem();
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        } else {
            throw new ItemNotFoundException(id);
        }
    }
}
