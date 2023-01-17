package ch.heig.items.api.entities;

import jakarta.persistence.*;

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
    @ManyToOne @JoinColumn(name="id", nullable=false)
    private ItemEntity item;

    public int getId() {
        return id;
    }

    public String getSound() {
        return sound;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
