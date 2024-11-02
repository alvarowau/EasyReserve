package org.alvarowau.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.model.dto.mapper.MapperServiceOffering;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingRequest;
import org.alvarowau.model.dto.serviceoffering.ServiceOfferingResponse;
import org.alvarowau.model.entity.ServiceOffering;
import org.alvarowau.repository.ServiceOfferingRepository;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.service.ProviderService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final MapperServiceOffering mapper;
    private final ProviderService providerService;
    private final SecurityContextUtil securityContextUtil;

    public ServiceOfferingResponse createServiceOffering(@Valid ServiceOfferingRequest request) {
        log.info("Iniciando la creación de un nuevo ServiceOffering con nombre: {}", request.name());

        // Primero crea el ServiceOffering sin el Provider
        ServiceOffering serviceOffering = mapper.toEntity(request);

        // Guarda el ServiceOffering en la base de datos (sin el Provider por ahora)
        try {
            serviceOffering = serviceOfferingRepository.save(serviceOffering);
            log.info("ServiceOffering guardado sin Provider: {}", serviceOffering);
        } catch (Exception e) {
            log.error("Error al guardar ServiceOffering: {}", e.getMessage());
            throw new IllegalStateException("No se pudo guardar el ServiceOffering.", e);
        }

        // Obtén el Provider del usuario autenticado
        String authenticatedUsername = securityContextUtil.getAuthenticatedUsername();
        Optional<Provider> optionalProvider = providerService.findByUsername(authenticatedUsername);

        Provider provider = optionalProvider.orElseThrow(() -> {
            log.error("Proveedor no encontrado para el usuario autenticado: {}", authenticatedUsername);
            return new IllegalArgumentException("Proveedor no encontrado para el usuario autenticado.");
        });

        // Establece el Provider en el ServiceOffering
        serviceOffering.setProvider(provider);
        log.info("Estableciendo Provider en ServiceOffering: {}", provider);

        // Actualiza el ServiceOffering con el Provider
        try {
            serviceOffering = serviceOfferingRepository.save(serviceOffering);
            log.info("ServiceOffering actualizado con Provider: {}", serviceOffering);
        } catch (Exception e) {
            log.error("Error al actualizar ServiceOffering con Provider: {}", e.getMessage());
            throw new IllegalStateException("No se pudo actualizar el ServiceOffering con el Provider.", e);
        }

        // Retorna la respuesta
        log.info("Creación de ServiceOffering completada: {}", serviceOffering);
        return mapper.toResponse(serviceOffering);
    }
}
