package ch.heig.quotes.api.endpoints;

import ch.heig.quotes.api.entities.ItemEntity;
import ch.heig.quotes.api.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemsEndPoint implements ItemsApi {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ResponseEntity<List<Items>> getItems(){
        List<ItemEntity> itemsEntities = itemRepository.findAll();
        List<Items> items = new ArrayList<>();
        for(ItemEntity itemEntity : itemsEntities){
            Items item = new Items();
            item.setId(itemEntity.getId());
            item.setName(itemEntity.getName());
            item.setSound(itemEntity.getSound());
            items.add(item);
        }
        return new ResponseEntity<List<Items>>(items, HttpStatus.OK))
    }


}
