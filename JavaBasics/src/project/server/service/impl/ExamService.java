package project.server.service.impl;

import project.client.model.Exam;
import project.server.dao.ServerDao;
import project.server.service.IExamService;

public class ExamService implements IExamService {
    private ServerDao serverDao;

    public ExamService(ServerDao serverDao) {
        this.serverDao = serverDao;
    }

    @Override
    public void add(Exam exam) {
        serverDao.addExam(exam);
    }

    @Override
    public void update(Exam exam) {
        serverDao.updateExam(exam);
    }

    @Override
    public Exam query(Integer id) {
        return serverDao.queryExam(id);
    }

    @Override
    public boolean delete(Integer id) {
        return serverDao.deleteExam(id);
    }
}
