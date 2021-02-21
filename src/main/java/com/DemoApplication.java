package com;

import com.Service.serviceRoleUserImp;
import com.entities.RoleApp;
import com.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
// Privilege config @EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class DemoApplication implements CommandLineRunner {
    @Autowired
    serviceRoleUserImp service;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {

        RoleApp r1 = service.AddRoleApp(new RoleApp(null, "USER"));
        RoleApp r2 = service.AddRoleApp(new RoleApp(null, "ADMIN"));
        RoleApp r3 = service.AddRoleApp(new RoleApp(null, "CUSTOMER_MANAGER"));
        RoleApp r4 = service.AddRoleApp(new RoleApp(null, "PRODUCT_MANAGER"));
        RoleApp r5 = service.AddRoleApp(new RoleApp(null, "BILLS_MANAGER"));


        UserApp u1 = service.AddUSer(new UserApp(null, "Benjoudie", "Taher", "1234", null));
        UserApp u2 = service.AddUSer(new UserApp(null, "Bendahmane", "Aymane", "1234", null));
        UserApp u3 = service.AddUSer(new UserApp(null, "Benharouga", "Hassan", "1234", null));


        service.AddRoleToUSer("Benjoudie", "USER");
        service.AddRoleToUSer("Benjoudie", "ADMIN");

        service.AddRoleToUSer("Bendahmane", "CUSTOMER_MANAGER");
        service.AddRoleToUSer("Bendahmane", "PRODUCT_MANAGER");
        service.AddRoleToUSer("Benharouga", "BILLS_MANAGER");


        System.out.println(service.ConsulterAllRoles().toString());

        System.out.println(service.ConsulterAllUsers().toString());
    }
}
