package springboot.springboot.database.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.database.entity.Departments;
import springboot.springboot.database.entity.Doctors;
import springboot.springboot.database.entity.Entity;
import springboot.springboot.database.entity.Patients;
import springboot.springboot.database.model.ModelBuid;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController<T extends Entity<?>> {
    @Autowired
    private ModelBuid model = new ModelBuid();

    @RequestMapping("/insertAll")
    public String insertAllObject(@RequestBody List<Map<String, Object>> dataList) throws SQLException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        List<DataModel> dataModelList = new ArrayList<>();
        for (Map<String, Object> data : dataList) {
            DataModel dataModel = new DataModel();
            dataModel.type = (String) data.get("type");
            dataModel.data = new HashMap<>();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (!entry.getKey().equals("type")) {
                    dataModel.data.put(entry.getKey(), entry.getValue().toString());
                }
            }
            dataModelList.add(dataModel);
        }

        // In ra tất cả thông tin các đối tượng DataModel

        for (DataModel dm : dataModelList) {
            System.out.println(dm.type + ": " + dm.data);
        }

        // Gọi hàm insertAll1 với tham số là dataModelList
        insertAll1(dataModelList);

        return "Data extraction completed.";
    }

    public void insertAll1(List<DataModel> dataModelList) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        List<Entity> objectList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());


        for (DataModel data : dataModelList) {
            // Lấy tên lớp từ data.type
            String className = "springboot.springboot.database.entity." + data.type;
            Class<?> entityClass = Class.forName(className);

            // Tạo đối tượng từ tên lớp và ánh xạ dữ liệu
            Object entity = entityClass.getDeclaredConstructor().newInstance();
            modelMapper.map(data.data, entity);
            ;
            if (entity instanceof Entity) {
                objectList.add((Entity) entity);
            }

        }
        model.insertAll1(objectList);
        // Tiếp tục xử lý với danh sách objectList
    }

    @RequestMapping("/getAll")
    public List<Entity> getAllObject(@RequestParam Map<String, Object> params) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        dataList.add(params);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        List<DataModel> dataModelList = new ArrayList<>();

        for (Map<String, Object> data : dataList) {
            DataModel dataModel = new DataModel();
            dataModel.type = (String) data.get("type");
            dataModel.data = new HashMap<>();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (!entry.getKey().equals("type")) {
                    dataModel.data.put(entry.getKey(), entry.getValue().toString());
                }
            }

            dataModelList.add(dataModel);
        }

        // In ra tất cả thông tin các đối tượng DataModel
        for (DataModel dm : dataModelList) {
            System.out.println(dm.type + ": " + dm.data);
        }

        // Gọi hàm insertAll1 với tham số là dataModelList
        List<Entity> objectList = getAll1(dataModelList);

        return objectList;
    }

    public List<Entity> getAll1(List<DataModel> dataModelList) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        List<Entity> objectList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        for (DataModel data : dataModelList) {
            // Lấy tên lớp từ data.type
            String className = "springboot.springboot.database.entity." + data.type;
            Class<?> entityClass = Class.forName(className);

            // Tạo đối tượng từ tên lớp và ánh xạ dữ liệu
            Object entity = entityClass.getDeclaredConstructor().newInstance();
            modelMapper.map(data.data, entity);
            ;
            if (entity instanceof Entity) {
                objectList.add((Entity) entity);
            }

        }
        return model.getEntityListById(objectList);
    }

}
