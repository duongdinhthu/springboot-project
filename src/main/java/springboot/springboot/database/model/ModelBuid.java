package springboot.springboot.database.model;


import org.springframework.stereotype.Component;
import springboot.springboot.database.connectDTB.MySqlConnect;
import springboot.springboot.database.entity.Entity;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

@Component
public class ModelBuid<T extends Entity<?>> implements ModelBuidDAO {
    private List<T> entities = new ArrayList<>();// T dai dien cho cac thuc the entity( Product, Customer....)
    public static Connection connection;

    public static Connection openConnection() throws SQLException {
        connection = MySqlConnect.getMySQLConnection();
        return connection;
    }

    public static PreparedStatement pstm;


    public static PreparedStatement openPstm(String query) throws SQLException {
        pstm = openConnection().prepareStatement(query);
        return pstm;
    }

    public static boolean exUpdate() throws SQLException {
        int check = pstm.executeUpdate();
        return check > 0;
    }

    public static ResultSet exQuery() throws SQLException {
        ResultSet rs = pstm.executeQuery();
        return rs;
    }

    private String getTableName(Class<T> entityClass) {
        String tableName = entityClass.getSimpleName();
        return tableName;
    }

    private StringBuilder queryInsert(Entity entity) {
        String tableName = getTableName((Class<T>) entity.getClass());
        StringBuilder query = new StringBuilder("insert into ");
        query.append(tableName).append(" (");
        Field[] fields = entity.getClass().getDeclaredFields();
        List<Field> includedFields = new ArrayList<>();
        List<Object> fieldValues = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value != null && !"0".equals(value.toString())) {
                    includedFields.add(field);
                    fieldValues.add(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < includedFields.size(); i++) {
            if (i > 0) {
                query.append(", ");
            }
            query.append(includedFields.get(i).getName());
        }
        query.append(") values (");

        for (int i = 0; i < includedFields.size(); i++) {
            if (i > 0) {
                query.append(", ");
            }
            query.append("?");
        }
        query.append(")");
        // In ra các trường và giá trị của chúng
        for (int i = 0; i < includedFields.size(); i++) {
            System.out.println(includedFields.get(i).getName());
            System.out.println(fieldValues.get(i));
        }

        return query;
    }


    private StringBuilder queryUpdate(Entity entity) {
        String tableName = getTableName((Class<T>) entity.getClass());
        StringBuilder query = new StringBuilder("update ");
        query.append(tableName).append(" set ");

        Field[] fields = entity.getClass().getDeclaredFields();
        Field idField = fields[0];

        List<Field> updatedFields = new ArrayList<>();
        List<Object> fieldValues = new ArrayList<>();

        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                Object value = fields[i].get(entity);
                if (value != null) {
                    updatedFields.add(fields[i]);
                    fieldValues.add(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < updatedFields.size(); i++) {
            if (i > 0) {
                query.append(", ");
            }
            query.append(updatedFields.get(i).getName()).append(" = ").append("?");

            // In ra tên trường và giá trị của trường
            System.out.println(updatedFields.get(i).getName());
            System.out.println(fieldValues.get(i));
        }

        query.append(" where ").append(idField.getName()).append(" = ?");

        // In ra tên trường id và giá trị của trường id
        idField.setAccessible(true);
        try {
            Object idValue = idField.get(entity);

            System.out.println(idField.getName());
            System.out.println(idValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return query;
    }

    private StringBuilder queryDelete(Entity entity) {
        String tableName = getTableName((Class<T>) entity.getClass());
        StringBuilder query = new StringBuilder("delete from ");
        query.append(tableName).append(" where ");
        Field[] fields = entity.getClass().getDeclaredFields();
        boolean foundField = false; // Biến để xác định xem trường nào có giá trị không

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value != null && !"0".equals(value.toString())) {
                    // Bỏ field có giá trị là null hoặc 0
                    System.out.println(value);
                    if (foundField) {
                        query.append(" and ");
                    }
                    query.append(field.getName()).append(" = ?");
                    foundField = true; // Đánh dấu rằng đã tìm thấy trường có giá trị
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Xử lý exception theo cách phù hợp
            }
        }

        if (!foundField) {
            // Xử lý trường hợp không tìm thấy trường có giá trị
            // Ví dụ: Ghi log, throw exception, hoặc thực hiện hành động khác
        }
        return query;
    }

    private StringBuilder queryGetAll(Class<T> entityClass) {
        String tableName = getTableName(entityClass);
        StringBuilder query = new StringBuilder("select * from ");
        query.append(tableName);
        return query;
    }

    private StringBuilder queryGetEntityById(Entity entity) {
        String tableName = getTableName((Class<T>) entity.getClass());
        StringBuilder query = new StringBuilder("select * from ");
        query.append(tableName).append(" where ");

        Field[] fields = entity.getClass().getDeclaredFields();
        boolean foundField = false; // Biến để xác định xem trường nào có giá trị không

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value != null && !"0".equals(value.toString())) {
                    // Bỏ field có giá trị là null hoặc 0
                    System.out.println(value);
                    if (foundField) {
                        query.append(" and ");
                    }
                    query.append(field.getName()).append(" = ?");
                    foundField = true; // Đánh dấu rằng đã tìm thấy trường có giá trị
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // Xử lý exception theo cách phù hợp
            }
        }

        if (!foundField) {
            // Xử lý trường hợp không tìm thấy trường có giá trị
            // Ví dụ: Ghi log, throw exception, hoặc thực hiện hành động khác
        }

        return query;
    }

    @Override
    public int insert(Entity entity) throws SQLException, IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        String query = queryInsert(entity).toString();
        PreparedStatement preparedStatement = openConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        System.out.println(query);
        int parameterIndex = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(entity);
            if (value != null && !"0".equals(value.toString())) {
                preparedStatement.setObject(parameterIndex++, value);
            }
        }
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedKey = generatedKeys.getInt(1);
                return generatedKey;
            } else {
                throw new SQLException("Creating record failed, no ID obtained.");
            }
        } else {
            throw new SQLException("Creating record failed, no rows affected.");
        }
    }

    @Override
    public void insertAll(List entity) throws SQLException, IllegalAccessException {
        List<Entity> entityList = entity;
        PreparedStatement pstm = null;

        try {
            for (Entity entity1 : entityList) {
                String query = queryInsert(entity1).toString(); // Tạo query insert riêng cho từng phần tử
                pstm = openPstm(query);
                System.out.println(query);
                Field[] fields = entity1.getClass().getDeclaredFields();
                int parameterIndex = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(entity1);
                    if (value != null && !"0".equals(value.toString())) {
                        pstm.setObject(parameterIndex++, value);
                    }
                }

                pstm.executeUpdate(); // Thực hiện insert cho từng phần tử
            }
        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
    }

    @Override
    public boolean update(Entity entity) throws SQLException, IllegalAccessException {
        System.out.println(entity);
        Field[] fields = entity.getClass().getDeclaredFields();
        String query = queryUpdate(entity).toString();
        System.out.println(query);
        pstm = openPstm(query);

        int parameterIndex = 1;
        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object value = fields[i].get(entity);
            if (value != null) {
                pstm.setObject(parameterIndex++, value);
            }
        }

        fields[0].setAccessible(true);
        Object value1 = fields[0].get(entity);
        pstm.setObject(parameterIndex, value1);

        boolean rowsUpdated = exUpdate();
        System.out.println(rowsUpdated);
        return rowsUpdated;
    }

    @Override
    public boolean delete(Entity entity) throws IllegalAccessException, SQLException {
        String query = queryDelete(entity).toString();
        System.out.println(query);
        pstm = openPstm(query);
        Field[] fields = entity.getClass().getDeclaredFields();
        List<Field> validFields = new ArrayList<>();
        for (Field f : fields) {
            f.setAccessible(true);
            Object val = f.get(entity);
            if (val != null && !"0".equals(val.toString())) {
                validFields.add(f);
            }
        }
        int index = 1;
        for (Field f : validFields) {
            Object val = f.get(entity);
            pstm.setObject(index, val);
            index++;
        }
        boolean rowsUpdated = exUpdate();
        System.out.println(rowsUpdated);
        return rowsUpdated;
    }

    @Override
    public List<T> getAll(Class entityClass) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> entities = new ArrayList<>();
        String query = queryGetAll(entityClass).toString();
        pstm = openPstm(query);
        System.out.println(query);
        ResultSet rs = exQuery();
        while (rs.next()) {
            T newEntity = (T) createEntityFromResultSet(rs, entityClass);
            entities.add(newEntity);
        }
        return entities;
    }


    private T createEntityFromResultSet(ResultSet rs, Class<T> entityClass) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T newEntity = entityClass.getDeclaredConstructor().newInstance();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Field field = null;
            try {
                field = entityClass.getDeclaredField(columnName);
            } catch (NoSuchFieldException e) {
                // Field không tồn tại trong lớp entityClass, bỏ qua
                continue;
            }

            field.setAccessible(true);
            Object value = rs.getObject(columnName);
            if (value != null) {
                field.set(newEntity, value);
            }
        }
        return newEntity;
    }

    @Override
    public List<T> getEntityById(Entity entity) throws SQLException, IllegalAccessException, InstantiationException {
        List<T> entityList = new ArrayList<>(); // Khai báo và khởi tạo entityList

        String query = queryGetEntityById(entity).toString();
        System.out.println(query);
        openPstm(query);

        Field[] fields = entity.getClass().getDeclaredFields();
        List<Field> validFields = new ArrayList<>();

        for (Field f : fields) {
            f.setAccessible(true);
            Object val = f.get(entity);
            if (val != null && !"0".equals(val.toString())) {
                validFields.add(f);
            }
        }
        int index = 1;
        for (Field f : validFields) {

            Object val = f.get(entity);
            pstm.setObject(index, val);
            index++;
        }
        ResultSet rs = exQuery();

        while (rs.next()) {
            T newEntity = (T) entity.getClass().newInstance();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                String columnName = rs.getMetaData().getColumnName(i);
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals(columnName)) {
                        Object fieldValue = rs.getObject(i);
                        field.set(newEntity, fieldValue);
                        System.out.println(field.getName() + ": " + fieldValue);
                        break;
                    }
                }
            }
            entityList.add(newEntity);
        }


        return entityList;
    }
    public List<Entity> getEntityListById(List<Entity> listObject) throws SQLException {
        List<Entity> entityList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = openConnection(); // Lấy kết nối tới cơ sở dữ liệu

            for (Entity entity : listObject) {
                String query = queryGetEntityById(entity).toString();
                System.out.println(query);
                pstm = conn.prepareStatement(query);

                Field[] fields = entity.getClass().getDeclaredFields();
                List<Field> validFields = new ArrayList<>();

                for (Field f : fields) {
                    f.setAccessible(true);
                    Object val = f.get(entity);
                    if (val != null && !"0".equals(val.toString())) {
                        validFields.add(f);
                    }
                }

                int index = 1;
                for (Field f : validFields) {
                    Object val = f.get(entity);
                    pstm.setObject(index, val);
                    index++;
                }

                rs = pstm.executeQuery();

                List<Entity> tempEntityList = new ArrayList<>(); // Tạo một list tạm để lưu trữ entity mới

                while (rs.next()) {
                    Entity newEntity = entity.getClass().getDeclaredConstructor().newInstance();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String columnName = field.getName();
                        Object fieldValue = rs.getObject(columnName);

                        if (fieldValue != null) {
                            field.set(newEntity, fieldValue);
                            System.out.println(columnName + ": " + fieldValue);
                        }
                    }
                    tempEntityList.add(newEntity);
                }

                entityList.addAll(tempEntityList); // Thêm tất cả entity mới vào entityList sau khi xử lý ResultSet
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // Xử lý ngoại lệ khi tạo mới Entity không thành công
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (conn != null) {
                conn.close(); // Đóng kết nối
            }
        }

        return entityList;
    }
    public void insertAll1(List<Entity> objectList) throws SQLException, IllegalAccessException {
        PreparedStatement pstm = null;

        try {
            for (Entity entity : objectList) {
                String query = queryInsert(entity).toString(); // Tạo query insert riêng cho từng phần tử
                pstm = openPstm(query);
                System.out.println(query);
                Field[] fields = entity.getClass().getDeclaredFields();
                int parameterIndex = 1;
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (value != null && !"0".equals(value.toString())) {
                        pstm.setObject(parameterIndex++, value);
                    }
                }


            }
            pstm.executeUpdate(); // Thực hiện insert cho từng phần tử
        } finally {
            if (pstm != null) {
                pstm.close();
            }
        }
    }
}

