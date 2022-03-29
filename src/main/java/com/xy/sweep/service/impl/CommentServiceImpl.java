package com.xy.sweep.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.dao.CommentDao;
import com.xy.sweep.entity.CommentEntity;
import com.xy.sweep.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * @author climb.xu
 * @date 2022/2/22 19:21
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {
}
