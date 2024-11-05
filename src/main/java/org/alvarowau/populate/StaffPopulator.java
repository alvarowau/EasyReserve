package org.alvarowau.populate;

import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StaffPopulator {

    public List<UserRegistrationRequest> createStaff(){
        List<UserRegistrationRequest> staffList = new ArrayList<>();
        staffList.add(new UserRegistrationRequest(
                "alvaro-wau", "1234", "1234", "alvaro@wau.com",
                "Alvaro", "Wau", "11223344R", "789-123-7890", "456-789-1238", "wau@alvaro.com",
                "Calle Sur 8", "Madrid", "Madrid", "41002", "España"
        ));

        staffList.add(new UserRegistrationRequest(
                "victorRomero47", "victor456", "victor456", "victor.romero47@example.com",
                "Víctor", "Romero", "22334455S", "654-321-9879", "123-456-7898", "victor.alt@example.com",
                "Avenida Montaña 20", "Santander", "Cantabria", "39002", "España"
        ));

        staffList.add(new UserRegistrationRequest(
                "luisaMolina48", "luisa789", "luisa789", "luisa.molina48@example.com",
                "Luisa", "Molina", "33445566T", "321-654-9870", "789-123-4567", "luisa.alt@example.com",
                "Calle Azul 12", "Murcia", "Murcia", "30002", "España"
        ));

        staffList.add(new UserRegistrationRequest(
                "alvaroSanz49", "alvaro321", "alvaro321", "alvaro.sanz49@example.com",
                "Álvaro", "Sanz", "44556677U", "987-654-1234", "654-789-3210", "alvaro.alt@example.com",
                "Avenida Libertad 33", "Granada", "Andalucía", "18002", "España"
        ));
        return staffList;
    }
}