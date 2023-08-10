$(function () {
    //修改个人信息
    $('#updateUserNameButton').click(function() {
        $("#updateUserNameButton").attr("disabled",true);
        var loginUser = $('#loginUser').val();
        var accountNo = $('#accountNo').val();
        var phone = $('#phone').val();
        var email = $('#email').val();
        if (validUserNameForUpdate(loginUser, accountNo, phone, email)) {
            //ajax提交数据
            var params = $("#userNameForm").serialize();
            $.ajax({
                type: "POST",
                url: "/teacher/profile/name",
                data: params,
                success: function (r) {
                    $("#updateUserNameButton").attr("disabled",false);
                    console.log(r);
                    if (r == 'success') {
                        alert('修改成功');
                        $('#updateUserName-info').css("display", "none");
                    } else {
                        alert('修改失败');
                    }
                }
            });
        }else{
            $("#updateUserNameButton").attr("disabled",false);
        }

    });
    //修改密码
    $('#updatePasswordButton').click(function () {
        $("#updatePasswordButton").attr("disabled",true);
        var originalPassword = $('#originalPassword').val();
        var newPassword = $('#newPassword').val();
        if (validPasswordForUpdate(originalPassword, newPassword)) {
            var params = $("#userPasswordForm").serialize();
            $.ajax({
                type: "POST",
                url: "/teacher/profile/password",
                data: params,
                success: function (r) {
                    $("#updatePasswordButton").attr("disabled",false);
                    console.log(r);
                    if (r == 'success') {
                        alert('修改成功');
                        window.location.href = '/admin/login';
                    } else {
                        alert('修改失败');
                    }
                }
            });
        }else {
            $("#updatePasswordButton").attr("disabled",false);
        }

    });
})

/**
 * 名称验证
 */
function validUserNameForUpdate(loginUser, accountNo, phone, email) {
    /*trim() 方法用于删除字符串的头尾空白符，空白符包括：空格、制表符 tab、换行符等其他空白符等。*/
    if (isNull(loginUser) || loginUser.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入用户名！");
        // alert("请输入用户名")
        return false;
    }
    if (isNull(accountNo) || accountNo.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("账号不能为空！");
        // alert("账号不能为空")
        return false;
    }
    if (isNull(phone) || phone.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入手机号！");
        // alert("手机号不能为空")
        return false;
    }
    if (isNull(email) || email.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入邮箱！");
        // alert("邮箱不能为空")
        return false;
    }
    if (!validUserName(loginUser)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的用户名！");
        // alert("请输入符合规范的用户名")
        return false;
    }
    if (!validAccountNo(accountNo)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的账号！");
        // alert("请输入符合规范的账号")
        return false;
    }
    if (!validPhoneNumber(phone)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的手机号！");
        // alert("请输入符合规范的手机号")
        return false;
    }
    if (!validEmail(email)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的邮箱！");
        // alert("请输入符合规范的邮箱号")
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入原密码！");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("新密码不能为空！");
        return false;
    }
    if (!validPassword(newPassword)) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入符合规范的密码！");
        return false;
    }
    return true;
}
