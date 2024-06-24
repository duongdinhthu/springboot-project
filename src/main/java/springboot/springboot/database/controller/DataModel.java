package springboot.springboot.database.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import springboot.springboot.database.entity.Appointments;
import springboot.springboot.database.entity.Entity;
import springboot.springboot.database.entity.Staffs;
import springboot.springboot.database.model.ModelBuid;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataModel<T extends Entity<?>> {


    public String type;
    public Map<String, String> data;
    public void processData(List<DataModel> dataList) {
        for (DataModel dataModel : dataList) {
            try {
                Class<?> clazz = Class.forName("path.to.your.package." + dataModel.type);
                Object obj = clazz.newInstance();

                Map<String, String> data = dataModel.data;
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    try {
                        Field field = clazz.getDeclaredField(entry.getKey());
                        field.setAccessible(true);

                        if (field.getType() == Integer.class) {
                            field.set(obj, Integer.parseInt(entry.getValue()));
                        } else {
                            field.set(obj, entry.getValue());
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(obj.toString());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}