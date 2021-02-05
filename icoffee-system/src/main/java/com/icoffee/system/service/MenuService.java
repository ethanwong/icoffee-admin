package com.icoffee.system.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.dto.XTreeDto;

import java.util.List;


/**
 * @Name MenuService
 * @Description 菜单事务接口
 * @Author lincy
 * @Create 2020-02-21 15:50
 */
public interface MenuService extends MpBaseService<Menu> {

    /**
     * 获取页面菜单
     *
     * @return
     */
    ResultDto getMenu();

    /**
     * 获取列表
     * @return
     */
    List<Menu> getRootMenuList(QueryWrapper queryWrapper);

    /**
     * 获取菜单树
     *
     * @return
     */
    List<XTreeDto> getTree();


    /**
     * 判断uri唯一
     *
     * @param uri
     * @return
     */
    boolean existsUri(String uri);

    /**
     * 保存
     *
     * @param menu 实体
     * @return
     */
    ResultDto saveEntity(Menu menu);

    /**
     * 修改
     *
     * @param menu 实体
     * @return
     */
    ResultDto updateEntity(Menu menu);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    ResultDto delete(String id);

    /**
     * 获取名称层级
     *
     * @param uri
     * @return
     */
    ResultDto getNameHierarchy(String uri);

    /**
     * 查询分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageDto<Menu> findPage(int pageNo, int pageSize);
}
