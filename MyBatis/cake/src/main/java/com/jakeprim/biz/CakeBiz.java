package com.jakeprim.biz;

import com.jakeprim.dto.CakeDTO;
import com.jakeprim.pojo.Cake;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author prim
 */
public interface CakeBiz {
    int insert(Cake cake);

    int delete(int id);

    int update(Cake cake);

    CakeDTO selectById(int id);

    List<CakeDTO> selectAll();

    List<CakeDTO> selectByCategory(int cid);

    List<CakeDTO> selectByStatus(String status);
}
