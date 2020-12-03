package project.server.service;

import project.client.model.Exam;

public interface IExamService {
    void add(Exam exam);

    void update(Exam exam);

    Exam query(Integer id);

    boolean delete(Integer id);
}
