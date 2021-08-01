package com.sacc.assessment.repository;

import com.sacc.assessment.entity.TextContent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/7/20
 **/
public interface TextContentRepository extends JpaRepository<TextContent, Integer> {
    @Override
    <S extends TextContent> S saveAndFlush(S s);
}
