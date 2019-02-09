package com.mmallnew.service;

import com.mmallnew.common.ServiceResponse;
import com.mmallnew.pojo.Category;

import java.util.List;

/**
 * @author ：Y.
 * @date ：Created in 21:45 2019/2/8
 * @version : $version$
 */
public interface ICategoryService {

    ServiceResponse addCategory(String categoryName, Integer parentId);

    ServiceResponse updateCategoryName(Integer categoryId, String categoryName);

    ServiceResponse<List<Category>> getChildrentParallelCategory(Integer categoryId);

    ServiceResponse selectCategoryAndChildrenById(Integer categoryId);
}
