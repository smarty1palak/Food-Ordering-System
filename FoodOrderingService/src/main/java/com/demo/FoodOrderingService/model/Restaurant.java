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

    private Integer currLoad = 0;

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

}
