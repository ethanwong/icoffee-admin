package com.icoffee.system.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.Menu;

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
    ResultDto getTree(String parentId);


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
     * 查询分页列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageDto<Menu> findPage(int pageNo, int pageSize);

    /**
     * 根据模块名称获取菜单信息
     * @param moduleName
     * @return
     */
    Menu getMenuByModuleName(String moduleName);

    /**
     * 获取菜单信息，包括子菜单和父级菜单
     * @param menuId
     * @return
     */
    Menu getAllMenuInfoById(String menuId);
}
