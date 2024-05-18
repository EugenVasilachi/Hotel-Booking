import React, { useState, useEffect } from "react";
import axios from "axios";
import "../style/reservationlist.css";

const ReservationList = () => {
  const [reservations, setReservations] = useState([]);

  const fetchReservations = async () => {
    await axios
      .get("http://localhost:8080/reservations")
      .then((response) => setReservations(response.data))
      .catch((error) => console.error("Error fetching reservations:", error));
  };

  const handleCancel = async (reservationId) => {
    try {
      await axios.delete(`http://localhost:8080/reservations/${reservationId}`);
      fetchReservations();
    } catch (error) {
      console.error("Error cancelling reservation:", error);
    }
  };

  useEffect(() => {
    fetchReservations();
  }, []);

  return (
    <div>
      <h2>My Reservations</h2>
      <ul>
        {reservations.map((reservation) => (
          <li key={reservation.id}>
            <p>Hotel: {reservation.hotelName}</p>
            <p>Room Number: {reservation.roomNumber}</p>
            <p>Room Type: {reservation.roomType}</p>
            <p>Check-In: {new Date(reservation.checkIn).toLocaleString()}</p>
            <p>Check-Out: {new Date(reservation.checkOut).toLocaleString()}</p>
            <p>
              Date created: {new Date(reservation.createdAt).toLocaleString()}
            </p>
            <button
              className="cancel-button"
              onClick={() => handleCancel(reservation.id)}
            >
              Cancel Reservation
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ReservationList;
