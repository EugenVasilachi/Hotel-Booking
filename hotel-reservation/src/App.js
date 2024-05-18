import "./App.css";
import React, { useEffect, useState } from "react";
import HotelList from "./components/HotelList";
import RoomList from "./components/RoomList";
import BookingForm from "./components/BookingForm";
import FeedbackForm from "./components/FeedbackForm";
import ReservationList from "./components/ReservationList";

function App() {
  const [selectedHotel, setSelectedHotel] = useState(null);
  const [selectedRooms, setSelectedRooms] = useState([]);
  const [radius, setRadius] = useState();
  const [showReservations, setShowReservations] = useState(false);

  const handleRadiusEntered = (e) => {
    setRadius(e.target.value);
  };

  const handleShowReservations = () => {
    setShowReservations(!showReservations);
    // setSelectedHotel(null);
  };

  useEffect(() => {
    console.log(selectedRooms);
  }, [selectedRooms]);

  return (
    <div className="App">
      <h1>Hotel Reservation App</h1>
      <input
        type="number"
        value={radius}
        onChange={handleRadiusEntered}
        placeholder="Enter radius in meters"
      />
      {!showReservations ? (
        <>
          <HotelList setSelectedHotel={setSelectedHotel} radius={radius} />
          {selectedHotel && (
            <>
              <RoomList
                hotel={selectedHotel}
                setSelectedRooms={setSelectedRooms}
              />
              {selectedRooms.length > 0 && (
                <BookingForm rooms={selectedRooms} />
              )}
              <FeedbackForm hotel={selectedHotel} />
            </>
          )}
        </>
      ) : (
        <ReservationList />
      )}
      <button onClick={handleShowReservations}>My Reservations</button>
    </div>
  );
}

export default App;
