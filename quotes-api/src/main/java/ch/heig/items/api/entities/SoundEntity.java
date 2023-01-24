package ch.heig.items.api.entities;

import jakarta.persistence.*;
import org.openapitools.model.Sound;

@Entity(name = "Sound")
@Table(name = "sounds")
public class SoundEntity {
    @TableGenerator(name = "genSounds",
            table = "idSounds",
            pkColumnName = "name",
            valueColumnName = "val",
            initialValue = 3,
            allocationSize = 100)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "genSounds")
    private int id;
    private String sound;
//    @ManyToOne @JoinColumn(name="idItem", nullable=true)
//    private ItemEntity item;

    public int getId() {
        return id;
    }

    public String getSound() {
        return sound;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Sound toSound(){
        Sound sound = new Sound();
        sound.setSound(this.sound);
        sound.setId(this.id);
        return sound;
    }

    public SoundEntity fromSound(Sound sound) {
        this.id = sound.getId();
        this.sound = sound.getSound();
        return this;
    }
}
