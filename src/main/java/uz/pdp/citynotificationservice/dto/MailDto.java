package uz.pdp.citynotificationservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MailDto {
    private String message;
    private String email;
    private String link;
}
