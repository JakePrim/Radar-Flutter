package project.server.service;

import project.client.model.Grade;

import java.util.List;

public interface IGradeService {
    void add(Grade grade);

    List<Grade> query(Integer sId);

    void importGrade();
}
