package com.example.demo.services;

import com.example.demo.dtos.FeedbackDTO;
import com.example.demo.models.Feedback;

public interface IFeedbackService {
    Feedback createFeedback(FeedbackDTO feedbackDTO);
}
