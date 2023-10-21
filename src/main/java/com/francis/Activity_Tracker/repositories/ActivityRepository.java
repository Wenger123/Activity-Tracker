package com.francis.Activity_Tracker.repositories;

import com.francis.Activity_Tracker.entities.Activity;
import com.francis.Activity_Tracker.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Optional<Activity> findActivityByIdAndName(Long id,String name);
    Optional<Activity> findActivityByStatus(Status status);
    Boolean existsByName(String activityName);
    @Modifying
    @Transactional
    @Query("UPDATE Activity a SET a.name = :newName, a.description = :newDesc, a.status = :newStatus WHERE a.id = :taskId AND a.user.id = :userId")
    void updateActivityQuery(
            @Param("newName") String newName,
            @Param("newDesc") String newDesc,
            @Param("newStatus") Status newStatus,
            @Param("taskId") Long taskId,
            @Param("userId") Long userId
    );
}
