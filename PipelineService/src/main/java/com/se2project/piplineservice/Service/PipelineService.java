package com.se2project.piplineservice.Service;

import com.se2project.piplineservice.Repository.*;

import com.se2project.piplineservice.entity.Update;
import com.se2project.piplineservice.exception.BadRequestException;
import com.se2project.piplineservice.model.State;
import com.se2project.piplineservice.model.TransitionLog;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PipelineService {

    private final UpdateRepository updateRepo;
    private final TransitionLogRepository logRepo;

    public PipelineService(
            UpdateRepository updateRepo,
            TransitionLogRepository logRepo
    ) {
        this.updateRepo = updateRepo;
        this.logRepo = logRepo;
    }


    // create Update and make it in modifying state initially
    public Update create(String title, String user) {

        Update u = new Update();

        u.setTitle(title);
        u.setCreatedBy(user);
        u.setCurrentState(State.MODIFYING);
        u.setTestsPassed(false);

        u.setCreatedAt(LocalDateTime.now());
        u.setUpdatedAt(LocalDateTime.now());

        return updateRepo.save(u);
    }

    // get update by id
    public Update get(Long id) {

        return updateRepo.findById(id)
                .orElseThrow(() ->
                        new BadRequestException("Update not found"));
    }

    // move to testing
    public Update moveToTesting(Long id) {

        Update u = get(id);

        if (u.getCurrentState() != State.MODIFYING) {
            throw new BadRequestException("Invalid transition");
        }

        log(u, State.TESTING);

        u.setCurrentState(State.TESTING);
        u.setUpdatedAt(LocalDateTime.now());

        return updateRepo.save(u);
    }

    // move to review after tests passed
    public Update moveToReview(Long id) {

        Update u = get(id);

        if (u.getCurrentState() != State.TESTING || !u.isTestsPassed()) {
            throw new BadRequestException("Tests must pass first");
        }

        log(u, State.REVIEW);

        u.setCurrentState(State.REVIEW);
        u.setUpdatedAt(LocalDateTime.now());

        return updateRepo.save(u);
    }

    // return to modifying
    public Update moveToModifying(Long id) {

        Update u = get(id);

        if (u.getCurrentState() != State.TESTING
                && u.getCurrentState() != State.REVIEW) {

            throw new BadRequestException("Invalid transition");
        }

        log(u, State.MODIFYING);

        u.setCurrentState(State.MODIFYING);
        u.setTestsPassed(false);

        u.setUpdatedAt(LocalDateTime.now());

        return updateRepo.save(u);
    }

    // merge update
    public Update merge(Long id) {

        Update u = get(id);

        if (u.getCurrentState() != State.REVIEW) {
            throw new BadRequestException("Only review can merge");
        }

        log(u, State.MERGED);

        u.setCurrentState(State.MERGED);
        u.setUpdatedAt(LocalDateTime.now());

        return updateRepo.save(u);
    }

    // reject update
    public Update reject(Long id) {

        Update u = get(id);

        if (u.getCurrentState() != State.REVIEW
                && u.getCurrentState() != State.TESTING) {

            throw new BadRequestException("Invalid transition");
        }

        log(u, State.REJECTED);

        u.setCurrentState(State.REJECTED);
        u.setUpdatedAt(LocalDateTime.now());

        return updateRepo.save(u);
    }

    public Update setTestResult(Long id, boolean passed) {

        Update u = get(id);

        u.setTestsPassed(passed);
        u.setUpdatedAt(LocalDateTime.now());

        return updateRepo.save(u);
    }

    private void log(Update u, State toState) {

        TransitionLog log = new TransitionLog();

        log.setUpdateId(u.getId());
        log.setFromState(u.getCurrentState());
        log.setToState(toState);
        log.setTimestamp(LocalDateTime.now());

        logRepo.save(log);
    }
    public List<Update> getAll() {
    return updateRepo.findAll();
}
}
