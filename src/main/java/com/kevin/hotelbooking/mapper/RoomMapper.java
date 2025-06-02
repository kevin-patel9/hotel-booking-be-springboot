package com.kevin.hotelbooking.mapper;

import com.kevin.hotelbooking.dtos.RoomCreateRequestDto;
import com.kevin.hotelbooking.entities.Hotel;
import com.kevin.hotelbooking.entities.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    Room toRoom(RoomCreateRequestDto room);
}
