package com.cqupt.mike.service.impl;

import com.cqupt.mike.dao.AdminUserMapper;
import com.cqupt.mike.entity.AdminUser;
import com.cqupt.mike.service.AdminUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public int login(String adName,String password){
        List<AdminUser> userList=adminUserMapper.login(adName);//根据管理员用户名查询所有符合条件的管理员
        if(userList.size()!=0){//根据返回表的长度，判断此用户名是否存在
            for(int i=0;i<userList.size();i++){ //循环遍历符合条件的用户
                if (userList.get(i).getPassword().equals(password)){  //判断密码是否匹配
                    return userList.get(i).getAdId();//登陆成功,返回登陆用户的id
                }
            }
            return 0;//密码错误
        }
        return  -1;//用户不存在
    }

    @Override
    public AdminUser getUserDetailById(Integer loginUserId){
        return adminUserMapper.selectByPrimaryKey(loginUserId);//调用数据库，通过id查询管理员信息
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        //根据id查询管理员用户
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
//            String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");  //密码加密
//            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            //比较原密码是否正确
            if (originalPassword.equals(adminUser.getPassword())) {
                //设置新密码并修改
                adminUser.setPassword(newPassword);
                if (adminUserMapper.updateByPrimaryKey(adminUser) > 0) {
                    //修改,成功则返回true
                    return true;
                }
            }
        }
        return false;
    }

    //修改用户名、账号、手机号和邮箱
    @Override
    public Boolean updateName(Integer adId, String adName, Integer accountNo , String phone, String email) {
        //根据id查询管理员用户
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(adId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //设置新名称并修改
            adminUser.setAdName(adName);
            adminUser.setAccountNo(accountNo);
            adminUser.setPhone(phone);
            adminUser.setEmail(email);
            if (adminUserMapper.updateByPrimaryKey(adminUser) > 0) {
                //修改,成功则返回true
                return true;
            }
        }
        return false;
    }
}
