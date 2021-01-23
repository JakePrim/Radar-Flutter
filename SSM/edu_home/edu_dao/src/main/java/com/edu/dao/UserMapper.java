package com.edu.dao;

import com.edu.pojo.*;
import com.edu.pojo.vo.UserVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> findAllUserByPage(UserVo userVo);

    /**
     * 更新用户的状态
     *
     * @param user
     */
    void updateUserStatus(User user);

    /**
     * 查询对应用户的密文密码
     *
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据用户id查询角色信息
     *
     * @param userId
     * @return
     */
    List<Role> findUserRoleById(Integer userId);

    /**
     * 根据角色id 查询顶级菜单
     */
    List<Menu> findParentMenuByRoleId(List<Integer> role_ids);

    /**
     * 对父菜单pid 查询关联的子菜单信息
     */
    List<Menu> findSubMenuByPid(@Param("pid") Integer pid, @Param("contextSubIds") List<Integer> contextSubIds);

    /**
     * 查询有权限的所有子菜单id
     */
    List<Integer> findContextSubByRoleId(@Param("pids") List<Integer> pids, @Param("role_ids") List<Integer> role_ids);

    /**
     * 获取用户关联的资源权限信息
     */
    List<Resource> findUserContextResourceByRoleId(List<Integer> role_ids);

    /**
     * 删除用户关联的角色数据
     */
    void deleteUserContextRole(Integer userId);

    /**
     * 用户关联角色
     *
     * @param
     */
    void insertUserContextRole(User_Role_relation user_role_relation);
}
