package com.prim.service.impl;

import com.prim.dao.LogDao;
import com.prim.pojo.Log;
import com.prim.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Qualifier("logDao")
    @Autowired
    private LogDao logDao;

    @Override
    public void addSystemLog(Log log) {
        log.setOprTime(new Date());
        log.setType("system");
        logDao.insert(log);
    }

    @Override
    public void addLoginLog(Log log) {
        log.setOprTime(new Date());
        log.setType("login");
        logDao.insert(log);
    }

    @Override
    public void addOperationLog(Log log) {
        log.setOprTime(new Date());
        log.setType("operation");
        logDao.insert(log);
    }

    @Override
    public List<Log> getSystemLog() {
        return logDao.selectByType("system");
    }

    @Override
    public List<Log> getLoginLog() {
        return logDao.selectByType("login");
    }

    @Override
    public List<Log> getOperationLog() {
        return logDao.selectByType("operation");
    }
}
