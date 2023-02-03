package reggie.dto;


import lombok.Data;
import reggie.entity.Setmeal;
import reggie.entity.SetmealDish;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
