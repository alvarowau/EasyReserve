package org.alvarowau.populate;

import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProviderPopulator {


    public List<UserRegistrationRequest> createProvider() {
        List<UserRegistrationRequest> providerList = new ArrayList<>();

        providerList.add(new UserRegistrationRequest(
                "juanPerez01", "password123", "password123", "juan.perez01@example.com",
                "Juan", "Pérez", "12345678X", "123-456-7890", "321-654-0987", "juan.alt@example.com",
                "Calle Principal 123", "Madrid", "Madrid", "28001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "mariaGonzalez02", "pass456", "pass456", "maria.gonzalez02@example.com",
                "María", "González", "87654321Y", "987-654-3210", "654-321-0987", "maria.sec@example.com",
                "Avenida del Sol 456", "Barcelona", "Cataluña", "08002", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "carlosRodriguez03", "clave789", "clave789", "carlos.rodriguez03@example.com",
                "Carlos", "Rodríguez", "11223344Z", "456-789-1234", "789-123-4567", "carlos.rod@example.com",
                "Paseo de la Castellana 789", "Sevilla", "Andalucía", "41001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "lauraMartinez04", "contrasena321", "contrasena321", "laura.martinez04@example.com",
                "Laura", "Martínez", "33445566A", "321-987-6543", "123-456-7890", "laura.alt@example.com",
                "Calle Verde 23", "Valencia", "Comunidad Valenciana", "46001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "pedroLopez05", "segura456", "segura456", "pedro.lopez05@example.com",
                "Pedro", "López", "55667788B", "654-123-9876", "789-654-3210", "pedro.alt@example.com",
                "Calle Azul 45", "Bilbao", "País Vasco", "48001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "anaTorres06", "torres2024", "torres2024", "ana.torres06@example.com",
                "Ana", "Torres", "66778899C", "123-456-7899", "987-654-1230", "ana.torres@example.com",
                "Avenida de las Flores 89", "Málaga", "Andalucía", "29001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "diegoFernandez07", "diego789", "diego789", "diego.fernandez07@example.com",
                "Diego", "Fernández", "77889900D", "654-321-4567", "456-789-1230", "diego.alt@example.com",
                "Calle Roja 78", "Zaragoza", "Aragón", "50001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "luciaHernandez08", "lucia123", "lucia123", "lucia.hernandez08@example.com",
                "Lucía", "Hernández", "88990011E", "321-654-9870", "123-456-7894", "lucia.alt@example.com",
                "Calle Blanca 56", "Valladolid", "Castilla y León", "47001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "santiagoDiaz09", "santiago456", "santiago456", "santiago.diaz09@example.com",
                "Santiago", "Díaz", "99001122F", "987-123-6540", "789-123-4561", "santiago.diaz@example.com",
                "Avenida Libertad 10", "Granada", "Andalucía", "18001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "victoriaAlvarez10", "victoria321", "victoria321", "victoria.alvarez10@example.com",
                "Victoria", "Álvarez", "11002233G", "123-789-4561", "654-321-7890", "victoria.alt@example.com",
                "Calle Estrella 67", "Murcia", "Murcia", "30001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "adrianMoreno11", "adrian654", "adrian654", "adrian.moreno11@example.com",
                "Adrián", "Moreno", "22113344H", "654-987-3210", "123-987-6540", "adrian.alt@example.com",
                "Calle Larga 123", "San Sebastián", "País Vasco", "20001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "martinaGutierrez12", "martina2024", "martina2024", "martina.gutierrez12@example.com",
                "Martina", "Gutiérrez", "33224455I", "321-456-7891", "987-123-6541", "martina.alt@example.com",
                "Avenida Central 45", "Alicante", "Comunidad Valenciana", "03001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "fernandoCastro13", "fernando789", "fernando789", "fernando.castro13@example.com",
                "Fernando", "Castro", "44335566J", "123-987-6543", "456-123-7891", "fernando.alt@example.com",
                "Calle Mayor 78", "Santander", "Cantabria", "39001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "sofiaMolina14", "sofia456", "sofia456", "sofia.molina14@example.com",
                "Sofía", "Molina", "55446677K", "987-321-6542", "789-321-6542", "sofia.alt@example.com",
                "Calle del Sol 90", "Pamplona", "Navarra", "31001", "España"
        ));

        providerList.add(new UserRegistrationRequest(
                "javierSantos15", "javier123", "javier123", "javier.santos15@example.com",
                "Javier", "Santos", "66557788L", "321-654-1234", "123-789-4562", "javier.alt@example.com",
                "Avenida Norte 67", "Badajoz", "Extremadura", "06001", "España"
        ));

        return providerList;
    }
}