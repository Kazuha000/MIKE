$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/teachers/list',//获取数据的地址
        datatype: "json",
        // label:如果colNames为空则用此值来作为列的显示名称，如果都没有设置则使用name 值
        // name：列显示的名称； index：传到服务器端用来排列用的列名称
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '昵称', name: 'name', index: 'name', width: 80},
            {label: '登录名', name: 'accountNo', index: 'accountNo', width: 80},
            {label: '性别', name: 'sex', index: 'sex', width: 60,formatter: sexFormatter},
            {label: '手机号', name: 'phone', index: 'phone', width: 80},
            {label: '身份状态', name: 'status', index: 'status', width: 60, formatter: lockedFormatter},
            // {label: '是否注销', name: 'isDeleted', index: 'isDeleted', width: 60, formatter: deletedFormatter},
            {label: '注册时间', name: 'creatTime', index: 'creatTime', width: 120}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function lockedFormatter(cellvalue) {
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">锁定</button>";
        }
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">正常</button>";
        }
        else if (cellvalue == -1) {
        return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">已注销</button>";
    }
}

    function sexFormatter(cellvalue) {
        if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">男</button>";
        }
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">未知</button>";
        }
        else if (cellvalue == 2) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">女</button>";
        }
    }
    });

    /**
     * jqGrid重新加载
     */
    function reload() {
        var page = $("#jqGrid").jqGrid('getGridParam', 'page');
        $("#jqGrid").jqGrid('setGridParam', {
            page: page
        }).trigger("reloadGrid");
    }
    function  transformsex(sex){
        if (sex == 0){ sex = "未知"}
        if (sex == 1){ sex = "男"}
        else if (sex == 2){ sex = "女"}
        return sex;
    }

    function teacherAdd(){
        reset();
    $('.modal-title').html('教师添加');
    $('#teacherModal').modal('show');
    }

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var name = $("#teacherName").val();
    var accountNo = $("#teacherAccountNo").val();
    var password = $("#teacherPassword").val();
    var sex = $("#teacherSex").val();
    var phone = $("#teacherPhone").val();
    var email = $("#teacherEmail").val();

    var data = {
        "name": name,
        "accountNo":accountNo,
        "password":password,
        "sex":sex,
        "phone": phone,
        "email": email,
    };
    var url = '/admin/teachers/save';
    var id = getSelectedRowWithoutAlert();
    if (id != null) {
        url = '/admin/teachers/update';
        data = {
            "id": id,
            "name": name,
            "accountNo":accountNo,
            "password":password,
            "sex":sex,
            "phone": phone,
            "email": email,
        };
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                // modal('hide')手动隐藏模态框。
                $('#teacherModal').modal('hide');
                Swal.fire({
                    text: "保存成功",
                    icon: "success",iconColor:"#1d953f",
                });
                reload();
            } else {
                $('#teacherModal').modal('hide');
                Swal.fire({
                    text: result.message,
                    icon: "error",iconColor:"#f05b72",
                });
            }
            ;
        },
        error: function () {
            Swal.fire({
                text: "操作失败",
                icon: "error",iconColor:"#f05b72",
            });
        }
    });
});

function teacherEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    //请求数据
    $.get("/admin/teachers/info/" + id, function (r) {
        if (r.resultCode == 200 && r.data != null) {
            //填充数据至modal
            $("#teacherName").val(r.data.name);
            $("#teacherAccountNo").val(r.data.accountNo);
            $("#teacherPassword").val(r.data.password);
            $("#teacherSex").val(r.data.sex);
            $("#teacherPhone").val(r.data.phone);
            $("#teacherEmail").val(r.data.email);

        }
    });
    $('.modal-title').html('教师编辑');
    $('#teacherModal').modal('show');
}
    function lockUser(lockStatus) {
        var ids = getSelectedRows();
        if (ids == null) {
            return;
        }
        if (lockStatus != 0 && lockStatus != 1) {
            Swal.fire({
                text: '非法操作',
                icon: "error",iconColor:"#f05b72",
            });
        }
        Swal.fire({
            title: "确认弹框",
            text: "确认要修改账号状态吗?",
            icon: "warning",iconColor:"#dea32c",
            showCancelButton: true,
            confirmButtonText: '确认',
            cancelButtonText: '取消'
        }).then((flag) => {
                if (flag.value) {
                    $.ajax({
                        type: "POST",
                        url: "/admin/teachers/lock/" + lockStatus,
                        contentType: "application/json",
                        data: JSON.stringify(ids),
                        success: function (r) {
                            if (r.resultCode == 200) {
                                Swal.fire({
                                    text: "操作成功",
                                    icon: "success",iconColor:"#1d953f",
                                });
                                $("#jqGrid").trigger("reloadGrid");
                            } else {
                                Swal.fire({
                                    text: r.message,
                                    icon: "error",iconColor:"#f05b72",
                                });
                            }
                        }
                    });
                }
            }
        )
        ;
    }
function reset() {
    $("#teacherAccountNo").val('##');
    $("#teacherName").val('##');
    $("#teacherPassword").val('##');
    $("#teacherEmail").val('##');
    $("#teacherPhone").val('##');
}


