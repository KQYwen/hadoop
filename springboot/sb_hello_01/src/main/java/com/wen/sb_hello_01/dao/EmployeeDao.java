package com.wen.sb_hello_01.dao;

import com.wen.sb_hello_01.pojo.Department;
import com.wen.sb_hello_01.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDao {

    //模拟数据库中的数据
    private static Map<Integer, Employee> employeeMap=null;

    @Autowired
    private DepartmentDao departmentDao;

    static {
        employeeMap=new HashMap<Integer, Employee>();
        employeeMap.put(1001,new Employee(1001,"aa","a1248133194@qq.com",1, new Department(101,"教学部")));
        employeeMap.put(1002,new Employee(1002,"bb","b1248133194@qq.com",0, new Department(102,"市场部")));
        employeeMap.put(1003,new Employee(1003,"cc","c1248133194@qq.com",1, new Department(103,"教研部")));
        employeeMap.put(1004,new Employee(1004,"dd","d1248133194@qq.com",0, new Department(104,"运营部")));
        employeeMap.put(1005,new Employee(1005,"ee","e1248133194@qq.com",1, new Department(105,"后勤部")));
    }


    //主键自增
    private static Integer initId=1006;
    //增加一个员工
    public void save(Employee employee){
        if (employee.getId()==null){
            employee.setId(initId++);
        }
        employee.setDepartment(departmentDao.getDepartmentById(employee.getDepartment().getId()));

        employeeMap.put(employee.getId(),employee);
    }

    //查询全部员工信息
    public Collection<Employee> getAll(){
        return employeeMap.values();
    }


    //通过id查询员工
    public Employee getEmployeeById(Integer id){
        return employeeMap.get(id);
    }

    //删除员工
    public void delete(Integer id){
        employeeMap.remove(id);
    }
}
