package springboot.springboot.database.model;


import org.springframework.stereotype.Component;
import springboot.springboot.database.connectDTB.MySqlConnect;
import springboot.springboot.database.entity.Entity;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                query.append(", ");
            }
            query.append(fields[i].getName());
        }
        query.append(") values (");
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                query.append(", ");
            }
            query.append("?");
        }
        query.append(")");
        return query;
    }

    private StringBuilder queryInsertDemo(Entity entity) {
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
                if (value != null && !field.getName().equals("appointmentsList")) {
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
        for (int i = 1; i < fields.length; i++) {
            if (i > 1) {
                query.append(", ");
            }
            query.append(fields[i].getName()).append(" = ?");
        }
        query.append(" where ").append(fields[0].getName()).append(" = ?");
        return query;
    }

    private StringBuilder queryDelete(Entity entity) {
        String tableName = getTableName((Class<T>) entity.getClass());
        StringBuilder query = new StringBuilder("delete from ");
        query.append(tableName).append(" where ");
        Field[] fields = entity.getClass().getDeclaredFields();
        query.append(fields[0].getName()).append(" = ?");
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
        query.append(fields[0].getName()).append(" = ?");
        return query;
    }

    @Override
    public int insert(Entity entity) throws SQLException, IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        String query = queryInsertDemo(entity).toString();
        System.out.println(query);
        PreparedStatement preparedStatement = openConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        int parameterIndex = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(entity);
            System.out.println(field.getName());
            System.out.println(value);
            preparedStatement.setObject(parameterIndex++, value);
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
        for (Entity entity1 : entityList) {
            Field[] fields = entity1.getClass().getDeclaredFields();
            String query = queryInsert(entity1).toString();
            System.out.println(query);
            pstm = openPstm(query);
            int parameterIndex = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity1);
                pstm.setObject(parameterIndex++, value);
            }
            pstm.addBatch();
        }
        pstm.executeUpdate();
    }

    @Override
    public boolean update(Entity entity) throws SQLException, IllegalAccessException {
        System.out.println(entity);
        Field[] fields = entity.getClass().getDeclaredFields();
        String query = queryUpdate(entity).toString();
        System.out.println(query);
        pstm = openPstm(query);
        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object value = fields[i].get(entity);
            pstm.setObject(i, value);
        }
        fields[0].setAccessible(true);
        Object value1 = fields[0].get(entity);
        pstm.setObject(fields.length, value1);
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
        fields[0].setAccessible(true);
        Object value = fields[0].get(entity);
        System.out.println(value);
        pstm.setObject(1, value);
        boolean rowsUpdated = exUpdate();
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
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true);
            Object value = rs.getObject(field.getName());
            field.set(newEntity, value);

        }
        return newEntity;
    }

    @Override
    public List<T> getEntityById(Entity entity) throws SQLException, IllegalAccessException, InstantiationException {
        String query = queryGetEntityById(entity).toString();
        System.out.println(query);
        openPstm(query);
        Field[] fields = entity.getClass().getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        fields[0].setAccessible(true);
        Object value = fields[0].get(entity);
        pstm.setObject(1, value);
        ResultSet rs = exQuery();
        List<T> entityList = new ArrayList<>();
        while (rs.next()) {
            T newEntity = (T) entity.getClass().newInstance();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                String columnName = rs.getMetaData().getColumnName(i);
                Field field = fieldMap.get(columnName);
                if (field != null) {
                    field.setAccessible(true);
                    Object fieldValue = rs.getObject(i);
                    field.set(newEntity, fieldValue);
                }
            }
            entityList.add(newEntity);
        }
        return entityList;
    }
}

