package com.slxy.analysis.shiro;

import com.slxy.analysis.model.Permission;
import com.slxy.analysis.model.User;
import com.slxy.analysis.service.PermissionService;
import com.slxy.analysis.service.StudentService;
import com.slxy.analysis.service.TeacherService;
import com.slxy.analysis.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author: sherlock
 * @description:自定义realm
 * @date: 2019/8/31 18:11
 */

/**
 * @author: sherlock
 * @description:自定义realm
 * @date: 2019/8/31 18:11
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;
    /**
     * 执行认证逻辑,判断用户名和密码
     * @param authenticationToken  封装的前台传入的用户名和密码
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //判断登录用户的身份是学生还是教师
        String role = token.getHost();
        //根据不同的身份进行验证
        User user;
        if (User.STUDENT.equals(role)){
            //根据学生用户名获取其所在的年级
            Integer grade = Integer.valueOf(token.getUsername().substring(0,2));
            user = studentService.getStudentPassword(grade,token.getUsername());
            if (user != null) {
                //在用户信息中添加自己的角色信息
                user.setRole("student");
            }
            //添加用户角色
        }else if(User.TEACHER.equals(role)){
            user = teacherService.getTeacherById(token.getUsername());
            if (user == null) {
                //shiro底层会抛出UnKnowAccountException
                return null;
            }
            //在用户信息中添加自己的角色信息
            user.setRole("teacher");
        }else {
            //此时系统出错，shiro底层会抛出UnKnowAccountException
            return null;
        }
        if (user == null) {
            //shiro底层会抛出UnKnowAccountException
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }

    /**
     *  执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户的授权字符串集合，防止多次授权
        Set<String> stringPermissions = info.getStringPermissions();
        if (stringPermissions != null){
            return info;
        }
        Subject subject = SecurityUtils.getSubject();
        //获取当前用户信息
        User user = (User) subject.getPrincipal();
        //根据当前用户是学生还是老师来查询其权限
        if(User.STUDENT.equals(user.getRole())){
            //学生的权限角色为1，获取当前学生的授权字符串列表
            List<Permission> permissions = permissionService.listTeacherPermissionsByRole("1");
            for (Permission p : permissions) {
                //添加资源的授权字符串
                info.addStringPermission(p.getPermission());
            }
            //授权学生角色
            info.addRole("student");
        }else if(User.TEACHER.equals(user.getRole())){
            //获取当前教师权限角色
            String role = teacherService.getTeacherRole(user.getId());
            //获取当前用户的授权字符串列表
            List<Permission> permissions = permissionService.listTeacherPermissionsByRole(role);
            for (Permission p : permissions) {
                //添加资源的授权字符串
                info.addStringPermission(p.getPermission());
            }
            //授权教师角色
            info.addRole("teacher");
        }
        return info;
    }
}
