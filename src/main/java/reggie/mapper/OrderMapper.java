package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.Orders;

/**
 * @author hust_xy
 */

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
