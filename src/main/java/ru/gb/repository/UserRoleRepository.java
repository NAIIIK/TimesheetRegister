package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.entity.UserRole;
import ru.gb.entity.UserRoleId;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

    @Query(nativeQuery = true, value = "select role_id from users_roles where user_id = :userId")
    List<Long> findUserRolesByUserId(Long userId);
}
