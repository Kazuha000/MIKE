package com.cqupt.mike.service.impl;

import com.cqupt.mike.dao.AdminUserMapper;
import com.cqupt.mike.dao.TeacherMapper;
import com.cqupt.mike.entity.AdminUser;
import com.cqupt.mike.entity.Teacher;
import com.cqupt.mike.service.AdminUserService;
import com.cqupt.mike.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public int login(String name,String password){
        List<Teacher> userList=teacherMapper.login(name);//根据管理员用户名查询所有符合条件的管理员
        if(userList.size()!=0){//根据返回表的长度，判断此用户名是否存在
            for(int i=0;i<userList.size();i++){ //循环遍历符合条件的用户
                if (userList.get(i).getPassword().equals(password)){  //判断密码是否匹配
                    return userList.get(i).getId();//登陆成功,返回登陆用户的id
                }
            }
            return 0;//密码错误
        }
        return  -1;//用户不存在
    }

    @Override
    public Teacher getUserDetailById(Integer loginUserId){
        return teacherMapper.selectByPrimaryKey(loginUserId);//调用数据库，通过id查询管理员信息
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        //根据id查询管理员用户
        Teacher teacher = teacherMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (teacher != null) {
//            String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");  //密码加密
//            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            //比较原密码是否正确
            if (originalPassword.equals(teacher.getPassword())) {
                //设置新密码并修改
                teacher.setPassword(newPassword);
                if (teacherMapper.updateByPrimaryKey(teacher) > 0) {
                    //修改,成功则返回true
                    return true;
                }
            }
        }
        return false;
    }

    //修改用户名与账号
    @Override
    public Boolean updateName(Integer id, String name, Integer accountNo) {
        //根据id查询管理员用户
        Teacher  teacher = teacherMapper.selectByPrimaryKey(id);
        //当前用户非空才可以进行更改
        if (teacher != null) {
            //设置新名称并修改
            teacher.setName(name);
            teacher.setAccountNo(accountNo);
            if (teacherMapper.updateByPrimaryKey(teacher) > 0) {
                //修改,成功则返回true
                return true;
            }
        }
        return false;
    }

}
