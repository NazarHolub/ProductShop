package app.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotBlank(message = "Must be filled")

    String name;
    int price;


    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public int getPrice() { return price; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public void setPrice(int price) { this.price = price; }
}
