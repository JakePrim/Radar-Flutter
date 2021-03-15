package org.apache.ibatis.autoconstructor;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    void insertUser(@Param("name") String name);


}