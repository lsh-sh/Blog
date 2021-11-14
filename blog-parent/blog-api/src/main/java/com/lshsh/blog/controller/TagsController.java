package com.lshsh.blog.controller;

import com.lshsh.blog.service.TagService;
import com.lshsh.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagsController {


    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public Result hotsTags() {
        int limit = 6;
        return tagService.findHotsTags(limit);
    }
}
