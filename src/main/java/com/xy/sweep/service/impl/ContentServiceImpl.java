package com.xy.sweep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.exception.RRException;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;
import com.xy.sweep.common.utils.TokenUtil;
import com.xy.sweep.dao.ContentDao;
import com.xy.sweep.entity.ContentEntity;
import com.xy.sweep.entity.dto.ContentPubDTO;
import com.xy.sweep.entity.dto.UserDTO;
import com.xy.sweep.service.ContentService;
import com.xy.sweep.service.HallService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("contentService")
public class ContentServiceImpl extends ServiceImpl<ContentDao, ContentEntity> implements ContentService {
    @Resource
    private HallService hallService;
    @Resource
    private TokenUtil tokenUtil;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ContentEntity> page = this.page(new Query<ContentEntity>().getPage(params), new QueryWrapper<ContentEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<ContentEntity> listByHallId(Long hallId) {
        return this.list(new QueryWrapper<ContentEntity>().eq("deceased_id", hallService.getDeceasedIdByHallId(hallId)));
    }

    @Override
    public List<ContentEntity> myContent() {
        UserDTO user = tokenUtil.getUserJson(UserDTO.class);
        return this.list(new QueryWrapper<ContentEntity>().eq("user_id", user.getUser().getId()));
    }

    @Override
    public ContentEntity publish(ContentPubDTO dto) {
        Long deceasedId = dto.getDeceasedId();
        if (deceasedId == null) {
            Long hallId = dto.getHallId();
            if (hallId == null) {
                throw new RRException("逝者id和纪念馆id不能都为空");
            }
            deceasedId = hallService.getDeceasedIdByHallId(hallId);
        }
        ContentEntity contentEntity = new ContentEntity();
        contentEntity.setDeceasedId(deceasedId);
        UserDTO user = tokenUtil.getUserJson(UserDTO.class);
        contentEntity.setUserId(user.getUser().getId());
        BeanUtils.copyProperties(dto, contentEntity);
        contentEntity.setStatus(0);
        this.save(contentEntity);
        return contentEntity;
    }
}