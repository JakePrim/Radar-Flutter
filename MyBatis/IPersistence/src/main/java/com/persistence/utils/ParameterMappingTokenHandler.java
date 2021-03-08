package com.persistence.utils;

import java.util.ArrayList;
import java.util.List;


public class ParameterMappingTokenHandler implements TokenHandler {
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    // context是参数名称 #{id} #{username}

    @Override
    public String handleToken(String content) {
        //存储#{}的参数名称
        parameterMappings.add(buildParameterMapping(content));
        //将#{}替换成?号
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }

}
