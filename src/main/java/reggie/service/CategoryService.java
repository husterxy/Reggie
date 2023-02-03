package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import reggie.entity.Category;

/**
 * @author hust_xy
 */


public interface CategoryService extends IService<Category> {

    /**
     * 根据id删除分类
     * @param id
     */
    void remove(Long id);
}
