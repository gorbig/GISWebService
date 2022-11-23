package com.example.giswebservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @SequenceGenerator(
            name = "dbUser_sequence",
            sequenceName = "dbUser_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "student_sequence")
    private Long id;
}
