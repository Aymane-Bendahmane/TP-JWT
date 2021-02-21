package com.Service;

import com.entities.RoleApp;
import com.entities.UserApp;
import com.repositories.RoleAppRepository;
import com.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class serviceRoleUserImp implements serviceRoleUser {
    RoleAppRepository roleAppRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public serviceRoleUserImp(RoleAppRepository roleAppRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleAppRepository = roleAppRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserApp AddUSer(UserApp user) {
        String g = user.getPassword();
        user.setPassword(passwordEncoder.encode(g));
        return userRepository.save(user);
    }

    @Override
    public RoleApp AddRoleApp(RoleApp roleApp) {
        return roleAppRepository.save(roleApp);
    }

    @Override
    public UserApp AddRoleToUSer(String nom, String role) {

        UserApp user = this.ConsultUserByName(nom);
        RoleApp roleApp = roleAppRepository.findByRole(role);

        Collection<RoleApp> roleApps = user.getRoleApps();
        roleApps.add(roleApp);
        user.setRoleApps(roleApps);

       return userRepository.save(user);
    }

    @Override
    public UserApp ConsultUserByName(String s) {
        return userRepository.findByNom(s);
    }

    @Override
    public List<UserApp> ConsulterAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<RoleApp> ConsulterAllRoles() {
        return roleAppRepository.findAll();
    }
}
