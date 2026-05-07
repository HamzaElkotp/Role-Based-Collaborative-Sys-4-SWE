package com.se2project.piplineservice.Service;

import com.se2project.piplineservice.Piplinedto.UpdateDTO;
import com.se2project.piplineservice.Repository.*;
import com.se2project.piplineservice.entity.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.se2project.piplineservice.model.*;
import com.se2project.piplineservice.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class PipelineService {
    @Autowired
    PiplineRepository piplineRepository;
    private final UpdateRepository updateRepo;
    private final TransitionLogRepository logRepo;

    public PipelineService(UpdateRepository updateRepo, TransitionLogRepository logRepo) {
        this.updateRepo = updateRepo;
        this.logRepo = logRepo;
    }
    // create Update nd make it in modifying state intially
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
// fun to get Update by id
    public Update get(Long id) {
        return updateRepo.findById(id)
                .orElseThrow(() -> new BadRequestException("Update not found"));
    }
// fun to move Update To Testing
    public Update moveToTesting(Long id) {
        Update u = get(id);

        if (u.getCurrentState() != State.MODIFYING)
            throw new BadRequestException("Invalid transition");

        log(u, State.TESTING);
        u.setCurrentState(State.TESTING);
        u.setUpdatedAt(LocalDateTime.now());

        return updateRepo.save(u);
    }

// fun to move Update To Review after pass Testing
    public Update moveToReview(Long id) {
        Update u = get(id);

        if (u.getCurrentState() != State.TESTING || !u.isTestsPassed())
            throw new BadRequestException("Tests must pass first");

        log(u, State.REVIEW);
        u.setCurrentState(State.REVIEW);

        return updateRepo.save(u);
    }
// fun to move Update To Modifying after fail Testing or fail Review
    public Update moveToModifying(Long id) {
        Update u = get(id);

        if (u.getCurrentState() != State.TESTING && u.getCurrentState() != State.REVIEW)
            throw new BadRequestException("Invalid transition");

        log(u, State.MODIFYING);
        u.setCurrentState(State.MODIFYING);
        u.setTestsPassed(false); // critical rule

        return updateRepo.save(u);
    }
// fun to merge Update  after pass Testing & Review
    public Update merge(Long id) {
        Update u = get(id);

        if (u.getCurrentState() != State.REVIEW)
            throw new BadRequestException("Only review can merge");

        log(u, State.MERGED);
        u.setCurrentState(State.MERGED);

        return updateRepo.save(u);
    }
// fun to reject Update  after fail Testing or fail Review
    public Update reject(Long id) {
        Update u = get(id);

        if (u.getCurrentState() != State.REVIEW && u.getCurrentState() != State.TESTING)
            throw new BadRequestException("Invalid transition");

        log(u, State.REJECTED);
        u.setCurrentState(State.REJECTED);

        return updateRepo.save(u);
    }

    public Update setTestResult(Long id, boolean passed) {
        Update u = get(id);
        u.setTestsPassed(passed);
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
    /*public UpdateDTO getUpdate(Integer id){
        //business ops
        Update update = piplineRepository.getUpdate(id);
        UpdateDTO updateDTO = new UpdateDTO();
        updateDTO.UpdateMapper(update);
        return update;
        //return updateDTO.UpdateMapper(update);
        //return updateMapper.toDTO(update);

    }*/
}