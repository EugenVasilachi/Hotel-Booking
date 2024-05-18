package com.example.demo.services.impl;

import com.example.demo.dtos.FeedbackDTO;
import com.example.demo.models.Feedback;
import com.example.demo.repositories.FeedbackRepository;
import com.example.demo.repositories.HotelRepository;
import com.example.demo.services.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeedbackService implements IFeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository,
                           HotelRepository hotelRepository) {
        this.feedbackRepository = feedbackRepository;
        this.hotelRepository = hotelRepository;
    }

    public Feedback createFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = Feedback.builder()
                .hotel(hotelRepository.findById(feedbackDTO.getHotelId()).orElseThrow(() -> new RuntimeException("Hotel not found")))
                .rating(feedbackDTO.getRating())
                .comment(feedbackDTO.getComment())
                .createdAt(LocalDateTime.now())
                .build();
        return feedbackRepository.save(feedback);
    }
}
