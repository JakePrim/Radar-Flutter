package com.prim.esdemo;

import com.google.gson.Gson;
import com.prim.esdemo.pojo.Product;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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
        product.setBrand("坚果");
        product.setCategory("手机");
        product.setId(3L);
        product.setImages("http://image.huawei.com/1.jpg");
        product.setPrice(5991.99);
        product.setTitle("坚果手机");

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

    @Test
    public void testFindIndex() throws IOException {
        //创建get请求
        GetRequest request = new GetRequest("test", "item", "1");
        //执行查询
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        //获取数据
        String source = getResponse.getSourceAsString();
        Product product = gson.fromJson(source, Product.class);
        System.out.println(product);
    }

    @Test
    public void testDeleteIndex() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("test", "item", "1");
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    @Test
    public void matchAll() throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //查询构建工具
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //添加查询条件 指定查询类型
        sourceBuilder.query(QueryBuilders.matchAllQuery());//借助QueryBuilders 构建查询条件
        searchRequest.source(sourceBuilder);
        //执行查询
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //获取查询结果
        SearchHits hits = response.getHits();
        //获得文档数据
        SearchHit[] hitsHits = hits.getHits();
        for (SearchHit hit : hitsHits) {
            //获得json串
            String json = hit.getSourceAsString();
            Product product = gson.fromJson(json, Product.class);
            System.out.println(product);
        }
    }

    public void basicQuery(SearchSourceBuilder sourceBuilder) throws IOException {
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest();
        //查询构建工具
        searchRequest.source(sourceBuilder);
        //执行查询
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //获取查询结果
        SearchHits hits = response.getHits();
        //获得文档数据
        SearchHit[] hitsHits = hits.getHits();
        for (SearchHit hit : hitsHits) {
            //获得json串
            String json = hit.getSourceAsString();
            Product product = gson.fromJson(json, Product.class);
            System.out.println(product);
        }
    }

    @Test
    public void match() throws IOException {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("title", "手机"));
        basicQuery(sourceBuilder);
    }

    public void sort(){

    }

    /**
     * 释放资源
     */
    @After
    public void close() throws IOException {
        restHighLevelClient.close();
    }

}
