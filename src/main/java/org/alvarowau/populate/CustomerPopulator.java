package org.alvarowau.populate;

import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerPopulator {

    public List<UserRegistrationRequest> createCustomer(){
        List<UserRegistrationRequest> customerList = new ArrayList<>();

        customerList.add(new UserRegistrationRequest(
                "paulaGarcia16", "paulaPass123", "paulaPass123", "paula.garcia16@example.com",
                "Paula", "García", "77668899M", "123-456-7898", "654-321-9876", "paula.alt@example.com",
                "Calle Luna 11", "Madrid", "Madrid", "28002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "alvaroMorales17", "alvaro2023", "alvaro2023", "alvaro.morales17@example.com",
                "Álvaro", "Morales", "88991122N", "456-789-1235", "321-987-6543", "alvaro.alt@example.com",
                "Avenida de los Reyes 20", "Barcelona", "Cataluña", "08003", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "sofiaRuiz18", "sofiaPass456", "sofiaPass456", "sofia.ruiz18@example.com",
                "Sofía", "Ruiz", "99112233P", "789-456-1235", "123-654-9872", "sofia.ruiz@example.com",
                "Calle Roja 33", "Valencia", "Comunidad Valenciana", "46002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "davidSanchez19", "davidPass789", "davidPass789", "david.sanchez19@example.com",
                "David", "Sánchez", "11223344Q", "654-123-7895", "789-456-3214", "david.san@example.com",
                "Calle del Río 4", "Sevilla", "Andalucía", "41002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "carlaLopez20", "carla789", "carla789", "carla.lopez20@example.com",
                "Carla", "López", "22334455R", "321-654-7892", "987-654-3215", "carla.alt@example.com",
                "Avenida Sur 78", "Bilbao", "País Vasco", "48002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "victorHernandez21", "victor123", "victor123", "victor.hernandez21@example.com",
                "Víctor", "Hernández", "33445566S", "123-789-4567", "456-123-7892", "victor.alt@example.com",
                "Paseo del Prado 67", "Málaga", "Andalucía", "29002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "martaRomero22", "marta456", "marta456", "marta.romero22@example.com",
                "Marta", "Romero", "44556677T", "987-654-1233", "123-987-6545", "marta.alt@example.com",
                "Calle Dorada 98", "Zaragoza", "Aragón", "50002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "alejandroDiaz23", "alejandro789", "alejandro789", "alejandro.diaz23@example.com",
                "Alejandro", "Díaz", "55667788U", "321-123-4565", "456-987-1235", "alejandro.alt@example.com",
                "Calle Norte 12", "Valladolid", "Castilla y León", "47002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "nataliaAlonso24", "natalia321", "natalia321", "natalia.alonso24@example.com",
                "Natalia", "Alonso", "66778899V", "456-987-3211", "789-123-4568", "natalia.alt@example.com",
                "Calle del Lago 15", "Granada", "Andalucía", "18002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "pabloVega25", "pablo654", "pablo654", "pablo.vega25@example.com",
                "Pablo", "Vega", "77889900W", "789-321-6549", "987-456-3212", "pablo.alt@example.com",
                "Avenida Mar 77", "Murcia", "Murcia", "30002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "martinSerrano26", "martin2024", "martin2024", "martin.serrano26@example.com",
                "Martín", "Serrano", "88990011X", "321-789-1237", "123-456-7893", "martin.ser@example.com",
                "Calle Primavera 24", "San Sebastián", "País Vasco", "20002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "claraMartinez27", "clara987", "clara987", "clara.martinez27@example.com",
                "Clara", "Martínez", "99001122Y", "123-654-9873", "789-654-1234", "clara.alt@example.com",
                "Calle Verano 19", "Alicante", "Comunidad Valenciana", "03002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "sergioOrtiz28", "sergio456", "sergio456", "sergio.ortiz28@example.com",
                "Sergio", "Ortiz", "11002233Z", "654-321-1234", "987-321-4567", "sergio.alt@example.com",
                "Calle Invierno 88", "Santander", "Cantabria", "39002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "lucasRamirez29", "lucas123", "lucas123", "lucas.ramirez29@example.com",
                "Lucas", "Ramírez", "22113344A", "789-654-3211", "123-987-6546", "lucas.alt@example.com",
                "Avenida Otoño 22", "Pamplona", "Navarra", "31002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "angelaSuarez30", "angela789", "angela789", "angela.suarez30@example.com",
                "Ángela", "Suárez", "33224455B", "123-789-6541", "654-987-1237", "angela.alt@example.com",
                "Calle Sol 29", "Badajoz", "Extremadura", "06002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "lauraJimenez31", "laura654", "laura654", "laura.jimenez31@example.com",
                "Laura", "Jiménez", "44335566C", "321-987-1238", "987-456-3213", "laura.alt@example.com",
                "Calle Loma 48", "Mérida", "Extremadura", "06800", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "robertoDiaz32", "roberto2024", "roberto2024", "roberto.diaz32@example.com",
                "Roberto", "Díaz", "55446677D", "789-123-4563", "654-789-1239", "roberto.alt@example.com",
                "Avenida Marítima 5", "Cádiz", "Andalucía", "11001", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "noeliaLopez33", "noelia123", "noelia123", "noelia.lopez33@example.com",
                "Noelia", "López", "66557788E", "456-321-7890", "321-654-9874", "noelia.alt@example.com",
                "Calle Bosque 59", "Toledo", "Castilla-La Mancha", "45002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "danielGil34", "daniel2024", "daniel2024", "daniel.gil34@example.com",
                "Daniel", "Gil", "77668899F", "123-456-1235", "789-123-6543", "daniel.alt@example.com",
                "Avenida Europa 11", "Logroño", "La Rioja", "26002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "andresRuiz35", "andres321", "andres321", "andres.ruiz35@example.com",
                "Andrés", "Ruiz", "88779900G", "987-123-4564", "321-456-7895", "andres.alt@example.com",
                "Calle Horizonte 7", "Gijón", "Asturias", "33202", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "elenaMartinez36", "elena987", "elena987", "elena.martinez36@example.com",
                "Elena", "Martínez", "99880011H", "654-987-3212", "123-789-4562", "elena.alt@example.com",
                "Avenida Central 21", "Vigo", "Galicia", "36202", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "adrianGutierrez37", "adrian456", "adrian456", "adrian.gutierrez37@example.com",
                "Adrián", "Gutiérrez", "11002233I", "789-456-7891", "654-321-9875", "adrian.alt@example.com",
                "Calle Victoria 43", "Salamanca", "Castilla y León", "37002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "lauraVidal38", "laura456", "laura456", "laura.vidal38@example.com",
                "Laura", "Vidal", "22114455J", "123-789-6540", "321-987-1236", "laura.alt@example.com",
                "Paseo Nuevo 87", "Oviedo", "Asturias", "33002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "javierMunoz39", "javier654", "javier654", "javier.munoz39@example.com",
                "Javier", "Muñoz", "33225566K", "987-123-9872", "456-123-7894", "javier.alt@example.com",
                "Avenida del Sol 13", "Palma", "Islas Baleares", "07002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "inesTorres40", "ines123", "ines123", "ines.torres40@example.com",
                "Inés", "Torres", "44336677L", "654-321-6540", "789-456-7896", "ines.alt@example.com",
                "Calle Blanca 3", "Huesca", "Aragón", "22002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "miguelCastro41", "miguel789", "miguel789", "miguel.castro41@example.com",
                "Miguel", "Castro", "55447788M", "321-987-3217", "123-456-6540", "miguel.alt@example.com",
                "Calle Alegre 44", "Tarragona", "Cataluña", "43002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "susanaBlanco42", "susana456", "susana456", "susana.blanco42@example.com",
                "Susana", "Blanco", "66558899N", "987-654-7890", "789-123-1234", "susana.alt@example.com",
                "Avenida Parque 71", "Santiago de Compostela", "Galicia", "15702", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "franciscoOrtega43", "francisco123", "francisco123", "francisco.ortega43@example.com",
                "Francisco", "Ortega", "77669900O", "654-789-4561", "456-123-9871", "francisco.alt@example.com",
                "Calle Norte 18", "Cáceres", "Extremadura", "10002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "carmenPereira44", "carmen789", "carmen789", "carmen.pereira44@example.com",
                "Carmen", "Pereira", "88770011P", "123-987-6548", "789-321-1239", "carmen.alt@example.com",
                "Calle Flor 60", "León", "Castilla y León", "24002", "España"
        ));

        customerList.add(new UserRegistrationRequest(
                "antonioPerez45", "antonio456", "antonio456", "antonio.perez45@example.com",
                "Antonio", "Pérez", "99881122Q", "321-123-6548", "654-321-7897", "antonio.alt@example.com",
                "Avenida Este 91", "Córdoba", "Andalucía", "14002", "España"
        ));
        return customerList;

    }
}