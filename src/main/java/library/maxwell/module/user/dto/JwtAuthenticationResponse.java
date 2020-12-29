package library.maxwell.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

	private String token;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String jwt) {
        this.token = jwt;
    }
}
