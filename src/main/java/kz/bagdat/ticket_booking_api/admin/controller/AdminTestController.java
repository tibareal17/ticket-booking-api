package kz.bagdat.ticket_booking_api.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminTestController {

    @GetMapping("/admin/test")
    public String adminTest() {
        return "Admin access granted";
    }
}