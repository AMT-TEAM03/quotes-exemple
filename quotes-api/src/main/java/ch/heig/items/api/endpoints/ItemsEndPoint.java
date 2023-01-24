package ch.heig.items.api.endpoints;

import ch.heig.items.api.entities.ItemEntity;
import ch.heig.items.api.exceptions.InvalidArgumentException;
import ch.heig.items.api.exceptions.ItemNotFoundException;
import ch.heig.items.api.repositories.ItemRepository;
import ch.heig.items.api.repositories.SoundRepository;
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
    @Autowired
    private SoundRepository soundRepository;

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
        ItemEntity itemEntity = new ItemEntity().fromItem(item);
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
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            throw new ItemNotFoundException(id);
        }
    }

    @Override
    public ResponseEntity<Item> putItem(
            Integer id,
            Item item
    ) {
        System.out.println("PUT Lessgoooo");
        if(item.getId() != id){
            throw new InvalidArgumentException();
        }
        System.out.println("ID is the same we good");
        Optional<ItemEntity> optItem = itemRepository.findById(id);
        ItemEntity itemToUpdate;
        if(optItem.isPresent()) {
            System.out.println("Exists, we update");
            itemToUpdate = optItem.get();
        }else{
            System.out.println("Doesn't exist, we create");
            itemToUpdate = new ItemEntity();
        }
        itemToUpdate.fromItem(item);
        System.out.println("We save the item!");
        itemRepository.save(itemToUpdate);
        System.out.println("We respond");
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Item> patchItem(
            Integer id,
            String name,
            Sound soundFrotte,
            Sound soundTape,
            List<Sound> soundsTombe
    ) {
        Optional<ItemEntity> opt = itemRepository.findById(id);
        if(opt.isPresent()){
            ItemEntity itemToUpdate = opt.get();
            Item responseItem = new Item();
            if(name != null)
                responseItem.setName(name);
            if(soundFrotte != null)
                responseItem.setSoundFrotte(soundFrotte);
            if(soundTape != null)
                responseItem.setSoundTape(soundTape);
            if(soundsTombe != null)
                responseItem.setSoundsTombe(soundsTombe);
            itemToUpdate.fromItem(responseItem);
            itemRepository.save(itemToUpdate);
            return new ResponseEntity<>(responseItem, HttpStatus.OK);
        }else{
            throw new ItemNotFoundException(id);
        }
    }
}
