package org.lg.upone.login.controller;

import jakarta.validation.Valid;
import org.lg.engine.core.client.enctypt.Decrypt;
import org.lg.engine.core.client.model.WfUser;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.upone.login.db.model.ERole;
import org.lg.upone.login.db.model.Roles;
import org.lg.upone.login.db.model.User;
import org.lg.upone.login.db.service.impl.RolesService;
import org.lg.upone.login.db.service.impl.UserService;
import org.lg.upone.login.model.req.LoginRequest;
import org.lg.upone.login.model.req.SignupRequest;
import org.lg.upone.login.model.res.MessageResponse;
import org.lg.upone.login.model.res.UserLoginResponse;
import org.lg.upone.login.security.jwt.JwtUtils;
import org.lg.upone.login.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired(required = false)
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolesService roleService;

    @Autowired(required = false)
    PasswordEncoder encoder;

    @Autowired(required = false)
    JwtUtils jwtUtils;

    @PostMapping("/login")
    @Decrypt
    public ResponseEntity<?> authenticateUser(@Valid @ModelAttribute LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        UserLoginResponse userLoginResponse = new UserLoginResponse();

        WfUser user = new WfUser();
        user.setDeptId("deptid");
        user.setDeptName("XX部");
        user.setOrgId("aaa");
        user.setOrgName("XX公司");
        user.setName(userDetails.getUsername());
        user.setId(userDetails.getId().toString());
        userLoginResponse.setUser(user);
        userLoginResponse.setPermissions(new ArrayList<>(0));
        userLoginResponse.setRoles(roles);

        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userLoginResponse);
    }

    @PostMapping("/signup")
    @Decrypt
    public ApiResult<?> registerUser(@Valid @ModelAttribute SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ApiResult.fail("账户已经注册",500);
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

//        if (userService.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//        }

        // Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())

        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Roles> roles = new HashSet<>();

        if (strRoles == null) {
            Roles userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Roles is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Roles is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Roles modRole = roleService.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Roles is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Roles userRole = roleService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Roles is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userService.insert(user);
        return ApiResult.success();
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
