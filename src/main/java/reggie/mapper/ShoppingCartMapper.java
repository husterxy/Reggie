package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.ShoppingCart;

/**
 * @author hust_xy
 */

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
