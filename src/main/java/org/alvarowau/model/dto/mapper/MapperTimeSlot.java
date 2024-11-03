package org.alvarowau.model.dto.mapper;


import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.timeslot.TimeSlotRequest;
import org.alvarowau.model.entity.TimeSlot;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperTimeSlot {

    private final ModelMapper modelMapper;

    public TimeSlot toEntity(TimeSlotRequest request) {
        return TimeSlot.builder()
                .startTime(request.startTime())
                .endTime(request.endTime())
                .isAvailable(true)
                .build();
    }
}
