package org.alvarowau.populate;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.entity.ServiceOffering;
import org.alvarowau.repository.ServiceOfferingRepository;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.service.ProviderService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class ServiceOfferingPopulator {

    private final ServiceOfferingRepository serviceOfferingRepository;
    private final ProviderService providerService;

    public List<ServiceOffering> createServiceOfferings(List<UserRegistrationRequest> listProvider) {
        List<String> serviceNames = List.of(
                "Consulta General",
                "Terapia Física",
                "Asesoría Legal",
                "Revisión Médica",
                "Evaluación Psicológica",
                "Consultoría Financiera",
                "Diagnóstico Completo",
                "Revisión Técnica",
                "Consulta Veterinaria",
                "Terapia Ocupacional",
                "Asesoría en IT",
                "Diagnóstico Nutricional",
                "Entrenamiento Personal",
                "Asesoría de Marketing",
                "Reparación Electrónica"
        );
        List<Provider> providerEntities = new ArrayList<>();
        for (UserRegistrationRequest provider : listProvider) {
            providerService.findByUsername(provider.username()).ifPresent(providerEntities::add);
        }

        List<ServiceOffering> serviceOfferings = new ArrayList<>();
        int contador = 0;
        for (Provider provider : providerEntities) {
            ServiceOffering serviceOffering = ServiceOffering.builder()
                    .name(serviceNames.get(contador))
                    .duration(ThreadLocalRandom.current().nextInt(30, 51))
                    .provider(provider)
                    .isEnabled(true)
                    .build();
            serviceOfferings.add(serviceOffering);
            contador++;
        }
        List<ServiceOffering> serviceOfferingsFinal = new ArrayList<>();
        for (ServiceOffering serviceOffering : serviceOfferings) {
            serviceOffering = serviceOfferingRepository.save(serviceOffering);
            serviceOfferingsFinal.add(serviceOffering);
        }
        return serviceOfferingsFinal;
    }
}