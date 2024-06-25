package springboot.springboot.database.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.database.entity.*;
import springboot.springboot.database.model.EntityToJSON;
import springboot.springboot.database.model.ModelBuid;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/appointments")

public class AppointmentsController<T extends Entity<?>> {
    private EntityToJSON json = new EntityToJSON();
    @Autowired
    private ModelBuid model = new ModelBuid();

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/insert")
    public void insert(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        ModelMapper modelMapper = new ModelMapper();//Tạo một đối tượng modelMapper từ lớp ModelMapper,
        // được sử dụng để thực hiện ánh xạ dữ liệu giữa các đối tượng.
        modelMapper.addConverter(new StringToDateConverter());// Thêm một Converter mới vào ModelMapper
        // để chỉ định cách chuyển đổi giữa các kiểu dữ liệu.
        // Trong trường hợp này, trình chuyển đổi StringToDateConverter
        // được thêm vào để giúp chuyển đổi dữ liệu từ kiểu String sang kiểu Date khi ánh xạ.
        Appointments appointments = modelMapper.map(requestData, Appointments.class);
        //Sử dụng ModelMapper để ánh xạ dữ liệu từ đối tượng requestData vào đối tượng Payments.
        // ModelMapper sẽ tự động ánh xạ các trường dữ liệu tương ứng giữa hai đối tượng dựa trên tên trường
        // và kiểu dữ liệu của chúng. Trong quá trình ánh xạ, Converter đã được thêm vào trước đó sẽ được sử dụng để
        // chuyển đổi dữ liệu từ String sang Date (hoặc bất kỳ loại dữ liệu nào mà Converter xử lý).
        model.insert(appointments);
    }

    @PutMapping("/update")
    public void update(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Appointments appointments = modelMapper.map(requestData, Appointments.class);
        model.update(appointments);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Appointments appointments = modelMapper.map(requestData, Appointments.class);
        model.delete(appointments);
        return "success";
    }

    @GetMapping("/list")
    public List<T> list() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = model.getAll(new Appointments().getClass());
        return list;
    }

    @GetMapping("/getById")
    public List<Appointments> getById(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        List<Appointments> appointmentsList = new ArrayList<>();
        // Truy vấn danh sách bệnh nhân từ cơ sở dữ liệu với id chỉ định
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Appointments appointments1 = modelMapper.map(requestData, Appointments.class);
        List<Appointments> appointments = model.getEntityById(appointments1);
        for (Appointments appointment : appointments) {
            Appointments newAppoinment = new Appointments();
            // Copy dữ liệu từ patient vào newPatient
            BeanUtils.copyProperties(appointment, newAppoinment);
            // Gán danh sách vào các trường list tương ứng với các class
            Staffs staffs = new Staffs();
            staffs.setStaff_id(appointment.getStaff_id());
            newAppoinment.setStaff(model.getEntityById(staffs));
            Doctors doctors = new Doctors();
            doctors.setDoctor_id(appointment.getDoctor_id());
            newAppoinment.setDoctor(model.getEntityById(doctors));
            Patients patients = new Patients();
            patients.setPatient_id(appointment.getPatient_id());
            newAppoinment.setPatient(model.getEntityById(patients));

            appointmentsList.add(newAppoinment);
        }
        return appointmentsList;
    }

    @PostMapping("/insertAll")
    public void insertAll(@RequestBody List<Map<String, Object>> dataList) throws SQLException, IllegalAccessException {
        List<Appointments> appointmentsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        for (Map<String, Object> data : dataList) {
            Appointments appointments = modelMapper.map(data, Appointments.class);
            appointmentsList.add(appointments);
        }
        model.insertAll(appointmentsList);
    }

    public static Object createElementInstance(Class<?> elementType) throws Exception {
        // Kiểm tra xem lớp cụ thể có constructor mặc định không
        Constructor<?> constructor = elementType.getConstructor();
        // Tạo một đối tượng mới thông qua constructor mặc định của lớp cụ thể
        return constructor.newInstance();
    }
}
