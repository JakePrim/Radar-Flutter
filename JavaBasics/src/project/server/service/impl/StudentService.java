package project.server.service.impl;

import project.client.model.Student;
import project.client.model.User;
import project.server.dao.ServerDao;
import project.server.service.IStudentService;

import java.util.List;

/**
 * 服务端学员的业务处理
 */
public class StudentService implements IStudentService {
    private ServerDao serverDao;

    public StudentService(ServerDao serverDao) {
        this.serverDao = serverDao;
    }

    @Override
    public void add(Student student) {
        serverDao.addStudent(student);
    }

    @Override
    public void update(Student student) {
        serverDao.updateStudent(student);
    }

    @Override
    public Student query(int id) {
        return serverDao.queryStudent(id);
    }

    @Override
    public boolean delete(int id) {
        return serverDao.deleteStudent(id);
    }

    @Override
    public Student checkStudent(User user) {
        return serverDao.checkStudent(user);
    }
}
