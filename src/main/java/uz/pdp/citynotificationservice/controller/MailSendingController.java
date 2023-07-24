package uz.pdp.citynotificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.citynotificationservice.dto.MailDto;
import uz.pdp.citynotificationservice.dto.Verification;
import uz.pdp.citynotificationservice.service.MailSendingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class MailSendingController {
    private final MailSendingService mailSendingService;

    @GetMapping("/send-single")
    public ResponseEntity<Verification> sendSingle(@RequestBody MailDto mailDto) {
        return ResponseEntity.ok(mailSendingService.sendMessage(mailDto));
    }
}
