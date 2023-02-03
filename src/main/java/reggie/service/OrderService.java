package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import reggie.entity.Orders;

/**
 * @author hust_xy
 */


public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     *
     * @param orders
     */
    @Transactional(rollbackFor = Exception.class)
    void submit(Orders orders);
}
