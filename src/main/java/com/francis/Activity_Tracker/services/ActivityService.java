package com.francis.Activity_Tracker.services;

import com.francis.Activity_Tracker.dtos.dtoRequests.ActivityDto;
import com.francis.Activity_Tracker.dtos.dtoResponses.ActivityDtoResponse;
import com.francis.Activity_Tracker.entities.Activity;
import com.francis.Activity_Tracker.enums.Status;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityService {
    List<ActivityDtoResponse> getAllActivities(Long userId);
    ActivityDtoResponse getActivityByIdAndName(Long id,String name);
    void createActivity(ActivityDto activityDto, Long id);
    void updateActivity(Long id, String name, Activity activity, Long userId);
    void deleteActivity(Long id, String name);
    List<ActivityDtoResponse> getAllPending(Status status, Long userId);
    List<ActivityDtoResponse> getAllInProgress(Status status, Long userId);
    List<ActivityDtoResponse> getAllDone(Status status, Long userId);
    Boolean updateActivityQuery(
            String newName,
            String newDesc,
            Status newStatus,
            Long taskId,
            Long userId
    );
}
