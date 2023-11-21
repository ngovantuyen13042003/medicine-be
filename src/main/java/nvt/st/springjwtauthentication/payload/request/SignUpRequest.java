package nvt.st.springjwtauthentication.payload.request;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class SignUpRequest {
    private String email;
    private String password;
    private Set<String> listRoles;

}
