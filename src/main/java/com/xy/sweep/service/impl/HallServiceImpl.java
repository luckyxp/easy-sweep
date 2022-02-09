package com.xy.sweep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;
import com.xy.sweep.common.utils.TokenUtil;
import com.xy.sweep.dao.HallDao;
import com.xy.sweep.entity.DeceasedEntity;
import com.xy.sweep.entity.HallEntity;
import com.xy.sweep.entity.UserHallEntity;
import com.xy.sweep.entity.dto.HallAudit;
import com.xy.sweep.entity.dto.HallUpdate;
import com.xy.sweep.entity.dto.UserDTO;
import com.xy.sweep.service.DeceasedService;
import com.xy.sweep.service.HallService;
import com.xy.sweep.service.UserHallService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("hallService")
public class HallServiceImpl extends ServiceImpl<HallDao, HallEntity> implements HallService {
    @Resource
    private UserHallService userHallService;
    @Resource
    private DeceasedService deceasedService;
    @Resource
    private TokenUtil tokenUtil;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HallEntity> page = this.page(
                new Query<HallEntity>().getPage(params),
                new QueryWrapper<HallEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<HallEntity> newCreate() {
        return this.baseMapper.newCreate();
    }

    @Override
    public List<HallEntity> rank(String key) {
        return this.baseMapper.rank(StringUtils.camelToUnderline(key));
    }

    @Override
    public void createHall(HallEntity hall) {
        hall.setId(null);
        hall.setCreateTime(null);
        hall.setUpdateTime(null);
        hall.setDeepNum(null);
        hall.setKindNum(null);
        hall.setVisitorNum(null);
        hall.setStatus(0);
        hall.setDeleted(null);
        this.save(hall);
        String id = hall.getId() + "";
        hall.setIdentifier("#0000000".substring(0, 8 - id.length()) + id);
        this.updateById(hall);

        DeceasedEntity deceasedEntity = new DeceasedEntity();
        deceasedEntity.setHallId(hall.getId());
        deceasedService.save(deceasedEntity);


        UserHallEntity userHallEntity = new UserHallEntity();
        userHallEntity.setHallId(hall.getId());
        userHallEntity.setType(0);
        userHallEntity.setUserId(tokenUtil.getUserJson(UserDTO.class).getUser().getId());
        userHallService.save(userHallEntity);
    }

    @Override
    public void audit(HallAudit hallAudit) {
        HallEntity hallEntity = new HallEntity();
        hallEntity.setId(hallAudit.getId());
        hallEntity.setStatus(hallAudit.getStatus());
        this.updateById(hallEntity);
    }

    @Override
    public void updateInfoById(HallUpdate hallUpdate) {
        HallEntity hallEntity = new HallEntity();
        hallEntity.setId(hallUpdate.getId());
        hallEntity.setTitle(hallUpdate.getTitle());
        hallEntity.setBackground(hallUpdate.getBackground());
        hallEntity.setType(hallUpdate.getType());
        this.updateById(hallEntity);
    }

    @Override
    public void deleteById(Long id) {
        this.removeById(id);
        userHallService.remove(new QueryWrapper<UserHallEntity>().eq("hall_id", id));
        deceasedService.remove(new QueryWrapper<DeceasedEntity>().eq("hall_id", id));
    }

}