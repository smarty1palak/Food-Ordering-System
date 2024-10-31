package com.mahel.FoodOrderingService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "food")
    private List<RestaurantMenuFood> restaurantMenuFoods;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "food")
//    private List<OrderItem> orderItems;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category foodCategory;

//    @Column(length = 1000)
//    @ElementCollection
//    private List<String> images;

//    private boolean available;



//    private boolean isVegetarian;
//
//    private boolean isSeasonal;

//    @ManyToMany
//    @JoinTable(
//            name = "food_ingredient",
//            joinColumns = @JoinColumn(name = "food_id"),
//            inverseJoinColumns = @JoinColumn(name = "ingredients_item_id")
//    )
//    private List<IngredientsItem> ingredientsItems;
//
//    private Date creationDate;
}
