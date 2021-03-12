package com.prim.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prim.common.pojo.Goods;
import com.prim.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: homework
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 14:16
 * @PackageName: com.prim.goods.controller
 * @ClassName: GoodsController.java
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //根据商品分类分页查询
    @GetMapping("/queryType/{page}/{pageSize}/{type}")
    public Map selectGoodsPageType(@PathVariable(name = "page") Integer page,
                                   @PathVariable(name = "pageSize") Integer pageSize,
                                   @PathVariable(name = "type") Integer type) {
        IPage<Goods> productsIPage = goodsService.selectGoodsPageType(new Page<>(page, pageSize), type);
        Map resultMap = new HashMap();
        resultMap.put("list", productsIPage.getRecords());
        resultMap.put("total", productsIPage.getTotal());
        return resultMap;
    }

    @GetMapping("/queryName/{page}/{pageSize}/{name}")
    public Map queryPageName(@PathVariable(name = "page") Integer page,
                             @PathVariable(name = "pageSize") Integer pageSize,
                             @PathVariable(name = "name") String name) {
        IPage<Goods> productsIPage = goodsService.selectGoodsPageName(new Page<>(page, pageSize), name);
        Map resultMap = new HashMap();
        resultMap.put("list", productsIPage.getRecords());
        resultMap.put("total", productsIPage.getTotal());
        return resultMap;
    }

    @GetMapping("/queryFlag/{page}/{pageSize}/{flag}")
    public Map queryPageFlag(@PathVariable(name = "page") Integer page,
                             @PathVariable(name = "pageSize") Integer pageSize,
                             @PathVariable(name = "flag") String flag) {
        IPage<Goods> productsIPage = goodsService.selectGoodsPageFlag(new Page<>(page, pageSize), flag);
        Map resultMap = new HashMap();
        resultMap.put("list", productsIPage.getRecords());
        resultMap.put("total", productsIPage.getTotal());
        return resultMap;
    }

    @GetMapping("/queryPrice/{page}/{pageSize}/{sp}/{ep}")
    public Map queryPagePrice(@PathVariable(name = "page") Integer page,
                              @PathVariable(name = "pageSize") Integer pageSize,
                              @PathVariable(name = "sp") String startPrice,
                              @PathVariable(name = "ep") String endPrice) {
        IPage<Goods> productsIPage = goodsService.selectGoodsPagePrice(new Page<>(page, pageSize), startPrice, endPrice);
        Map resultMap = new HashMap();
        resultMap.put("list", productsIPage.getRecords());
        resultMap.put("total", productsIPage.getTotal());
        return resultMap;
    }

    @GetMapping("/queryStock/{page}/{pageSize}/{ss}/{es}")
    public Map queryPageStock(@PathVariable(name = "page") Integer page,
                              @PathVariable(name = "pageSize") Integer pageSize,
                              @PathVariable(name = "ss") String startStock,
                              @PathVariable(name = "es") String endStock) {
        IPage<Goods> productsIPage = goodsService.selectGoodsPageStock(new Page<>(page, pageSize), startStock, endStock);
        Map resultMap = new HashMap();
        resultMap.put("list", productsIPage.getRecords());
        resultMap.put("total", productsIPage.getTotal());
        return resultMap;
    }

    @GetMapping("/queryById/{id}")
    public Goods queryById(@PathVariable Integer id) {
        Goods products = goodsService.findById(id);
        return products;
    }

    @GetMapping("/queryByOrderId/{id}")
    public List<Goods> queryByOrderId(@PathVariable Integer id) {
        List<Goods> goodsList = goodsService.findByOrderId(id);
        return goodsList;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGoods(@PathVariable Integer id) {
        int row = goodsService.deleteGoodsById(id);
        if (row > 0) {
            return "删除成功";
        }
        return "删除失败";
    }

    @PutMapping("/update")
    public String updateGoods(@RequestBody Goods goods) {
        int row = goodsService.updateGoods(goods);
        if (row > 0) {
            return "更新成功";
        }
        return "更新失败";
    }

    //模拟服务熔断
    //获取yml文件的配置
    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public String getPort() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return port;
    }
}
