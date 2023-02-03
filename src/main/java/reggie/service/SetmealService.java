package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import reggie.dto.SetmealDto;
import reggie.entity.Setmeal;

import java.util.List;

/**
 * @author hust_xy
 */

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时保存套餐与菜品的关系
     * @param setmealDto
     */
    @Transactional(rollbackFor = Exception.class)
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐与菜品的关系
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    void removeWithDish(List<Long> ids);
}
