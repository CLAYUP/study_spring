package com.mmallnew.service;

import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * 品类服务接口
 *
 * @author ：Y.
 * @version : $version$
 * @date ：Created in 21:45 2019/2/8
 */
public interface ICategoryService {

    /**
     * 添加品类名称
     *
     * @param categoryName 品类名称
     * @param parentId     品类ID
     * @return ServiceResponse<String>
     * @author :Y.
     * @date :22:36 2019/2/9
     */
    ServiceResponse<String> addCategory(String categoryName, Integer parentId);

    /**
     * 更新品类名称
     *
     * @param categoryId   品类id
     * @param categoryName 品类名称
     * @return ServiceResponse<String>
     * @author :Y.
     * @date :22:38 2019/2/9
     */
    ServiceResponse<String> updateCategoryName(Integer categoryId, String categoryName);

    /**
     * 获取当前一级的字节点
     *
     * @param categoryId id
     * @return ServiceResponse<List < Category>>
     * @author :Y.
     * @date :22:39 2019/2/9
     */
    ServiceResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 递归查询所有的子节点类别
     *
     * @param categoryId id
     * @return ServiceResponse<ArrayList < Integer>>
     * @author :Y.
     * @date :22:40 2019/2/9
     */
    ServiceResponse<ArrayList<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
