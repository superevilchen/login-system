package com.example.loginsystem.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RegistrationController.BASE_PATH)
@RequiredArgsConstructor
public class RegistrationController {

    protected static final String BASE_PATH = "api/v1/registration";
 //   private final AppUserService appUserService;
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) throws Exception {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam String token){
        return registrationService.confirmToken(token);
    }


}
