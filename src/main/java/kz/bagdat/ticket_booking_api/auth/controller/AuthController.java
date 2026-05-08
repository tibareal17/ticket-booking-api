package kz.bagdat.ticket_booking_api.auth.controller;

import jakarta.validation.Valid;
import kz.bagdat.ticket_booking_api.auth.dto.AuthMessageResponse;
import kz.bagdat.ticket_booking_api.auth.dto.LoginRequest;
import kz.bagdat.ticket_booking_api.auth.dto.RegisterRequest;
import kz.bagdat.ticket_booking_api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthMessageResponse register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return new AuthMessageResponse("User registered successfully");
    }

    @PostMapping("/login")
    public AuthMessageResponse login(@Valid @RequestBody LoginRequest request) {
        authService.login(request);
        return new AuthMessageResponse("Login successful");
    }
}
