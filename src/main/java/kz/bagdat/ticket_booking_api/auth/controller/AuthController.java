package kz.bagdat.ticket_booking_api.auth.controller;

import jakarta.validation.Valid;
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
    public String register(@Valid @RequestBody RegisterRequest request){
        authService.register(request);
        return "User registered successfully";
    }
}
