package com.mmallnew.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmallnew.common.ServiceResponse;
import com.mmallnew.dao.CategoryMapper;
import com.mmallnew.pojo.Category;
import com.mmallnew.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 实现接口
 *
 * @author ：Y.
 * @version : V1.0
 * @date ：Created in 21:46 2019/2/8
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServiceResponse<String> addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServiceResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = new Category();

        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServiceResponse.createBySuccessMessage("添加品类成功");
        }
        return ServiceResponse.createByErrorMessage("添加品类失败");
    }

    @Override
    public ServiceResponse<String> updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServiceResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();

        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServiceResponse.createBySuccessMessage("更新品类名称成功");
        }
        return ServiceResponse.createByErrorMessage("更新品类名称失败");
    }

    @Override
    public ServiceResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categories = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categories)) {
            logger.info("未找到当分类的子分类");
        }
        return ServiceResponse.createBySuccess(categories);
    }

    @Override
    public ServiceResponse<ArrayList<Integer>> selectCategoryAndChildrenById(Integer categoryId) {

        Set<Category> categorySet = Sets.newHashSet();

        findChildCategory(categorySet, categoryId);

        ArrayList<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServiceResponse.createBySuccess(categoryIdList);
    }

    /**
     * 递归算法，算出子节点
     *
     * @param categoryId  当前节点的id
     * @param categorySet 集合
     * @author :Y.
     * @date :22:21 2019/2/8
     */
    private void findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);

        if (category != null) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
    }
}

