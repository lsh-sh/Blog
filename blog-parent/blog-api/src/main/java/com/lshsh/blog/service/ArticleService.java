package com.lshsh.blog.service;

import com.lshsh.blog.vo.Result;
import com.lshsh.blog.vo.params.PageParams;

public interface ArticleService {

    /**
     * 分页查询 文章列表
     *
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章查询
     *
     * @return
     */
    Result hotsArticle(int limit);

    /**
     * 最新文章查询
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档查询
     * @return
     */
    Result listArchives();
}
