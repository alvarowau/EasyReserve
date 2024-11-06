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
import org.alvarowau.user.service.ProviderAccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceOfferingManagementService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final MapperServiceOffering mapper;
    private final ProviderAccountService providerService;
    private final SecurityContextUtil securityContextUtil;

    public ServiceOfferingResponse createServiceOfferingForProvider(ServiceOfferingRequest request) {
        ServiceOffering serviceOffering = mapper.toEntity(request);

        String authenticatedUsername = securityContextUtil.getAuthenticatedUsername();
        Optional<Provider> optionalProvider = providerService.findByUsername(authenticatedUsername);
        Provider provider = optionalProvider.orElseThrow(() -> {
            return new UserProviderNotFoundException("Proveedor no encontrado para el usuario autenticado.");
        });

        serviceOffering.setProvider(provider);
        try {
            serviceOffering = serviceOfferingRepository.save(serviceOffering);
        } catch (Exception e) {
            throw new org.alvarowau.exception.schedule.ServiceOfferingSaveException("No se pudo guardar el ServiceOffering.", e);
        }

        return mapper.toResponse(serviceOffering);
    }

    public List<ServiceOfferingResponse> getServiceOfferingsByProviderUsername(String username) {
        List<ServiceOffering> serviceOfferingList = serviceOfferingRepository.findByProvider_Username(username);
        return serviceOfferingList.stream().map(mapper::toResponse).toList();
    }

    public Optional<ServiceOffering> getServiceOfferingByName(String name) {
        return serviceOfferingRepository.findByName(name);
    }
}