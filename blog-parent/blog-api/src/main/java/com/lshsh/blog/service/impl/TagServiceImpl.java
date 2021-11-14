package com.lshsh.blog.service.impl;

import com.lshsh.blog.dao.mapper.TagMapper;
import com.lshsh.blog.dao.pojo.Tag;
import com.lshsh.blog.service.TagService;
import com.lshsh.blog.vo.Result;
import com.lshsh.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {

        return copyList(tagMapper.findTagsByArticleId(articleId));
    }

    @Override
    public Result findHotsTags(int limit) {
        List<Tag> tagList = tagMapper.findHotsTags(limit);
        return Result.success(tagList);
    }

    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
}
