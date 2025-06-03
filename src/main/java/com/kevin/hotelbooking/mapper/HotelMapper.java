package com.kevin.hotelbooking.mapper;

import com.kevin.hotelbooking.dtos.HotelCreateRequestDto;
import com.kevin.hotelbooking.dtos.HotelUpdateRequestDto;
import com.kevin.hotelbooking.entities.Hotel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    Hotel createHotel(HotelCreateRequestDto hotelCreateRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Hotel updateHotelFromDto(HotelUpdateRequestDto dto, @MappingTarget Hotel entity);
}
