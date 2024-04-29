package com.learningloom.learnerservice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CourseInfo {

    private String name;
    private Double price;
    private String paymentStatus = "Not Paid";
}
