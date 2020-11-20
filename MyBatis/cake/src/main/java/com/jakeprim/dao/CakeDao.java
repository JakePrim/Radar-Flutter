package com.jakeprim.dao;

import com.jakeprim.dto.CakeDTO;
import com.jakeprim.pojo.Cake;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author prim
 */
public interface CakeDao {
    /**
     * 插入商品数据
     *
     * @param cake
     * @return
     */
    @Insert("insert into cake(title,cid,image_path,price,taste,sweetness,weight,size,material,status) values(" +
            "#{title},#{cid},#{imagePath},#{price},#{taste},#{sweetness},#{weight},#{size},#{material},#{status})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(Cake cake);

    /**
     * 删除商品数据
     *
     * @param id
     * @return
     */
    @Delete("delete from cake where id = #{id}")
    int delete(@Param("id") int id);

    /**
     * 更新商品表
     *
     * @param cake
     * @return
     */
    @Update("update cake set title=#{title},cid=#{cid},image_path=#{imagePath},price=#{price},taste=#{taste},sweetness=#{sweetness},weight=#{weight},size=#{size},material=#{material},status=#{status} where id=#{id}")
    int update(Cake cake);

    /**
     * 查询某一个商品
     *
     * @param id
     * @return
     */
    @Select("select c.*,ca.title  c_title,ca.info from cake as c left join category as ca on c.cid=ca.id where c.id=#{id}")
    @Results(id = "resultList", value = {
            @Result(property = "cake.id", column = "id", id = true),
            @Result(property = "cake.cid", column = "cid"),
            @Result(property = "cake.title", column = "title"),
            @Result(property = "cake.imagePath", column = "image_path"),
            @Result(property = "cake.price", column = "price"),
            @Result(property = "cake.taste", column = "taste"),
            @Result(property = "cake.sweetness", column = "sweetness"),
            @Result(property = "cake.weight", column = "weight"),
            @Result(property = "cake.size", column = "size"),
            @Result(property = "cake.material", column = "material"),
            @Result(property = "cake.status", column = "status"),
            @Result(property = "category.title", column = "c_title"),
            @Result(property = "category.info", column = "info")
    })
    CakeDTO selectById(@Param("id") int id);

    /**
     * 查询所有商品
     *
     * @return
     */
    @Select("select c.*,ca.title  c_title,ca.info from cake as c left join category as ca on c.cid=ca.id")
    @ResultMap("resultList")
    List<CakeDTO> selectAll();

    /**
     * 根据分类查询商品
     *
     * @param cid
     * @return
     */
    @Select("select c.*,ca.title  c_title,ca.info from cake as c left join category as ca on c.cid=ca.id where c.cid=#{cid}")
    @ResultMap("resultList")
    List<CakeDTO> selectByCategory(@Param("cid") int cid);

    /**
     * 根据状态查询商品
     *
     * @param status
     * @return
     */
    @Select("select c.*,ca.title  c_title,ca.info from cake as c left join category as ca on c.cid=ca.id where c.status=#{status}")
    @ResultMap("resultList")
    List<CakeDTO> selectByStatus(@Param("status") String status);
}
