package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.exception.user.UserProviderNotFoundException;
import org.alvarowau.model.dto.mapper.MapperServiceOffering;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.entity.ServiceOffering;
import org.alvarowau.repository.ServiceOfferingRepository;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.service.ProviderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final MapperServiceOffering mapper;
    private final ProviderService providerService;
    private final SecurityContextUtil securityContextUtil;

    public ServiceOfferingResponse createServiceOffering(ServiceOfferingRequest request) {
        ServiceOffering serviceOffering = mapper.toEntity(request);

        String authenticatedUsername = securityContextUtil.getAuthenticatedUsername();
        Optional<Provider> optionalProvider = providerService.findByUsername(authenticatedUsername);
        Provider provider = optionalProvider.orElseThrow(() -> {
            log.error("Proveedor no encontrado para el usuario autenticado: {}", authenticatedUsername);
            return new UserProviderNotFoundException("Proveedor no encontrado para el usuario autenticado.");
        });

        serviceOffering.setProvider(provider);
        try {
            serviceOffering = serviceOfferingRepository.save(serviceOffering);
            log.info("ServiceOffering guardado con Provider: {}", serviceOffering);
        } catch (Exception e) {
            log.error("Error al guardar ServiceOffering: {}", e.getMessage());
            throw new org.alvarowau.exception.schedule.ServiceOfferingSaveException("No se pudo guardar el ServiceOffering.", e);
        }

        return mapper.toResponse(serviceOffering);
    }

    public List<ServiceOfferingResponse> searchServiceOfferingByUsernameProvider(String username) {
        List<ServiceOffering> serviceOfferingList = serviceOfferingRepository.findByProvider_Username(username);
        return serviceOfferingList.stream().map(mapper::toResponse).toList();
    }

    public Optional<ServiceOffering> getServiceOfferingByUsername(String name) {
        return serviceOfferingRepository.findByName(name);
    }
}