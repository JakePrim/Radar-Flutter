package project.server.service.impl;

import project.client.model.Grade;
import project.server.dao.ServerDao;
import project.server.service.IGradeService;

import java.util.List;

public class GradeService implements IGradeService {
    private ServerDao serverDao;

    public GradeService(ServerDao serverDao) {
        this.serverDao = serverDao;
    }

    @Override
    public void add(Grade grade) {
        serverDao.addGrade(grade);
    }

    @Override
    public List<Grade> query(Integer sId) {
        return serverDao.queryGrade(sId);
    }

    @Override
    public void importGrade() {
        serverDao.importGrade();
    }
}
