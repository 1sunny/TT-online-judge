package one.sunny.ttoj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String authDescription;

    private String authKey;

    /**
     * 1->使用, 0->停用
     */
    private Boolean isUsing;

    private Date createTime;

    private Date updateTime;

    private String component;

    private String path;

    private Long parentId;
}
