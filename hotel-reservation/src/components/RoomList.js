import React, { useState, useEffect } from "react";
import axios from "axios";

const RoomList = ({ hotel, setSelectedRooms }) => {
  const [rooms, setRooms] = useState([]);

  const getRooms = async () => {
    await axios
      .get(`http://localhost:8080/rooms/hotel/${hotel.id}`)
      .then((response) => setRooms(response.data))
      .catch((error) => console.error("Error fetching rooms:", error));
  };

  useEffect(() => {
    getRooms();
  }, [hotel]);

  const handleRoomSelection = (room) => {
    setSelectedRooms((prevRooms) => [...prevRooms, room]);
  };

  return (
    <div>
      <h2>Available Rooms in {hotel.name}</h2>
      <ul>
        {rooms.map((room) => (
          <li key={room.id}>
            {room.roomNumber} - {room.type} - ${room.price}
            {room.available && (
              <button onClick={() => handleRoomSelection(room)}>Select</button>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default RoomList;
