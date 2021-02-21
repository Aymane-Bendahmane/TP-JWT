package com.Service;

import com.entities.RoleApp;
import com.entities.UserApp;

import java.util.List;

public interface serviceRoleUser {
    UserApp AddUSer(UserApp user);

    RoleApp AddRoleApp(RoleApp roleApp);

    UserApp AddRoleToUSer(String user, String roleApp);

    UserApp ConsultUserByName(String s);

    List<UserApp> ConsulterAllUsers();

    List<RoleApp> ConsulterAllRoles();
}
