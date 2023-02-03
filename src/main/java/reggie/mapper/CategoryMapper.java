package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.Category;

/**
 * @author hust_xy
 */

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
