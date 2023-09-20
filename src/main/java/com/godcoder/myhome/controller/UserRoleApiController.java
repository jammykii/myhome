package com.godcoder.myhome.controller;

import com.godcoder.myhome.dto.UserRoleApiDTO;
import com.godcoder.myhome.model.UserRole;
import com.godcoder.myhome.repository.RoleRepository;
import com.godcoder.myhome.repository.UserRepository;
import com.godcoder.myhome.repository.UserRoleRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class UserRoleApiController {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/userRole")
    List<UserRoleApiDTO> all(){
//        List<UserRole> userRoles = null;
        List<UserRoleApiDTO> userRoles = userRoleRepository.findAllAboutUserRoles();
//        Map<String, E> result = new HashMap<>();
//        userRoles
//        result.put("id", userRoles.get(1).);
//        for (int i = 0; i > userRoles.)
        return  userRoles;
    }

//    @Secured("TOTAL_ADMIN")
    @DeleteMapping("/userRole")
    void deleteBoard(@RequestParam long userid, @RequestParam long roleid) {
        userRoleRepository.deleteByUserIdAndRoleId(userid, roleid);
    }

}
