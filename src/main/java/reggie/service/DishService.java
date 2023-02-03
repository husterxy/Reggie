package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import reggie.dto.DishDto;
import reggie.entity.Dish;

/**
 * @author hust_xy
 */


public interface DishService extends IService<Dish> {

    /**
     * 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
     * @param dishDto
     */
    @Transactional(rollbackFor = Exception.class)
    void saveWithFlavor(DishDto dishDto);

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    DishDto getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息及相应的口味信息
     * @param dishDto
     */
    @Transactional(rollbackFor = Exception.class)
    void updateWithFlavor(DishDto dishDto);
}
