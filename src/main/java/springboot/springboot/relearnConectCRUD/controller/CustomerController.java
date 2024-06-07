package springboot.springboot.relearnConectCRUD.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.relearnConectCRUD.entity.Customer;
import springboot.springboot.relearnConectCRUD.entity.Entity;
import springboot.springboot.relearnConectCRUD.model.ModelBuid;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private ModelBuid model = new ModelBuid();

    @GetMapping
    public List<Customer> getAllCustomers() {
        // Lấy tất cả khách hàng từ cơ sở dữ liệu
        List<Customer> customers = new ArrayList<>();
        return customers;
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) throws SQLException {
        // Thêm khách hàng mới vào cơ sở dữ liệu


        return customer;
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) throws SQLException {
        // Cập nhật thông tin khách hàng có ID cụ thể

        return customer;
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) throws SQLException {
        // Xóa khách hàng có ID cụ thể

    }
}