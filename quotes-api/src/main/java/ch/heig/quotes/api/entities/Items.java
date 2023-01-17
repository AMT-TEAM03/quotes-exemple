package ch.heig.quotes.api.entities;


import jakarta.persistence.*;

@Entity(name = "Item")
@Table(name = "items")
public class Items {
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
    private String sound;


    public Items(){}

    public Items(int id, String name, String sound) {
        this.id = id;
        this.name = name;
        this.sound = sound;
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

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }

}
