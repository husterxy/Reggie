package reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import reggie.entity.AddressBook;

/**
 * @author hust_xy
 */

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
