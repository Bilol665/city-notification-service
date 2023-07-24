package uz.pdp.citynotificationservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Verification {
    private String code;
    private String link;
    private String userEmail;
}
