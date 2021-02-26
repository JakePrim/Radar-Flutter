package com.persistence.io;

import java.io.InputStream;

/**
 * @program: PBatisTest
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-25 18:59
 * @PackageName: com.persistence.io
 * @ClassName: Resources.java
 **/
public class Resources {
    /**
     * 根据配置文件的路径将配置文件加载为输入流，存储在内存中
     *
     * @param path 配置文件路径
     * @return
     */
    public static InputStream getResourceAsSteam(String path) {
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}
