package com.example.database.entity.graal2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="mydata")
@Getter
@Setter
@RequiredArgsConstructor
public class MyData2 {

    @Id
    private String id;

    @Column(name = "some_data")
    private String someData;
}
