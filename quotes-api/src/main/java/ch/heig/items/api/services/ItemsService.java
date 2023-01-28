package ch.heig.items.api.services;

import ch.heig.items.api.entities.ItemEntity;
import ch.heig.items.api.exceptions.InvalidArgumentException;
import ch.heig.items.api.exceptions.ItemNotFoundException;
import ch.heig.items.api.repositories.ItemRepository;
import ch.heig.items.api.utils.Tuple;
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
            }else{
                item.setId(null);
            }
        }
        itemRepository.save(new ItemEntity().fromItem(item));
        return item;
    }

    public Tuple<Item, Boolean> addOrUpdate(Item item){
        ItemEntity itemToUpdate;
        Boolean newItem;
        if(item.getId() != null){
            Optional<ItemEntity> optItem = itemRepository.findById(item.getId());
            if(optItem.isPresent()) {
                newItem = false;
                itemToUpdate = optItem.get();
            }else{
                // To automatically generate id
                // We don't want the user to be able to choose the id
                item.setId(null);
                newItem = true;
                itemToUpdate = new ItemEntity();
            }
        }else{
            newItem = true;
            itemToUpdate = new ItemEntity();
        }
        itemToUpdate.fromItem(item);
        itemToUpdate = itemRepository.save(itemToUpdate);
        return new Tuple<>(itemToUpdate.toItem(), newItem);
    }

    public Item update(
            Item item
    ){
        if(item.getId() == null){
            throw new InvalidArgumentException();
        }
        Optional<ItemEntity> opt = itemRepository.findById(item.getId());
        if(!opt.isPresent()) {
            throw new ItemNotFoundException(item.getId());
        }
        ItemEntity itemToUpdate = opt.get();
        Item responseItem = new Item();
        responseItem.setId(itemToUpdate.getId());
        if(item.getName() != null)
            responseItem.setName(item.getName());
        if(item.getSoundFrotte() != null)
            responseItem.setSoundFrotte(item.getSoundFrotte());
        if(item.getSoundTape() != null)
            responseItem.setSoundTape(item.getSoundTape());
        if(item.getSoundsTombe() != null)
            responseItem.setSoundsTombe(item.getSoundsTombe());
        itemToUpdate.fromItem(responseItem);
        itemRepository.save(itemToUpdate);
        return responseItem;
    }
}
