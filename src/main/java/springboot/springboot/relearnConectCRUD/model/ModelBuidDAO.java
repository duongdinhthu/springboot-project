package springboot.springboot.relearnConectCRUD.model;




import org.springframework.stereotype.Repository;
import springboot.springboot.relearnConectCRUD.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
@Repository
public interface ModelBuidDAO<T extends Entity<?>> {
    public int insert(Entity entity) throws SQLException, IllegalAccessException;
    public void insertAll(List entity) throws SQLException, IllegalAccessException;

    public boolean update(Entity entity) throws SQLException, IllegalAccessException;

    public boolean delete(Entity entity) throws IllegalAccessException, SQLException;

    public List getEntityById(Entity entity) throws SQLException, IllegalAccessException, InstantiationException;

    public List getAll(Class entityClass) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

}
