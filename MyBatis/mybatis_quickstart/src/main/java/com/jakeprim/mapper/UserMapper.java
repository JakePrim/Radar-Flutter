package com.jakeprim.mapper;

import com.jakeprim.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    User findUserById(int id);

    List<User> findAllResultMap();

    List<User> findUserByIdAndUserName(int id, String username);

    List<User> findUserByIdAndUserName2(@Param("id") int id, @Param("username") String username);

    List<User> findUserByIdAndUserName3(User user);

    /**
     * 模糊查询 方式1
     *
     * @param username
     * @return
     */
    List<User> findUserByUserName1(String username);


    List<User> findUserByUserName2(String username);

    /**
     * 添加用户 获取主键值
     */
    void saveUser(User user);

    void saveUser1(User user);

    /**
     * 动态SQL if标签实例
     */
    List<User> findUserByIdAndUserNameIf(User user);

    /**
     * 动态SQL set 动态更新
     *
     * @param user
     */
    void updateSet(User user);

    /**
     * 动态SQL foreach 多值查询
     *
     * @param ids
     * @return
     */
    List<User> findByList(List<Integer> ids);

    List<User> findByArray(Integer[] ids);

    /**
     * 查询所有的用户及订单信息
     */
    List<User> findAllWithOrders();

    List<User> findAllWithRoles();

    /**
     * 根据id查询用户
     */
    User findById(Integer id);

    /**
     * 一对多嵌套查询
     */
    List<User> findAllWithOrders2();

    /**
     * 多对多嵌套查询
     */
    List<User> findAllWithRoles2();
}
