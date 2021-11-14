package com.lshsh.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Tag {
    private Long id;

    private String avatar;

    @TableField("tag_name")
    private String tagName;
}
