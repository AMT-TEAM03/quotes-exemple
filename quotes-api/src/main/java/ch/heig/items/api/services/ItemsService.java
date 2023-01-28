package ch.heig.items.api.services;

import ch.heig.items.api.entities.ItemEntity;
import ch.heig.items.api.exceptions.InvalidArgumentException;
import ch.heig.items.api.exceptions.ItemNotFoundException;
import ch.heig.items.api.repositories.ItemRepository;
import org.openapitools.model.Item;
import org.openapitools.model.Sound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAll(){
        List<ItemEntity> allItems = itemRepository.findAll();
        List<Item> result = new ArrayList<>();
        for(ItemEntity itemEntity : allItems){
            result.add(itemEntity.toItem());
        }
        return result;
    }

    public Item getById(Integer id){
        Optional<ItemEntity> opt = itemRepository.findById(id);
        if(!opt.isPresent()){
            return null;
        }
        return opt.get().toItem();
    }

    public Item add(Item item){
        if(item.getId() != null){
            Optional<ItemEntity> alreadyExist = itemRepository.findById(item.getId());
            if(alreadyExist.isPresent()){
                throw new InvalidArgumentException();
            }
        }else{
            itemRepository.save(new ItemEntity().fromItem(item));
        }
        return item;
    }

    public Item addOrUpdate(Item item){
        Optional<ItemEntity> optItem = itemRepository.findById(item.getId());
        ItemEntity itemToUpdate;
        if(optItem.isPresent()) {
            itemToUpdate = optItem.get();
        }else{
            itemToUpdate = new ItemEntity();
        }
        itemToUpdate.fromItem(item);
        itemRepository.save(itemToUpdate);
        return itemToUpdate.toItem();
    }

    public Item update(
            Integer id,
            String name,
            Sound soundFrotte,
            Sound soundTape,
            List<Sound> soundsTombe
    ){
        Optional<ItemEntity> opt = itemRepository.findById(id);
        if(!opt.isPresent()) {
            throw new ItemNotFoundException(id);
        }
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
        return responseItem;
    }
}
