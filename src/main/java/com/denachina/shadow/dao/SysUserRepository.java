package com.denachina.shadow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("SysUserRepository")
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {
    SysUser findByUsernameAndPassword(String username, String password);
}
