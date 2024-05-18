import React, { useState, useEffect } from "react";
import axios from "axios";

const HotelList = ({ setSelectedHotel, radius }) => {
  const [hotels, setHotels] = useState([]);
  const [isSelected, setIsSelected] = useState(false);

  const getLocation = () => {
    const expiresDate = new Date();
    expiresDate.setDate(expiresDate.getDate() + 7);
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          localStorage.setItem("latitude", latitude);
          localStorage.setItem("longitude", longitude);
          console.log(
            "Current location:",
            JSON.stringify({ latitude, longitude })
          );
        },
        (error) => {
          console.error("Error getting location", error);
        }
      );
    } else {
      console.error("Geolocation is not supported by this browser.");
    }
  };

  const getHotels = async () => {
    const latitude = localStorage.getItem("latitude");
    const longitude = localStorage.getItem("longitude");
    await axios
      .get(
        `http://localhost:8080/hotels/nearby/${radius}/${latitude}/${longitude}`
      )
      .then((response) => setHotels(response.data))
      .catch((error) => console.error("Error fetching hotels:", error));
  };

  const handleOnClick = (hotel) => {
    setIsSelected(!isSelected);
    if (isSelected) {
      setSelectedHotel(hotel);
    } else {
      setSelectedHotel(null);
    }
  };

  useEffect(() => {
    getLocation();
    getHotels();
  }, [radius]);

  return (
    <div>
      <h2>Nearby Hotels</h2>
      <ul>
        {hotels.map((hotel) => (
          <li key={hotel.id} onClick={() => handleOnClick(hotel)}>
            {hotel.name}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default HotelList;
