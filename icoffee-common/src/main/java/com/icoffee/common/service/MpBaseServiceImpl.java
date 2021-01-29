package com.icoffee.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icoffee.common.domain.BaseDomain;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.mybatis.MpBaseMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @Name MpBaseServiceImpl
 * @Description Service接口实现类基类
 * @Author chenly
 * @Create 2019-12-10 11:29
 */
@Log4j2
public class MpBaseServiceImpl<M extends MpBaseMapper<T>, T extends BaseDomain> extends ServiceImpl<M, T> implements MpBaseService<T> {

    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    /**
     * 默认页数
     */
    private final Integer PAGE_NO = 1;

    /**
     * 默认每页记录数
     */
    private final Integer PAGE_SIZE = 10;

    /**
     * 根据条件分页查询
     *
     * @param queryWrapper 查询条件
     * @param pageNo       当前页数
     * @param pageSize     每页记录数
     * @return
     */
    @Override
    public PageDto<T> selectPage(QueryWrapper<T> queryWrapper, Integer pageNo, Integer pageSize) {
        PageDto<T> pageDTO = new PageDto<>();
        if (pageNo == null) {
            pageNo = PAGE_NO;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        //当前页数
        pageDTO.setPageNo(pageNo);
        //每页记录数
        pageDTO.setPageSize(pageSize);
        IPage<T> page = getBaseMapper().selectPage(new Page<T>(pageNo, pageSize), queryWrapper);
        if (page.getRecords() == null || page.getRecords().isEmpty()) {
            return null;
        }
        //总记录数
        pageDTO.setTotalCount(page.getTotal());
        //总页数
        pageDTO.setTotalPage(page.getPages());
        //分页数据
        pageDTO.setData(page.getRecords());
        return pageDTO;
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public ResultDto selectAll() {
        try {
            List<T> dataList = getBaseMapper().selectAll();
            return new ResultDto(true, dataList);
        } catch (Exception exception) {
            log.error("删除所有数据出现异常,异常信息为:{}", exception.getMessage());
            throw exception;
        }
    }

    /**
     * 根据ID删除实体
     *
     * @param id 实体主键
     * @return
     */
    @Override
    public ResultDto deleteById(Serializable id) {
        try {
            if (StringUtils.isBlank(id.toString())) {
                return ResultDto.returnFail("ID不可为空");
            }
            removeById(id);
            return ResultDto.returnSuccess();
        } catch (Exception exception) {
            log.error("根据ID删除实体出现异常,异常信息为:{}", exception.getMessage());
            throw exception;
        }
    }

    /**
     * 根据条件删除
     *
     * @param queryWrapper 删除条件
     * @return
     */
    @Override
    public ResultDto deleteByWrapper(QueryWrapper<T> queryWrapper) {
        try {
            if (queryWrapper == null) {
                return ResultDto.returnFail("条件不可为空");
            }
            remove(queryWrapper);
            return ResultDto.returnSuccess();
        } catch (Exception exception) {
            log.error("根据条件删除实体出现异常,异常信息为:{}", exception.getMessage());
            throw exception;
        }
    }

    /**
     * 删除所有数据
     *
     * @return
     */
    @Override
    public ResultDto deleteAll() {
        try {
            getBaseMapper().deleteAll();
            return ResultDto.returnSuccess();
        } catch (Exception exception) {
            log.error("删除所有数据出现异常,异常信息为:{}", exception.getMessage());
            throw exception;
        }
    }
}