package uz.pdp.citynotificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.citynotificationservice.dto.MailDto;
import uz.pdp.citynotificationservice.service.MailSendingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class MailSendingController {
    private final MailSendingService mailSendingService;

    @PostMapping("/send-single")
    public ResponseEntity<String> sendSingle(@RequestBody MailDto mailDto) {
        return ResponseEntity.ok(mailSendingService.sendMessage(mailDto));
    }
}
