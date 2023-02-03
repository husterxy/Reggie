package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.entity.DishFlavor;
import reggie.mapper.DishFlavorMapper;
import reggie.service.DishFlavorService;

/**
 * @author hust_xy
 */

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
