import React, { useState } from "react";
import axios from "axios";

const BookingForm = ({ rooms }) => {
  const [checkIn, setCheckIn] = useState("");
  const [checkOut, setCheckOut] = useState("");

  const handleBooking = () => {
    if (!checkIn || !checkOut) {
      alert("Please select check-in and check-out dates");
      return;
    }

    console.log("Check-In: ", checkIn);
    console.log("Check-Out: ", checkOut);
    rooms.forEach((room) => {
      const booking = {
        roomId: room.id,
        checkIn: checkIn,
        checkOut: checkOut,
      };

      axios
        .post("http://localhost:8080/reservations", booking)
        .then((response) => {
          console.log(`Booking successful for room: ${room.id}`);
          alert("Booking successful!");
        })
        .catch((error) => console.error("Error booking rooms:", error));
    });
  };

  return (
    <div>
      <h2>Book Selected Rooms</h2>
      <label>
        Check-In:
        <input
          type="datetime-local"
          value={checkIn}
          onChange={(e) => setCheckIn(e.target.value)}
        />
      </label>
      <br />
      <label>
        Check-Out:
        <input
          type="datetime-local"
          value={checkOut}
          onChange={(e) => setCheckOut(e.target.value)}
        />
      </label>
      <br />
      <button onClick={handleBooking}>Book</button>
    </div>
  );
};

export default BookingForm;
