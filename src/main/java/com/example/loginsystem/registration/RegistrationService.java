package com.example.loginsystem.registration;

import com.example.loginsystem.appusers.AppUser;
import com.example.loginsystem.appusers.UserRole;
import com.example.loginsystem.registration.RegistrationRequest;
import com.example.loginsystem.services.AppUserService;
import com.example.loginsystem.services.ConfirmationTokenService;
import com.example.loginsystem.tokens.ConfirmationToken;
import com.example.loginsystem.utils.EmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) throws Exception {
//
        // TODO - REGEX NOT WORKING
//        if (!EmailValidator.isEmailValid(request)){
//            throw new Exception();
//        }
        return appUserService.signupUser(new AppUser(
                UserRole.USER,
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword())); // change later

    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }


}
