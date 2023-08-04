package uz.pdp.citynotificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.citynotificationservice.dto.MailDto;


@Service
@RequiredArgsConstructor
public class MailSendingService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public String sendMessage(MailDto mailDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("Verification");
        simpleMailMessage.setTo(mailDto.getEmail());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setText(mailDto.getMessage());
        javaMailSender.send(simpleMailMessage);
        return "OK";
    }
}
