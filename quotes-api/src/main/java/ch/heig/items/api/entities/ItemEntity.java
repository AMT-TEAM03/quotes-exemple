package ch.heig.items.api.entities;


import jakarta.persistence.*;
import org.openapitools.model.Item;
import org.openapitools.model.Sound;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Item")
@Table(name = "items")
public class ItemEntity {
    @TableGenerator(name = "genItems",
            table = "idItems",
            pkColumnName = "name",
            valueColumnName = "val",
            initialValue = 3,
            allocationSize = 100)
    @Id // @GeneratedValue
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genItems")
    private Integer id;
    private String name;

    @OneToOne @JoinColumn(name = "id")
    private SoundEntity soundFrotte;

    @ManyToOne @JoinTable(name = "ITEMS_SOUNDS_TAPE_ASSOCIATION",
            joinColumns = @JoinColumn( name = "idItem" ),
            inverseJoinColumns = @JoinColumn( name = "idSound" ))
    private SoundEntity soundTape;

    @ManyToMany
    @JoinTable( name = "ITEMS_SOUNDS_TOMBE_ASSOCIATION",
            joinColumns = @JoinColumn( name = "idSound" ),
            inverseJoinColumns = @JoinColumn( name = "idItem" ) )
    private List<SoundEntity> soundsTombe = new ArrayList<>();

    public ItemEntity(){}

    public ItemEntity(int id, String name, SoundEntity soundFrotte, SoundEntity soundTape, List<SoundEntity> soundsTombe) {
        this.id = id;
        this.name = name;
        this.soundFrotte = soundFrotte;
        this.soundTape = soundTape;
        this.soundsTombe = soundsTombe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SoundEntity getSoundFrotte() { return soundFrotte; }

    public SoundEntity getSoundTape() { return soundTape; }

    public List<SoundEntity> getSoundsTombe() { return soundsTombe; }

    public void setSoundFrotte(SoundEntity soundFrotte) { this.soundFrotte = soundFrotte; }

    public void setSoundTape(SoundEntity soundTape) { this.soundTape = soundTape; }

    public void setSoundsTombe(List<SoundEntity> soundsTombe) { this.soundsTombe = soundsTombe; }

    public Item toItem(){
        Item item = new Item();
        if(this.id != null){
            item.setId(this.id);
        }
        item.setName(this.name);
        if(this.soundFrotte != null){
            item.setSoundFrotte(this.soundFrotte.toSound());
        }
        if(this.soundTape != null){
            item.setSoundTape(this.soundTape.toSound());
        }
        List<Sound> newSoundsTombe = new ArrayList<>();
        for(SoundEntity sound : this.soundsTombe){
            newSoundsTombe.add(sound.toSound());
        }
        item.setSoundsTombe(newSoundsTombe);
        return item;
    }

    public ItemEntity fromItem(Item item){
        if(item.getId() != null){
            this.id = item.getId();
        }
        if(item.getName() != null){
            this.name = item.getName();
        }
        this.soundFrotte = item.getSoundFrotte() != null ?
                new SoundEntity().fromSound(item.getSoundFrotte()) :
                null;
        this.soundTape = item.getSoundTape() != null ?
                new SoundEntity().fromSound(item.getSoundTape()) :
                null;
        List<SoundEntity> newSoundsTombe = new ArrayList<>();
        if(item.getSoundsTombe() != null){
            for(Sound sound : item.getSoundsTombe()){
                newSoundsTombe.add(new SoundEntity().fromSound(sound));
            }
        }
        this.soundsTombe = newSoundsTombe;
        return this;
    }
}
