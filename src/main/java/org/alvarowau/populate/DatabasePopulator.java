package org.alvarowau.populate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.service.ProviderAccountService;
import org.alvarowau.user.service.UserSignUpService;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class DatabasePopulator {

    private final UserSignUpService userSignUpService;
    private final ProviderPopulate providerPopulate;
    private final CustomerPopulate customerPopulate;
    private final StaffPopulate staffPopulate;
    private final ServiceOfferingPopulate serviceOfferingPopulate;
    private final ScheduleServicePopulate scheduleServicePopulate;
    private final BookingPopulateService bookingPopulateService;
    private final FeedbackPopulate feedbackPopulate;
    private final ProviderAccountService providerService;

    @PostConstruct
    public void populateDatabase() {
        if (!providerService.existsRecords()) {
            List<UserRegistrationRequest> listStaff = staffPopulate.createStaff();
            listStaff.forEach(a -> userSignUpService.registerUser(a, RoleEnum.STAFF));
            List<UserRegistrationRequest> listProvider = providerPopulate.createProvider();
            listProvider.forEach(a -> userSignUpService.registerUser(a, RoleEnum.PROVIDER));
            List<UserRegistrationRequest> listCustomer = customerPopulate.createCustomer();
            listCustomer.forEach(a -> userSignUpService.registerUser(a, RoleEnum.CUSTOMER));
            serviceOfferingPopulate.createServiceOfferings(listProvider);
            scheduleServicePopulate.createScheduleService();
            bookingPopulateService.createBooking();
            bookingPopulateService.cancelBookings();
            feedbackPopulate.createFeedbacks();
        }
    }
}