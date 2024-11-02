package org.alvarowau.model.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.entity.ServiceOffering;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperServiceOffering {

    private final ModelMapper modelMapper;

    public ServiceOffering toEntity(ServiceOfferingRequest serviceOfferingRequest) {
        return ServiceOffering .builder()
                .name(serviceOfferingRequest.name())
                .duration(serviceOfferingRequest.duration())
                .build();
    }

    public ServiceOfferingResponse toResponse(ServiceOffering serviceOffering) {
        return new ServiceOfferingResponse(
                serviceOffering.getName(),
                serviceOffering.getDuration(),
                serviceOffering.getProvider().getUsername()
        );
    }
}
