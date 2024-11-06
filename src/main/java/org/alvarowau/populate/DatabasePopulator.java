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
    private final ProviderPopulator providerPopulator;
    private final CustomerPopulator customerPopulator;
    private final StaffPopulator staffPopulator;
    private final ServiceOfferingPopulator serviceOfferingPopulator;
    private final ScheduleServicePopulator scheduleServicePopulator;
    private final BookingPopulator bookingPopulator;
    private final FeedbackPopulator feedbackPopulator;
    private final ProviderAccountService providerService;

    @PostConstruct
    public void populateDatabase() {
        if(!providerService.existsRecords()) {
            List<UserRegistrationRequest> listStaff = staffPopulator.createStaff();
            listStaff.forEach(a -> userSignUpService.registerUser(a, RoleEnum.STAFF));
            List<UserRegistrationRequest> listProvider = providerPopulator.createProvider();
            listProvider.forEach(a -> userSignUpService.registerUser(a, RoleEnum.PROVIDER));
            List<UserRegistrationRequest> listCustomer = customerPopulator.createCustomer();
            listCustomer.forEach(a -> userSignUpService.registerUser(a, RoleEnum.CUSTOMER));
            serviceOfferingPopulator.createServiceOfferings(listProvider);
            scheduleServicePopulator.createScheduleService();
            bookingPopulator.createBooking();
            bookingPopulator.cancelBookings();
            feedbackPopulator.createFeedbacks();
        }
    }
}