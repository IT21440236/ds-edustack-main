package com.example.Payment.service;

import com.example.Payment.entity.CourseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "learnerservice")
public interface PaymentClient {

    @GetMapping("/api/v1/learners/{learnerId}/courses")
    ResponseEntity<Map<Long, CourseInfo>> getLearnerById(@PathVariable("learnerId") Long learnerId);


}
