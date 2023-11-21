package nvt.st.springjwtauthentication.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private  String token;
    private String type = "Bearer";
    private String email;
    private List<String> listRoles;

    public JwtResponse(String token, String email,List<String> listRoles) {
        this.token = token;
        this.email = email;
        this.listRoles = listRoles;
    }
}
