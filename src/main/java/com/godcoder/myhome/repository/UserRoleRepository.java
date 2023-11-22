package com.godcoder.myhome.repository;

import com.godcoder.myhome.dto.UserRoleApiDTO;
import com.godcoder.myhome.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    public static final String insertUserRole =
            "INSERT INTO USER_ROLE ( USER_ID, ROLE_ID) VALUES (:userId, :roleId)";
    @Transactional
    @Modifying
    @Query(value = insertUserRole, nativeQuery = true)
    Integer UserRoleSave(long userId, long roleId);

    public static final String deleteUserRole =
            "DELETE FROM USER_ROLE WHERE user_id = :userId AND role_id = :roleId";
    @Transactional
    @Modifying
    @Query(value = deleteUserRole, nativeQuery = true)
    void deleteByUserIdAndRoleId(long userId, long roleId);

    public static final String findAllUserRoles =
            "SELECT ur.user_id AS userId, u.username AS username, ur.role_id AS roleId, r.name AS rolename " +
            "FROM user_role ur, `user` u, `role` r WHERE ur.user_id = u.id AND ur.role_id = r.id";

    @Query(value = findAllUserRoles, nativeQuery = true)
    List<UserRoleApiDTO> findAllAboutUserRoles();

    @Transactional
    void deleteByUserId(long userId);
}
