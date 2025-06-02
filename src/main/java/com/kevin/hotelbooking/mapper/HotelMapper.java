package com.kevin.hotelbooking.mapper;

import com.kevin.hotelbooking.dtos.HotelCreateRequestDto;
import com.kevin.hotelbooking.entities.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {
//    List<HotelListDto> toHotelListDto(List<Hotel> hotel);

    Hotel createHotel(HotelCreateRequestDto hotelCreateRequestDto);
}
