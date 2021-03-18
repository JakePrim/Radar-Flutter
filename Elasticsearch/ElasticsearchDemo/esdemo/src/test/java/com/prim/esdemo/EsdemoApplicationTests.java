package com.prim.esdemo;

import com.google.gson.Gson;
import com.prim.esdemo.pojo.Product;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsdemoApplicationTests {

    private RestHighLevelClient restHighLevelClient;

    private Gson gson = new Gson();

    /**
     * 初始化客户端
     */
    @Before
    public void init() {
        //传递集群的信息
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("127.0.0.1", 9201, "http"),
                new HttpHost("127.0.0.1", 9202, "http"),
                new HttpHost("127.0.0.1", 9203, "http"));
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    /**
     * 查看是否连接成功
     */
    @Test
    public void testConnection() {
        System.out.println(restHighLevelClient);
    }

    /**
     * 插入文档
     */
    @Test
    public void testInsert() throws IOException {
        //1. 文档数据
        Product product = new Product();
        product.setBrand("小米");
        product.setCategory("手机");
        product.setId(1L);
        product.setImages("http://image.huawei.com/1.jpg");
        product.setPrice(5999.99);
        product.setTitle("小米11 plus");

        //2. 文档数据转换为json格式
        String source = gson.toJson(product);

        //3. 创建索引请求对象 访问索引库 Type 指定文档id
        //String index, String type, String id
        IndexRequest request = new IndexRequest("test", "item", product.getId().toString());
        request.source(source, XContentType.JSON);//设置数据源
        //4. 发出请求
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        //5. 接收响应信息
        System.out.println(indexResponse);
    }

    /**
     * 释放资源
     */
    @After
    public void close() throws IOException {
        restHighLevelClient.close();
    }

}
