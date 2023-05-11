package com.denachina.shadow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SysUserRepository")
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {
    /**
     * Find by user name and password sys user.
     *
     * @param userName the user name
     * @param password the password
     * @return the sys user
     */
    SysUser findByUserNameAndPassword(String userName, String password);
}
