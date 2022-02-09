package com.xy.sweep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.sweep.common.utils.Constant;
import com.xy.sweep.common.utils.PageUtils;
import com.xy.sweep.common.utils.Query;
import com.xy.sweep.common.utils.TokenUtil;
import com.xy.sweep.dao.HallDao;
import com.xy.sweep.entity.DeceasedEntity;
import com.xy.sweep.entity.HallEntity;
import com.xy.sweep.entity.UserHallEntity;
import com.xy.sweep.entity.dto.HallAudit;
import com.xy.sweep.entity.dto.HallSearchDTO;
import com.xy.sweep.entity.dto.HallUpdate;
import com.xy.sweep.entity.dto.UserDTO;
import com.xy.sweep.service.DeceasedService;
import com.xy.sweep.service.HallService;
import com.xy.sweep.service.UserHallService;
import com.xy.sweep.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("hallService")
public class HallServiceImpl extends ServiceImpl<HallDao, HallEntity> implements HallService {
    @Resource
    private UserHallService userHallService;
    @Resource
    private DeceasedService deceasedService;
    @Resource
    private UserService userService;
    @Resource
    private TokenUtil tokenUtil;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HallEntity> page = this.page(new Query<HallEntity>().getPage(params), new QueryWrapper<HallEntity>());

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
        DeceasedEntity deceasedEntity = new DeceasedEntity();
        deceasedEntity.setHallId(hall.getId());
        deceasedService.save(deceasedEntity);
        hall.setDeceasedId(deceasedEntity.getId());
        this.updateById(hall);
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

    @Override
    public PageUtils search(Map<String, Object> params) {
        String key = (String) params.get("key");
        if (key == null || key.trim().equals("")) {
            return queryPage(params);
        }
        //纪念馆模糊查询
        List<HallEntity> hallList = list(new QueryWrapper<HallEntity>().like("title", key));
        List<HallEntity> list = new ArrayList<>(hallList);
        //逝者姓名模糊查询
        List<DeceasedEntity> ds = deceasedService.list(new QueryWrapper<DeceasedEntity>().like("name", key));
        if (ds != null) {
            List<Long> did = ds.stream().map(DeceasedEntity::getHallId).collect(Collectors.toList());
            if (did.size() > 0) {
                List<HallEntity> halls = list(new QueryWrapper<HallEntity>().in("id", did));
                if (halls != null && halls.size() > 0) {
                    list.addAll(halls);
                }
            }
        }
        //建馆人模糊查询
        list.addAll(this.baseMapper.selectByUserName(key));
        //去重
        ArrayList<Long> ids = new ArrayList<>();
        list = list.stream().filter(item -> {
            if (ids.contains(item.getId())) {
                return false;
            }
            ids.add(item.getId());
            return true;
        }).collect(Collectors.toList());

        List<HallSearchDTO> result = list.stream().map(item -> {
            HallSearchDTO hallSearchDTO = new HallSearchDTO();

            String title = item.getTitle();
            if (title != null) {
                item.setTitle(title.replace(key, "<span style='color:blue'>" + key + "</span>"));
            }
            hallSearchDTO.setHall(item);

            DeceasedEntity deceasedEntity = deceasedService.getById(item.getDeceasedId());
            String name = deceasedEntity.getName();
            if (name != null) {
                deceasedEntity.setName(name.replace(key, "<span style='color:blue'>" + key + "</span>"));
            }
            hallSearchDTO.setDeceased(deceasedEntity);

            UserHallEntity userHall = userHallService.getOne(new QueryWrapper<UserHallEntity>().eq("hall_id", item.getId()).eq("type", 0));
            hallSearchDTO.setCreatorId(userHall.getUserId());
            String nickname = userService.getById(userHall.getUserId()).getNickname();
            if (nickname != null) {
                hallSearchDTO.setCreator(nickname.replace(key, "<span style='color:blue'>" + key + "</span>"));
            }
            return hallSearchDTO;
        }).collect(Collectors.toList());


        if (list.size() == 0) {
            return null;
        }
        int curPage = 1;
        int limit = 10;
        if (params.get(Constant.PAGE) != null) {
            curPage = Integer.parseInt((String) params.get(Constant.PAGE));
        }
        if (params.get(Constant.LIMIT) != null) {
            limit = Integer.parseInt((String) params.get(Constant.LIMIT));
        }
        List<HallSearchDTO> page = result.subList(Integer.min(result.size(), (curPage - 1) * limit), Integer.min(curPage * limit, result.size()));
        return new PageUtils(page, result.size(), limit, curPage);
    }

}