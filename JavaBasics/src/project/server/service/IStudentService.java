package project.server.service;

import project.client.model.Student;
import project.client.model.User;

public interface IStudentService {
    void add(Student student);
    void update(Student student);
    Student query(int id);
    boolean delete(int id);
    Student checkStudent(User user);
}
