package com.prim;

/**
 * Joinpoint连接点：增删改查这些方法都可以增强就是连接点。
 * Pointcut切入点：真正想拦截到的点。只想增强save方法 save方法就是切入点。
 * Advice:拦截后要做的事情。拦截save做权限校验，这个权限校验的方法就是通知：比如在save方法之前进行校验就是：前置通知
 * 比如在save执行之后做校验就是后置通知。比如在执行save方法之前和之后都进行校验叫环绕通知。
 * Target目标：被增强的对象，UserDaoImpl。
 * Weaving织入：将Advice应用到target的过程。将权限校验应用到UserDaoImpl的save方法的这个过程就是织入。
 * Proxy代理：被应用了增强后，产生一个代理类 代理对象。
 * Aspect切面：就是切入点和通知的组合。
 * @author prim
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    @Override
    public void save() {
        //保存用户
        System.out.println("UserDaoImpl.save");
    }

    @Override
    public void update() {
        checkPrivilege();
        //更新信息
        System.out.println("UserDaoImpl.update");
    }

    @Override
    public void delete() {
        //删除信息
        //比如在删除后 要加一个日志的记录
        System.out.println("UserDaoImpl.delete");
    }

    @Override
    public void find() {
        //查找信息
        System.out.println("UserDaoImpl.find");
    }

    /**
     * 检查权限的方法
     * 比如：要对所有对DAO方法进行权限校验
     */
//    public void checkPrivilege(){
//
//    }
}
