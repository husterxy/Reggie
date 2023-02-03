package reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reggie.dto.DishDto;
import reggie.entity.Dish;
import reggie.entity.DishFlavor;
import reggie.mapper.DishMapper;
import reggie.service.DishFlavorService;
import reggie.service.DishService;

import java.util.List;

/**
 * @author hust_xy
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public void saveWithFlavor(DishDto dishDto) {

        //保存菜品的基本信息到 dish 表
        this.save(dishDto);

        //菜品口味
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        dishFlavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDto.getId()));

        //保存菜品口味的基本信息到 dish_flavor 表
        dishFlavorService.saveBatch(dishFlavors);
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {

        //更新 dish 表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据（ dish_flavor 的 delete 操作）
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(lambdaQueryWrapper);

        //添加当前提交的口味数据（ dish_flavor 的 insert 操作）
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        dishFlavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDto.getId()));
        dishFlavorService.saveBatch(dishFlavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {

        //从 dish 表中查询菜品基本信息
        Dish dish = this.getById(id);

        //从 dish_flavor 表中查询当前菜品对应的口味信息
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(lambdaQueryWrapper);

        //属性值拷贝
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(flavors);
        return dishDto;
    }
}
