package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.OrderDetail;

/**
 * @author hust_xy
 */

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
