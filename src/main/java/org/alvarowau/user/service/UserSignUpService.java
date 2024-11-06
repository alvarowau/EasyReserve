package org.alvarowau.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alvarowau.exception.user.InvalidRoleException;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.model.dto.LoginResponse;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.dto.mapper.UserEntityMapper;
import org.alvarowau.user.model.entity.UserEntity;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.service.util.AuthenticationHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

    private final CustomerAccountService customerService;
    private final ProviderAccountService providerService;
    private final StaffAccountService staffService;
    private final UserEntityMapper userEntityMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse registerUser(@Valid UserRegistrationRequest request, RoleEnum role) {
        AuthenticationHelper.validatePasswordsMatch(request.password(), request.passwordRepeat());

        UserEntity userEntity = switch (role) {
            case RoleEnum.CUSTOMER -> customerService.saveEntity(userEntityMapper.convertToCustomer(request));
            case RoleEnum.PROVIDER -> providerService.saveEntity(userEntityMapper.convertToProvider(request));
            case RoleEnum.STAFF -> staffService.saveEntity(userEntityMapper.convertToStaff(request));
            default -> throw new InvalidRoleException("Rol no válido para crear el usuario");
        };

        return buildAuthResponse(userEntity);
    }

    private LoginResponse buildAuthResponse(UserEntity entity) {
        Authentication authentication = AuthenticationHelper.createAuthenticationToken(
                entity.getUsername(), entity.getPassword(), entity.getRole().name());
        String accessToken = jwtTokenProvider.createToken(authentication);
        return new LoginResponse(entity.getUsername(), "Usuario creado con éxito", accessToken, true);
    }
}
