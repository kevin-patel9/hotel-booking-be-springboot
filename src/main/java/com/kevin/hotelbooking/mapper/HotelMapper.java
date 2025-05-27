package com.kevin.hotelbooking.mapper;

import com.kevin.hotelbooking.dtos.HotelListDto;
import com.kevin.hotelbooking.entities.Hotel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
//    List<HotelListDto> toHotelListDto(List<Hotel> hotel);
}
