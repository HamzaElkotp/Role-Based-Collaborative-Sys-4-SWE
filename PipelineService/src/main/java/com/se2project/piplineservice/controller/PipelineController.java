package com.se2project.piplineservice.controller;

import com.se2project.piplineservice.Piplinedto.UpdateDTO;
import com.se2project.piplineservice.Service.PipelineService;
import com.se2project.piplineservice.entity.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PipelineController {

    private final PipelineService service;

    public PipelineController(PipelineService service) {
        this.service = service;
    }
    //DI
    @Autowired
    PipelineService piplineService;

    //@GetMapping("/getUpdate/{id}")
    //public ResponseEntity<UpdateDTO> getUpdate(@PathVariable Integer id){
    //    final UpdateDTO update = piplineService.getUpdate(id);
    //    return ResponseEntity.ok(update);
    //}

    /////////   Update Management ///////////
    //Method:POST	Endpoint:/update => Create a new update
    @PostMapping("/update")
    public Update create(@RequestBody Update req) {
        return service.create(req.getTitle(), req.getCreatedBy());
    }
    // Method: GET  Endpoint: /update/{id} => Get update by ID
    @GetMapping("/update/{id}")
    public Update get(@PathVariable Long id) {
        return service.get(id);
    }

    // Method: GET  Endpoint: /updates => Get all updates
    @GetMapping("/updates")
    public List<Update> getAll() {
        return service.getAll();
    }
    /////////   State Transitions  ///////////
    // Method: POST  Endpoint: /updates/{id}/move-to-testing  => Move to Testing
    @PostMapping("/{id}/testing")
    public Update moveToTesting(@PathVariable Long id) {
        return service.moveToTesting(id);
    }
    // Method: POST  Endpoint: /updates/{id}/move-to-review  => Move to Review
    @PostMapping("/{id}/review")
    public Update moveToReview(@PathVariable Long id) {
        return service.moveToReview(id);
    }
    // Method: POST  Endpoint: /updates/{id}/move-to-modifying  => Move back to Modifying
    @PostMapping("/{id}/modifying")
    public Update moveToModifying(@PathVariable Long id) {
        return service.moveToModifying(id);
    }
    /////////   Final Actions  ///////////
    // Method: POST  Endpoint: /updates/{id}/merge  => Merge the update
    @PostMapping("/{id}/merge")
    public Update merge(@PathVariable Long id) {
        return service.merge(id);
    }
    // Method: POST  Endpoint: /updates/{id}/reject  => Reject the update
    @PostMapping("/{id}/reject")
    public Update reject(@PathVariable Long id) {
        return service.reject(id);
    }
    // Method: POST  Endpoint: /updates/{id}/revert  => Revert the update
    @PostMapping("/{id}/test-result")
    public Update setTest(@PathVariable Long id, @RequestParam boolean passed) {
        return service.setTestResult(id, passed);
    }
}
