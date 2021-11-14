package com.lshsh.blog.service;

import com.lshsh.blog.vo.Result;
import com.lshsh.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    /**
     * 通过作者ID查找标签
     *
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    /**
     * 查找最热标签
     *
     * @param limit
     * @return
     */
    Result findHotsTags(int limit);
}
