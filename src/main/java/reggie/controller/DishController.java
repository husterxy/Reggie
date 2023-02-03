package reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reggie.common.R;
import reggie.dto.DishDto;
import reggie.entity.Category;
import reggie.entity.Dish;
import reggie.service.CategoryService;
import reggie.service.DishService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hust_xy
 */

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {

        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }

    /**
     * 菜品的分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        //分页展示数据
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasLength(name), Dish::getName, name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        Page<Dish> dishPage = dishService.page(pageInfo, lambdaQueryWrapper);

        //将菜品分类信息添加到集合中
        List<DishDto> list = new ArrayList<>();
        for (Dish record : dishPage.getRecords()) {

            DishDto dishDto = new DishDto();
            Category category = categoryService.getById(record.getCategoryId());
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            BeanUtils.copyProperties(record, dishDto);
            list.add(dishDto);
        }

        //在页面显示菜品分类信息
        Page<DishDto> dishDtoPage = new Page<>();
        BeanUtils.copyProperties(pageInfo, dishDtoPage);
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {

        dishService.updateWithFlavor(dishDto);
        return R.success("添加成功");
    }

    /**
     * 数据回显
     *
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {

        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 根据条件查询对应的菜品数据
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        lambdaQueryWrapper.eq(Dish::getStatus, 1);
        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(lambdaQueryWrapper);

        List<DishDto> dishDtoList = new ArrayList<>();
        for (Dish dish1 : list) {

            Long id = dish1.getId();
            DishDto dishDto = dishService.getByIdWithFlavor(id);
            dishDtoList.add(dishDto);
        }


        return R.success(dishDtoList);
    }
}
