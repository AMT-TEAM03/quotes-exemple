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

    private int id;
    private String name;

    @OneToOne @JoinColumn(name = "id")
    private SoundEntity soundFrotte;

    @OneToMany(targetEntity = SoundEntity.class, mappedBy = "item")
    private List<SoundEntity> soundsTape = new ArrayList<>();

    @ManyToMany
    @JoinTable( name = "ITEMS_SOUNDS_ASSOCIATION",
            joinColumns = @JoinColumn( name = "idItem" ),
            inverseJoinColumns = @JoinColumn( name = "idSound" ) )
    private List<SoundEntity> soundsTombe = new ArrayList<>();

    public ItemEntity(){}

    public ItemEntity(int id, String name, SoundEntity soundFrotte, List<SoundEntity> soundsTape, List<SoundEntity> soundsTombe) {
        this.id = id;
        this.name = name;
        this.soundFrotte = soundFrotte;
        this.soundsTape = soundsTape;
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

    public List<SoundEntity> getSoundsTape() { return soundsTape; }

    public List<SoundEntity> getSoundsTombe() { return soundsTombe; }

    public void setSoundFrotte(SoundEntity soundFrotte) { this.soundFrotte = soundFrotte; }

    public void setSoundsTape(List<SoundEntity> soundsTape) { this.soundsTape = soundsTape; }

    public void setSoundsTombe(List<SoundEntity> soundsTombe) { this.soundsTombe = soundsTombe; }

    public Item toItem(){
        Item item = new Item();
        item.setId(this.id);
        item.setName(this.name);
        item.setSoundFrotte(this.soundFrotte.toSound());
        List<Sound> convertedSoundsTombe = new ArrayList<>();
        this.soundsTombe.forEach(sound -> {
            convertedSoundsTombe.add(sound.toSound());
        });
        List<Sound> convertedSoundsTape = new ArrayList<>();
        this.soundsTombe.forEach(sound -> {
            convertedSoundsTape.add(sound.toSound());
        });
        item.setSoundsTombe(convertedSoundsTombe);
        item.setSoundsTape(convertedSoundsTape);
        return item;
    }
}
