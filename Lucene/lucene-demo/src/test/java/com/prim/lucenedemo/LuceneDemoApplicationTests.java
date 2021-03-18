package com.prim.lucenedemo;

import com.prim.lucenedemo.pojo.JobInfo;
import com.prim.lucenedemo.service.JobInfoService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneDemoApplicationTests {

    @Autowired
    private JobInfoService jobInfoService;

    /**
     * 创建索引
     */
    @Test
    public void create() throws IOException {
        //1. 指定索引文件的存储位置，索引具体的表现形式就是一组有规则的文件
        Directory directory = FSDirectory.open(new File("/Users/prim/java/class/index"));
        //2. 配置版本及其分词器
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
        //3. 创建indexWriter对象，创建索引
        IndexWriter indexWriter = new IndexWriter(directory, config);
        //删除已经存在的索引库
        indexWriter.deleteAll();
        //4. 获得索引源 原始数据
        List<JobInfo> jobInfos = jobInfoService.selectAll();
        //5. 遍历JobInfoList 每次遍历创建一个Document对象
        for (JobInfo jobInfo : jobInfos) {
            //创建Document对象
            Document document = new Document();
            //创建Field对象，添加到Document中
            //职位的id
            document.add(new LongField("id", jobInfo.getId(), Field.Store.YES));
            //切分词、索引、存储
            //名称
            document.add(new TextField("companyName", jobInfo.getCompanyName(), Field.Store.YES));
            //地址
            document.add(new TextField("companyAddr", jobInfo.getCompanyAddr(), Field.Store.YES));
            //公司的信息
            document.add(new TextField("companyInfo", jobInfo.getCompanyInfo(), Field.Store.YES));
            //职位名称
            document.add(new TextField("jobName", jobInfo.getJobName(), Field.Store.YES));
            //职位的信息
            document.add(new TextField("jobInfo", jobInfo.getJobInfo(), Field.Store.YES));
            //最小工资
            document.add(new IntField("salaryMin", jobInfo.getSalaryMin(), Field.Store.YES));
            //最大工资
            document.add(new IntField("salaryMax", jobInfo.getSalaryMax(), Field.Store.YES));
            document.add(new StringField("url", jobInfo.getUrl(), Field.Store.YES));
            //将文档追加到索引库
            indexWriter.addDocument(document);
        }
        //关闭资源
        indexWriter.close();
        System.out.println("create index success");
    }

    @Test
    public void query() throws IOException {
        //1. 指定索引文件的存储位置，索引具体的表现形式就是一组有规则的文件
        Directory directory = FSDirectory.open(new File("/Users/prim/java/class/index"));
        //2. IndexReader对象 读取索引
        IndexReader indexReader = DirectoryReader.open(directory);
        //3. 创建查询对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4. 使用term查询 查询公司名称中包含"北京"的所有的文档对象 默认的分词器对中文不太友好
        Query query = new TermQuery(new Term("companyName", "北京"));
        TopDocs search = indexSearcher.search(query, 100);
        //获得符合查询条件的文档数
        int totalHits = search.totalHits;
        System.out.println("totalHits:" + totalHits);
        //获得命中的文档 封装了文档的id信息
        ScoreDoc[] scoreDocs = search.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int id = scoreDoc.doc;//文档id
            //通过文档id获得文档对象
            Document document = indexSearcher.doc(id);
            System.out.println("id:" + document.get("id"));
            System.out.println("companyName:" + document.get("companyName"));
            System.out.println("companyAddr:" + document.get("companyAddr"));
            System.out.println("companyInfo:" + document.get("companyInfo"));
            System.out.println("jobName:" + document.get("jobName"));
            System.out.println("jobInfo:" + document.get("jobInfo"));
            System.out.println("url:" + document.get("url"));
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        }
        //释放资源
        indexReader.close();
    }

}
