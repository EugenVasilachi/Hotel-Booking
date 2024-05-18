package com.example.demo.controllers;

import com.example.demo.dtos.FeedbackDTO;
import com.example.demo.models.Feedback;
import com.example.demo.services.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/feedback")
public class FeedbackController {

    private IFeedbackService feedbackService;

    @Autowired
    public FeedbackController(IFeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping()
    public ResponseEntity<Feedback> leaveFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.createFeedback(feedbackDTO);
        return ResponseEntity.ok(feedback);
    }
}
