/**
 * Copyright 2009-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.autoconstructor;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@CacheNamespace
public interface AutoConstructorMapper {

    // ========== PrimitiveSubject ==========

    @Select("SELECT * FROM subject WHERE id = #{id}")
    PrimitiveSubject getSubject(final int id);
    @Select("SELECT * FROM subject")
    List<PrimitiveSubject> getSubjects();


    PrimitiveSubject testResultMap(Map<String, Object> map);

    PrimitiveSubject getSubject2(@Param("id") final int id); // add by 芋艿

    PrimitiveSubject getSubject3(@Param("id") final int id); // add by 芋艿

    PrimitiveSubject getSubject4(@Param("id") final Integer id); // add by 芋艿

    PrimitiveSubject getSubject5(String name, Integer age); // add by 芋艿

    List<PrimitiveSubject> getSubjectList(@Param("ids") List<Integer> ids); // add by 芋艿

    // ========== AnnotatedSubject ==========

//    @Select("SELECT * FROM subject")
    @Select("SELECT id AS ID, name, age, height, weight FROM subject")
    List<AnnotatedSubject> getAnnotatedSubjects();

    // ========== BadSubject ==========

    @Select("SELECT * FROM subject")
    List<BadSubject> getBadSubjects();

    // ========== ExtensiveSubject ==========

    @Select("SELECT * FROM extensive_subject")
    List<ExtensiveSubject> getExtensiveSubject();

}