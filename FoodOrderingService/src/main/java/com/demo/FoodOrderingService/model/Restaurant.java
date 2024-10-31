package com.demo.FoodOrderingService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(length = 1000)
    private String description;

    private Double rating;

    private Integer processingCapacity;

    private Integer currLoad;

    private long processingTimePerItem;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<RestaurantMenuItem> foods;

    public boolean hasProcessingCapacity() {
        // Logic to check current processing capacity
        if (currLoad == processingCapacity){
            return false;
        }
        else{
            return true;
        }
    }

    public void incrementProcessingLoad(int noOfItems){
        currLoad+=noOfItems;
    }

    public void releaseCapacity(int noOfItems) {
        currLoad-=noOfItems;
    }

    public List<RestaurantMenuItem> getMenu(){
        return foods;
    }
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
//    private List<Order> orders;

//    @ManyToOne
//    @JoinColumn(name = "owner_id", referencedColumnName = "id")
//    private User owner;

//    private String cuisineType;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id", referencedColumnName = "id")
//    private Address address;

//    @Embedded
//    private ContactInformation contactInformation;



//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
//    private List<IngredientsItem> ingredientsItems;
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
//    private List<IngredientCategory> ingredientCategories;
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
//    private List<Category> categories;
}
