package com.cqupt.mike.service.impl;

import com.cqupt.mike.common.ServiceResultEnum;
import com.cqupt.mike.controller.vo.MikeStudentVo;
import com.cqupt.mike.dao.StudentMapper;
import com.cqupt.mike.entity.Student;
import com.cqupt.mike.service.StudentService;
import com.cqupt.mike.util.BeanUtil;
import com.cqupt.mike.util.PageQueryUtil;
import com.cqupt.mike.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public  PageResult getstudentPage (PageQueryUtil pageUtil) {
        List<Student> mallUsers = studentMapper.findstudentList(pageUtil);
        int total = studentMapper.getTotalStudent(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    /**
     * 注册
     * @param stName 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String register(String stName, String password) {
        if (studentMapper.selectByLoginName(stName) != null) {  //查询用户是否已存在，若已存在则返回“用户已存在”
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        Student registerUser = new Student(); //若用户不存在，则new一个，传入注册信息
        registerUser.setStName(stName);

//        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPassword(password);
        if (studentMapper.insertStudent(registerUser) > 0) {  //向数据库插入注册用户信息，插入成功返回success，失败返回error
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     *
     * @param stName 用户名
     * @param password 密码
     * @param httpSession session
     * @return
     */
    @Override
    public String login(String stName, String password, HttpSession httpSession) {
        Student user = studentMapper.selectByLoginName(stName);
        if (user != null && httpSession != null) {
            if(!user.getPassword().equals(password)){ //判断密码是否正确
                return ServiceResultEnum.LOGIN_ERROR.getResult();
            }
            if (user.getStatus() == 0) {   //判断用户是否已经锁定
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            //用户名太长 影响页面展示
            if (user.getStName() != null && user.getStName().length() > 5) {//若用户名超过五位，则取前五位
                String tempstName = user.getStName().substring(0, 5) + "..";
                user.setStName(tempstName);
            }
            MikeStudentVo mikeStudentVo = new MikeStudentVo();
            BeanUtil.copyProperties(user, mikeStudentVo); //复制属性到vo
            httpSession.setAttribute("mikeStudent", mikeStudentVo); //将vo的值传入到session
            return ServiceResultEnum.SUCCESS.getResult();  //返回成功或失败
        }
        return ServiceResultEnum.LOGIN_NAME_ERROR.getResult();
    }

    /**
     * 忘记密码
     * @param stName 用户名
     * @param email 邮箱
     * @param httpSession
     * @param httpServletRequest
     * @return
     */
    public String forgetpassword(String stName, String email, HttpSession httpSession, HttpServletRequest httpServletRequest) {
        Student user = studentMapper.selectByLoginName(stName);
        if (user != null) {
            if (!user.getEmail().equals(email)) { //判断邮箱是否正确
                return ServiceResultEnum.EMAIL_ERROR.getResult();
            }
            // 学生id存入session
            httpServletRequest.getSession().setAttribute("stId",user.getStId());//setAttribute(string name,string value)
            return ServiceResultEnum.SUCCESS.getResult();  //返回成功或失败
        }
        return ServiceResultEnum.LOGIN_NAME_ERROR.getResult();
    }
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return studentMapper.lockUserBatch(ids, lockStatus) > 0;
    }

}
