package reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reggie.common.CustomException;
import reggie.dto.SetmealDto;
import reggie.entity.Setmeal;
import reggie.entity.SetmealDish;
import reggie.mapper.SetmealMapper;
import reggie.service.SetmealDishService;
import reggie.service.SetmealService;

import java.util.List;

/**
 * @author hust_xy
 */

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {

        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealDto.getId()));
        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    public void removeWithDish(List<Long> ids) {

        //查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId,ids);
        lambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = (int) this.count(lambdaQueryWrapper);
        if (count>0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //若可以删除，先删除套餐表中的数据
        this.removeByIds(ids);

        //再删除关系表的数据
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lambdaQueryWrapper1);
    }
}
