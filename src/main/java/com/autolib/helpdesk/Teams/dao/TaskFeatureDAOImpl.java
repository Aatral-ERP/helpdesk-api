package com.autolib.helpdesk.Teams.dao;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.autolib.helpdesk.Config.aws.S3Directories;
import com.autolib.helpdesk.common.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.autolib.helpdesk.Teams.model.TaskFeatureRequest;
import com.autolib.helpdesk.Teams.model.Tasks.Task;
import com.autolib.helpdesk.Teams.model.Tasks.TaskFeature;
import com.autolib.helpdesk.Teams.repository.TaskFeatureRepository;
import com.autolib.helpdesk.Teams.repository.Tasks.TaskRepository;
import com.autolib.helpdesk.common.Util;

@Repository
public class TaskFeatureDAOImpl implements TaskFeatureDAO {

    @Autowired
    TaskFeatureRepository featureRepo;

    @Autowired
    TaskRepository taskRepo;

    @Autowired
    S3StorageService s3StorageService;

    @Override
    public Map<String, Object> createTaskFeature(TaskFeatureRequest featureReq) {
        Map<String, Object> resp = new HashMap<>();
        try {

            if (featureReq.getFeature().getFeatureId() > 0) {
                featureReq.setFeature(featureRepo.save(featureReq.getFeature()));
                resp.putAll(Util.SuccessResponse());
            } else {

                TaskFeature featureTemp = featureRepo.findByName(featureReq.getFeature().getName());

                if (featureTemp != null) {
                    resp.putAll(Util.invalidMessage("Feature Name Already Exist"));
                } else {
                    featureReq.setFeature(featureRepo.save(featureReq.getFeature()));

                    if (featureReq.getFeature().getFiles() != null && featureReq.getFeature().getFiles().length() > 0) {

                        List<String> filenames = Arrays.asList(featureReq.getFeature().getFiles().split(";"));

                        filenames.parallelStream()
                                .filter(filename -> filename.length() > 0)
                                .forEach(filename -> {
                                    s3StorageService.pushLocalFileToAWS(
                                            S3Directories.TaskFeatureFiles + featureReq.getFeature().getFeatureId(),
                                            S3Directories.TaskFeatureFiles + featureReq.getDirectoryName() + "/" + filename);
                                });
                    }

                    resp.putAll(Util.SuccessResponse());
                }
            }

        } catch (Exception e) {
            resp.putAll(Util.FailedResponse(e.getMessage()));
            e.printStackTrace();
        }
        resp.put("TaskFeature", featureReq.getFeature());

        return resp;
    }

    @Override
    public Map<String, Object> getTaskFeature(int taskFeatureId) {
        Map<String, Object> resp = new HashMap<>();
        try {

            TaskFeature feature = featureRepo.getByFeatureId(taskFeatureId);

            resp.put("TaskFeature", feature);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> deleteTaskFeature(TaskFeatureRequest featureReq) {
        Map<String, Object> resp = new HashMap<>();
        try {

            featureRepo.delete(featureReq.getFeature());

            List<Task> tasks = taskRepo.findByFeatureId(featureReq.getFeature().getFeatureId());
            tasks.forEach(_task -> _task.setFeatureId(0));

            taskRepo.saveAll(tasks);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

    @Override
    public Map<String, Object> getTeamTaskFeatures(int teamId) {
        Map<String, Object> resp = new HashMap<>();
        try {

            List<TaskFeature> features = featureRepo.getByTeamId(teamId);

            resp.put("TaskFeatures", features);

            resp.putAll(Util.SuccessResponse());
        } catch (Exception ex) {
            resp.putAll(Util.FailedResponse(ex.getMessage()));
            ex.printStackTrace();
        }
        return resp;
    }

}
