package lk.ijse.dep9.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String name;
    private String address;
    private String contact;
    private String picture;

    public Student(String name, String address, String contact, String picture) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.picture = picture;
    }

    public Student(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }
}
