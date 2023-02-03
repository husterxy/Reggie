package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.Employee;

/**
 * @author hust_xy
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
