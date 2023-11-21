package nvt.st.springjwtauthentication.controller;

import nvt.st.springjwtauthentication.entity.Erole;
import nvt.st.springjwtauthentication.entity.Roles;
import nvt.st.springjwtauthentication.entity.User;
import nvt.st.springjwtauthentication.jwt.JwtTokenProvider;
import nvt.st.springjwtauthentication.payload.request.LoginRequest;
import nvt.st.springjwtauthentication.payload.request.SignUpRequest;
import nvt.st.springjwtauthentication.payload.response.JwtResponse;
import nvt.st.springjwtauthentication.payload.response.MessageResponse;
import nvt.st.springjwtauthentication.security.CustomUserDetail;
import nvt.st.springjwtauthentication.service.RoleService;
import nvt.st.springjwtauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email already use for an another account!"));
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if(listRoles == null) {
            // set role default : user
            Roles userRole = roleService.findByRoleName(Erole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role if not found!"));
            listRoles.add(userRole);
        }else {
           if(strRoles != null) {
               strRoles.forEach(role->{
                   switch (role){
                       case "admin":
                           Roles adminRole = roleService.findByRoleName(Erole.ROLE_ADMIN)
                                   .orElseThrow(()-> new RuntimeException("Error: Role is not found!"));
                           listRoles.add(adminRole);
                       case "moderator":
                           Roles mdRole = roleService.findByRoleName(Erole.ROLE_MODERATOR)
                                   .orElseThrow(()->new RuntimeException("Error: Role is not found!"));
                           listRoles.add(mdRole);
                       case "user" :
                           Roles userRole = roleService.findByRoleName(Erole.ROLE_USER)
                                   .orElseThrow(()->new RuntimeException("Error: Role is not found!"));
                           listRoles.add(userRole);
                   }
               });
           }else {
               Roles userRole = roleService.findByRoleName(Erole.ROLE_USER)
                       .orElseThrow(()->new RuntimeException("Error: Role is not found!"));
               listRoles.add(userRole);
           }
        }
        user.setRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(customUserDetail);
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetail.getEmail(),
                listRoles));
    }

}
