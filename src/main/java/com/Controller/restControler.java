package com.Controller;

import com.JwTUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.RoleApp;
import com.entities.UserApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Service.serviceRoleUserImp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class restControler {

    @Autowired
    serviceRoleUserImp service;

    @GetMapping(path = "/AllUsers")
        //Privilege config  @PostAuthorize("hasAnyAuthority('ADMIN')")
    List<UserApp> getAllUSer() {
        return service.ConsulterAllUsers();
    }

    @GetMapping(path = "/AllRoles")
    List<RoleApp> getAllRolesr() {
        return service.ConsulterAllRoles();
    }

    @PostMapping(path = "/saveUser")
        // Privilege config @PreAuthorize("hasAuthority('ADMIN')")
    UserApp saveUser(@RequestBody UserApp user) {
        return service.AddUSer(user);
    }

    @PostMapping(path = "/saveRole")
    RoleApp saveRole(@RequestBody RoleApp roleApp) {
        return service.AddRoleApp(roleApp);
    }

    @PostMapping(path = "/addRoleToUser")
    void saveRoleToUser(@RequestBody formUserRole f) {
        service.AddRoleToUSer(f.getNom(), f.getRole());
    }

    @GetMapping(path = "/RefreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception { //Refreshing the access token after verifying the refresh token
        String authToken = request.getHeader("Authorization");
        if (authToken != null && authToken.startsWith("Bearer ")) {
            try {
                String jwt = authToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JwTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                UserApp userApp = service.ConsultUserByName(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(userApp.getNom())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwTUtil.EXPIRE_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", userApp.getRoleApps().stream().map(r -> r.getRole()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> idToken = new HashMap<>();
                idToken.put("access-token", jwtAccessToken);
                idToken.put("refresh-token", jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new RuntimeException("Refresh token required");
        }
    }

    @GetMapping(path = "/profile")
    UserApp profile(Principal principal) {
        return service.ConsultUserByName(principal.getName());
    }
}

@Data
class formUserRole {
    public String nom;
    public String role;
}
