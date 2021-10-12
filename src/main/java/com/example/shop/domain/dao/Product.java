package com.example.shop.domain.dao;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends Auditable implements IdentifiedDataSerializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Double price;
    private String description;
    private Integer quantity;
    @ManyToOne
    @JoinTable(name = "user_category", inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Category category;

    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 1;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeString(name);
        out.writeString(description);
        out.writeDouble(price);
        out.writeInt(quantity);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        this.name = in.readString();
        this.description = in.readString();
        this.price = in.readDouble();
        this.quantity = in.readInt();
    }
}
