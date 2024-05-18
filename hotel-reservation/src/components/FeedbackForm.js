import React, { useState } from "react";
import axios from "axios";

const FeedbackForm = ({ hotel }) => {
  const [rating, setRating] = useState(1);
  const [comment, setComment] = useState("");

  const handleSubmit = () => {
    const feedback = {
      hotelId: hotel.id,
      rating,
      comment,
    };

    axios
      .post(`http://localhost:8080/feedback`, feedback)
      .then((response) => alert("Feedback submitted!"))
      .catch((error) => console.error("Error submitting feedback:", error));
  };

  return (
    <div>
      <h3>Leave Feedback for {hotel.name}</h3>
      <label>
        Rating:
        <select value={rating} onChange={(e) => setRating(e.target.value)}>
          {[1, 2, 3, 4, 5].map((r) => (
            <option key={r} value={r}>
              {r}
            </option>
          ))}
        </select>
      </label>
      <br />
      <label>
        Comment:
        <textarea
          value={comment}
          onChange={(e) => setComment(e.target.value)}
        />
      </label>
      <br />
      <button onClick={handleSubmit}>Submit Feedback</button>
    </div>
  );
};

export default FeedbackForm;
