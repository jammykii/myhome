package com.godcoder.myhome.controller;

import com.godcoder.myhome.dto.UserRoleApiDTO;
import com.godcoder.myhome.repository.RoleRepository;
import com.godcoder.myhome.repository.AccountRepository;
import com.godcoder.myhome.repository.UserRoleRepository;
//import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRoleApiController {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/userRole")
    List<UserRoleApiDTO> all(){
        List<UserRoleApiDTO> userRoles = userRoleRepository.findAllAboutUserRoles();
        return  userRoles;
    }

    @DeleteMapping("/userRole")
    void deleteBoard(@RequestParam long userid, @RequestParam long roleid) {
        userRoleRepository.deleteByUserIdAndRoleId(userid, roleid);
    }

}
