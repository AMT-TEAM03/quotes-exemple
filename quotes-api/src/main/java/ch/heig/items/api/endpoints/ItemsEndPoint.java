package ch.heig.items.api.endpoints;

import ch.heig.items.api.entities.ItemEntity;
import ch.heig.items.api.exceptions.ItemNotFoundException;
import ch.heig.items.api.repositories.ItemRepository;
import org.openapitools.api.ItemsApi;
import org.openapitools.model.Item;
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
            Item item = new Item();
            item.setId(itemEntity.getId());
            item.setName(itemEntity.getName());
//            item.setSoundFrotte(itemEntity.getSoundFrotte());
//            item.setSoundsTape(itemEntity.getSoundsTape());
//            item.setSoundsTombe(itemEntity.getSoundsTombe());
            items.add(item);
        }
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addItem(@RequestBody Item item){
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName(item.getName());
//        itemEntity.setSound(item.getSound());
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
            Item item = new Item();
            item.setId(itemEntity.getId());
            item.setName(itemEntity.getName());
//            item.setSound(itemEntity.getSound());
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        } else {
            throw new ItemNotFoundException(id);
        }
    }
}
