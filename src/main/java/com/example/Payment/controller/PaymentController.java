package com.example.Payment.controller;

import com.example.Payment.entity.CourseInfo;
import com.example.Payment.service.CompletedPaymentsImpl;
import com.example.Payment.service.PaymentClient;
import com.example.Payment.service.PaymentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/payments")
public class PaymentController {

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    PaymentImpl paymentImpl;

    @Autowired
    CompletedPaymentsImpl completedPaymentsImpl;

    @GetMapping("/{learnerId}/courses")
    public ResponseEntity<Map<Long, CourseInfo>> getLearnerCourses(@PathVariable Long learnerId) {
        Map<Long, CourseInfo> learnerCourses = paymentImpl.getLearnerCourses(learnerId);
        if (learnerCourses == null || learnerCourses.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            // Save the retrieved courses into the database
            paymentImpl.saveLearnerCourses(learnerId, learnerCourses);
            return ResponseEntity.ok(learnerCourses);
        }
    }

    @GetMapping("/{learnerId}/total-price")
    public ResponseEntity<Double> getTotalPriceForLearner(@PathVariable Long learnerId) {
        // Retrieve the learner's courses from the payment client
        ResponseEntity<Map<Long, CourseInfo>> response = paymentClient.getLearnerById(learnerId);

        if (response != null && response.getBody() != null) {
            // Calculate the total price from the courses
            Map<Long, CourseInfo> courses = response.getBody();
            double totalPrice = paymentImpl.calculateTotalPrice(courses);

            return ResponseEntity.ok(totalPrice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updatePaymentStatus/{learnerId}")
    public ResponseEntity<String> updatePaymentStatusForAllCourses(@PathVariable Long learnerId) {
        ResponseEntity<Void> response = paymentClient.updatePaymentStatusForAllCourses(learnerId);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("Payment status updated successfully for learner with ID: " + learnerId);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to update payment status for learner with ID: " + learnerId);
        }
    }

//    @GetMapping("/{learnerId}/total-price")
//    public ResponseEntity<String> getTotalPriceAndCompletePayment(@PathVariable Long learnerId) {
//        // Retrieve the learner's courses from the payment client
//        ResponseEntity<Map<Long, CourseInfo>> response = paymentClient.getLearnerById(learnerId);
//
//        if (response != null && response.getBody() != null) {
//            // Calculate the total price from the courses
//            Map<Long, CourseInfo> courses = response.getBody();
//            double totalPrice = paymentImpl.calculateTotalPrice(courses);
//
//            // Complete payment by calling the completePayment endpoint internally
//            ResponseEntity<String> paymentResponse = completePayment(learnerId, totalPrice);
//            return ResponseEntity.ok("Total price: " + totalPrice + "\nPayment status: " + paymentResponse.getBody());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PostMapping("/completePayment")
//    private ResponseEntity<String> completePayment(Long learnerId, Double price) {
//        try {
//            Completed_Payments completedPayment = new Completed_Payments();
//            completedPayment.setLearnerId(String.valueOf(learnerId));
//            completedPayment.setPrice(price);
//            completedPayment.setDate(new Date());
//            completedPayment.setCurrency(completedPayment.getCurrency());
//            completedPayment.setDescription(completedPayment.getDescription());
//            completedPayment.setIntent(completedPayment.getIntent());
//            completedPayment.setMethod(completedPayment.getMethod());
//
//            completedPaymentsImpl.saveCompletedPayment(completedPayment);
//            return ResponseEntity.ok("Payment completed and saved successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error occurred while completing payment: " + e.getMessage());
//        }
//    }

}
