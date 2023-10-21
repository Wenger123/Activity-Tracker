package com.francis.Activity_Tracker.services.ServicesImpls;

import com.francis.Activity_Tracker.dtos.dtoRequests.ActivityDto;
import com.francis.Activity_Tracker.dtos.dtoResponses.ActivityDtoResponse;
import com.francis.Activity_Tracker.entities.User;
import com.francis.Activity_Tracker.enums.Status;
import com.francis.Activity_Tracker.exceptions.ActivityAlreadyExist;
import com.francis.Activity_Tracker.exceptions.ActivityNotFoundException;
import com.francis.Activity_Tracker.entities.Activity;
import com.francis.Activity_Tracker.exceptions.UnauthorizedUserException;
import com.francis.Activity_Tracker.exceptions.UserNotFoundException;
import com.francis.Activity_Tracker.repositories.ActivityRepository;
import com.francis.Activity_Tracker.repositories.UserRepository;
import com.francis.Activity_Tracker.services.ActivityService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Service
@Transactional
@Slf4j
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
     @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository,UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ActivityDtoResponse> getAllActivities(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + userId + "' not found"));

        List<Activity> activities = user.getTasks();

        return activities.stream()
                .map(activity -> new ActivityDtoResponse(
                        activity.getId(),
                        activity.getName(),
                        activity.getDescription(),
                        activity.getStatus(),
                        activity.getCreatedDate(),
                        activity.getDoneDate(),
                        activity.getUpDateDate(),
                        activity.getUser()
                ))
                .collect(Collectors.toList());
    }



    @Override
    public ActivityDtoResponse getActivityByIdAndName(Long id,String name) {
        log.info("Searching for activity with name: " + name);
        Optional<Activity> activityOptional = activityRepository.findActivityByIdAndName(id,name);
        if (activityOptional.isPresent()) {
            log.info("Activity found: " + name);
            Activity activity = activityOptional.get();
            return new ActivityDtoResponse(
                    activity.getId(),
                    activity.getName(),
                    activity.getDescription(),
                    activity.getStatus(),
                    activity.getCreatedDate(),
                    activity.getDoneDate(),
                    activity.getUpDateDate(),
                    activity.getUser()
            );
        } else {
            log.warn("Activity not found with name: " + name);
            throw new ActivityNotFoundException("Activity not found with name: " + name);
        }
    }

    @Override
    public void createActivity(ActivityDto activityDto, Long userId) {
        String activityName = activityDto.getName();

        // Check if an activity with the same name already exists
        if (activityRepository.existsByName(activityName)) {
            throw new ActivityAlreadyExist("Activity with the name '" + activityName + "' already exists.");
        }

        // Retrieve the user from the database using the user's ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + userId + "' not found"));

        // Create a new activity
        Activity activity = new Activity();
        activity.setName(activityDto.getName());
        activity.setDescription(activityDto.getDescription());
        activity.setStatus(activityDto.getStatus());
        activity.setUser(user);

        // Add the activity to the user's task list
        user.addTask(activity);

        // Save the activity and the user
        activityRepository.save(activity);
        userRepository.save(user);
    }




    @Override
    public void updateActivity(Long id, String name, Activity activity, Long userId) {
        Optional<Activity> activityOptional = activityRepository.findActivityByIdAndName(id, name);
        if (activityOptional.isPresent()) {
            Activity activitys = activityOptional.get();

            // Ensure that the user trying to update the activity is the same user who created it
            if (!activity.getUser().getId().equals(userId)) {
                throw new UnauthorizedUserException("You are not authorized to update this activity.");
            }

            activitys.setName(activity.getName());
            activitys.setDescription(activity.getDescription());
            activitys.setDoneDate(activity.getDoneDate());
            activitys.setCreatedDate(activity.getCreatedDate());
            activitys.setUpDateDate(activity.getUpDateDate());
            activityRepository.saveAndFlush(activitys);
        } else {
            throw new ActivityNotFoundException("Activity not found with ID " + id + " and name " + name);
        }
    }


    @Override
    public void deleteActivity(Long id, String name) {
         Optional<Activity> activityOptional = activityRepository.findActivityByIdAndName(id, name);
         if(activityOptional.isPresent()){
             activityRepository.delete(activityOptional.get());
         }else {
             throw new ActivityNotFoundException("Activity not find with "+ id +"and "+ name);
         }
    }

    @Override
    public List<ActivityDtoResponse> getAllPending(Status status, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + userId + "' not found"));
        List<Activity> activities = user.getTasks(); // Assuming this gets the user's activities

        List<ActivityDtoResponse> pendingActivities = activities.stream()
                .filter(activity -> activity.getStatus() == Status.PENDING) // Filter by pending status
                .map(activity -> new ActivityDtoResponse(
                        activity.getId(),
                        activity.getName(),
                        activity.getDescription(),
                        activity.getStatus(),
                        activity.getCreatedDate(),
                        activity.getDoneDate(),
                        activity.getUpDateDate(),
                        activity.getUser()))
                .collect(Collectors.toList());

        if (pendingActivities.isEmpty()) {
            throw new ActivityNotFoundException("No pending activities found for the user.");
        }

        return pendingActivities;
    }

    @Override
    public List<ActivityDtoResponse> getAllInProgress(Status status, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + userId + "' not found"));
        List<Activity> activities = user.getTasks(); // Assuming this gets the user's activities

        List<ActivityDtoResponse> InProgressActivities = activities.stream()
                .filter(activity -> activity.getStatus() == Status.IN_PROGRESS) // Filter by pending status
                .map(activity -> new ActivityDtoResponse(
                        activity.getId(),
                        activity.getName(),
                        activity.getDescription(),
                        activity.getStatus(),
                        activity.getCreatedDate(),
                        activity.getDoneDate(),
                        activity.getUpDateDate(),
                        activity.getUser()))
                .toList();

        if (InProgressActivities.isEmpty()) {
            throw new ActivityNotFoundException("No pending activities found for the user.");
        }

        return InProgressActivities;
    }

    @Override
    public List<ActivityDtoResponse> getAllDone(Status status, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID '" + userId + "' not found"));
        List<Activity> activities = user.getTasks(); // Assuming this gets the user's activities

        List<ActivityDtoResponse> doneActivities = activities.stream()
                .filter(activity -> activity.getStatus() == Status.DONE) // Filter by pending status
                .map(activity -> new ActivityDtoResponse(
                        activity.getId(),
                        activity.getName(),
                        activity.getDescription(),
                        activity.getStatus(),
                        activity.getCreatedDate(),
                        activity.getDoneDate(),
                        activity.getUpDateDate(),
                        activity.getUser()))
                .toList();

        if (doneActivities.isEmpty()) {
            throw new ActivityNotFoundException("No pending activities found for the user.");
        }

        return doneActivities;
    }

    @Override
    public Boolean updateActivityQuery(String newName, String newDesc, Status newStatus, Long taskId, Long userId) {
        try {
            activityRepository.updateActivityQuery(newName, newDesc, newStatus, taskId, userId);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
