package dev.kayange.projectmanagementsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class PaymentLinkResponse {
    private String payment_link_url;
    private String payment_link_id;


}
