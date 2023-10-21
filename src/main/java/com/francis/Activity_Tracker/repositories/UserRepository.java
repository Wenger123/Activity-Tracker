package com.francis.Activity_Tracker.repositories;

import com.francis.Activity_Tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long userId);
    Optional<User> findByEmail( String email);
    User getUserByEmail( String email);
    Optional<User> deleteByIdAndEmail(Long id, String email);

    @Modifying
    @Transactional
    @Query("UPDATE User a SET a.First_name = :newFirstName, a.Last_name = :newLastName, a.phone_number = :newPhoneNumber, a.password = :newPassword WHERE a.email = :userEmail ")
    void updateUserQuery(
            @Param("newFirstName") String newFirstName,
            @Param("newLastName") String newLastName,
            @Param("newPhoneNumber") String newPhoneNumber,
            @Param("newPassword") String newPassword,
            @Param("userEmail") String userEmail
    );
}
