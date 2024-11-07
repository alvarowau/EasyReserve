package org.alvarowau.model.dto.mapper;


import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.booking.BookingCreationResponse;
import org.alvarowau.model.entity.Booking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class MapperBooking {

    private final ModelMapper modelMapper;

    public BookingCreationResponse toResponse(Booking booking) {
        return new BookingCreationResponse(
               booking.getAppointment().getTrackingNumber(),
               booking.getAppointment().getDate(),
               formatTimeRange(
                       booking.getAppointment().getStartTime(),
                       booking.getAppointment().getEndTime()
               ),
                booking.getBookingNumber(),
                booking.getStatus(),
                booking.getCustomer().getUsername()
        );
    }

    private String formatTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return startTime.format(formatter) + "-" + endTime.format(formatter);
    }


}
