package uz.pdp.citynotificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.citynotificationservice.dto.MailDto;
import uz.pdp.citynotificationservice.dto.Verification;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSendingService {
    private final Random random = new Random(10000);
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public Verification sendMessage(MailDto mailDto) {
        Long l = (long) random.nextInt(10000);
        Verification verification = Verification.builder()
                .code(String.valueOf(l))
                .link(mailDto.getLink())
                .userEmail(mailDto.getEmail())
                .build();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailDto.getEmail());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setText(mailDto.getMessage());
        javaMailSender.send(simpleMailMessage);
        return verification;
    }
}
