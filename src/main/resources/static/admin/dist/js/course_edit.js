var editor;
var courseVideo="url";
$(function () {

    //富文本编辑器 用于商品详情编辑
    const E = window.wangEditor;

    const editorConfig = { MENU_CONF: {} }
    editorConfig.MENU_CONF['uploadImage'] = {
        //配置服务端图片上传地址
        server: '/upload/files',
        // 超时时间5s
        timeout: 5 * 1000,
        fieldName: 'files',
        // 选择文件时的类型限制，默认为 ['image/*']
        allowedFileTypes: ['image/*'],
        // 限制图片大小 50M
        maxFileSize: 50 * 1024 * 1024,
        base64LimitSize: 5 * 1024,

        onBeforeUpload(file) {
            console.log('onBeforeUpload', file)

            return file // will upload this file
            // return false // prevent upload
        },
        onProgress(progress) {
            console.log('onProgress', progress)
        },
        onSuccess(file, res) {
            console.log('onSuccess', file, res)
        },
        onFailed(file, res) {
            alert(res.message)
            console.log('onFailed', file, res)
        },
        onError(file, err, res) {
            alert(err.message)
            console.error('onError', file, err, res)
        },
        customInsert: function (result,insertImgFn) {
            if (result != null && result.resultCode == 200) {
                // insertImgFn 可把图片插入到编辑器，传入图片 src ，执行函数即可
                result.data.forEach(img => {
                    insertImgFn(img)
                });
            } else if (result != null && result.resultCode != 200) {
                Swal.fire({
                    text: result.message,
                    icon: "error",iconColor:"#f05b72",
                });
            } else {
                Swal.fire({
                    text: "error",
                    icon: "error",iconColor:"#f05b72",
                });
            }
        }
    }

    editor = E.createEditor({
        selector: '#editor-text-area',
        html: $(".editor-text").val(),
        config: editorConfig
    })

    const toolbar = E.createToolbar({
        editor,
        selector: '#editor-toolbar',
    })

    //图片上传插件初始化 用于课程预览图上传
    new AjaxUpload('#uploadCourseCoverImg', {
        action: '/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                Swal.fire({
                    text: "只支持jpg、png、gif格式的文件！",
                    icon: "error",iconColor:"#f05b72",
                });
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#courseCoverImg").attr("src", r.data);
                $("#courseCoverImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else if (r != null && r.resultCode != 200) {
                Swal.fire({
                    text: r.message,
                    icon: "error",iconColor:"#f05b72",
                });
                return false;
            }
            else {
                Swal.fire({
                    text: "error",
                    icon: "error",iconColor:"#f05b72",
                });
            }
        }
    });

    //视频资源上传插件初始化 用于课程视频资源上传
    new AjaxUpload('#uploadCourseVideo', {
        action: '/upload/big/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            // if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
            //     Swal.fire({
            //         text: "只支持jpg、png、gif格式的文件！",
            //         icon: "error",iconColor:"#f05b72",
            //     });
            //     return false;
            // }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                Swal.fire({
                    text: "文件上传成功",
                });
                courseVideo=r.data;
                return false;
            } else if (r != null && r.resultCode != 200) {
                Swal.fire({
                    text: r.message,
                    icon: "error",iconColor:"#f05b72",
                });
                return false;
            }
            else {
                Swal.fire({
                    text: "error",
                    icon: "error",iconColor:"#f05b72",
                });
            }
        }
    });
});


$('#saveButton').click(function () {
    var courseId = $('#courseId').val();
    var courseCategoryId = $('#levelThree option:selected').val();
    var courseName = $('#courseName').val();
    var tag = $('#tag').val();
    var originalPrice = $('#originalPrice').val();
    var sellingPrice = $('#sellingPrice').val();
    var courseIntro = $('#courseIntro').val();
    var stockNum = $('#stockNum').val();
    var courseDetailContent = editor.getHtml();
    var courseCoverImg = $('#courseCoverImg')[0].src;
    if (isNull(courseCategoryId)) {
        Swal.fire({
            text: "请选择分类",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (isNull(courseName)) {
        Swal.fire({
            text: "请输入课程名称",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (!validLength(courseName, 100)) {
        Swal.fire({
            text: "课程名称过长",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (isNull(tag)) {
        Swal.fire({
            text: "请输入课程小标签",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (!validLength(tag, 100)) {
        Swal.fire({
            text: "标签过长",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (isNull(courseIntro)) {
        Swal.fire({
            text: "请输入课程简介",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (!validLength(courseIntro, 100)) {
        Swal.fire({
            text: "简介过长",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (isNull(originalPrice) || originalPrice < 1) {
        Swal.fire({
            text: "请输入课程价格",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (isNull(sellingPrice) || sellingPrice < 1) {
        Swal.fire({
            text: "请输入课程售卖价",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (isNull(stockNum) || stockNum < 1) {
        Swal.fire({
            text: "请输入课程库存数",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }

    if (isNull(courseDetailContent)) {
        Swal.fire({
            text: "请输入课程介绍",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (!validLength(courseDetailContent, 50000)) {
        Swal.fire({
            text: "课程介绍内容过长",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    if (isNull(courseCoverImg) || courseCoverImg.indexOf('img-upload') != -1) {
        Swal.fire({
            text: "封面图片不能为空",
            icon: "error",iconColor:"#f05b72",
        });
        return;
    }
    var url = '/teacher/course/save';
    var swlMessage = '保存成功';
    var data = {
        "courseName": courseName,
        "courseIntro": courseIntro,
        "courseCategoryId": courseCategoryId,
        "tag": tag,
        "originalPrice": originalPrice,
        "sellingPrice": sellingPrice,
        "stockNum": stockNum,
        "courseDetailContent": courseDetailContent,
        "courseCoverImg": courseCoverImg,
        "courseCarousel": courseCoverImg,
        "courseVideo": courseVideo,
        // "courseSellStatus": courseSellStatus
    };
    if (courseId > 0) {
        url = '/teacher/course/update';
        swlMessage = '修改成功';
        data = {
            "courseId": courseId,
            "courseName": courseName,
            "courseIntro": courseIntro,
            "courseCategoryId": courseCategoryId,
            "tag": tag,
            "originalPrice": originalPrice,
            "sellingPrice": sellingPrice,
            "stockNum": stockNum,
            "courseDetailContent": courseDetailContent,
            "courseCoverImg": courseCoverImg,
            "courseCarousel": courseCoverImg,
            "courseVideo": courseVideo,
            // "courseSellStatus": courseSellStatus
        };
    }
    console.log(data);
    alert(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode === 200) {
                Swal.fire({
                    text: swlMessage,
                    icon: "success",iconColor:"#1d953f",
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: '返回商品列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/teacher/course";
                })
            } else {
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

$('#cancelButton').click(function () {
    window.location.href = "/teacher/course";
});

$('#levelOne').on('change', function () {
    $.ajax({
        url: '/teacher/course/categories/listForSelect?categoryId=' + $(this).val(),
        type: 'GET',
        success: function (result) {
            if (result.resultCode == 200) {
                var levelTwoSelect = '';
                var secondLevelCategories = result.data.secondLevelCategories;
                var length2 = secondLevelCategories.length;
                for (var i = 0; i < length2; i++) {
                    levelTwoSelect += '<option value=\"' + secondLevelCategories[i].categoryId + '\">' + secondLevelCategories[i].categoryName + '</option>';
                }
                $('#levelTwo').html(levelTwoSelect);
                var levelThreeSelect = '';
                var thirdLevelCategories = result.data.thirdLevelCategories;
                var length3 = thirdLevelCategories.length;
                for (var i = 0; i < length3; i++) {
                    levelThreeSelect += '<option value=\"' + thirdLevelCategories[i].categoryId + '\">' + thirdLevelCategories[i].categoryName + '</option>';
                }
                $('#levelThree').html(levelThreeSelect);
            } else {
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

$('#levelTwo').on('change', function () {
    $.ajax({
        url: '/teacher/course/categories/listForSelect?categoryId=' + $(this).val(),
        type: 'GET',
        success: function (result) {
            if (result.resultCode == 200) {
                var levelThreeSelect = '';
                var thirdLevelCategories = result.data.thirdLevelCategories;
                var length = thirdLevelCategories.length;
                for (var i = 0; i < length; i++) {
                    levelThreeSelect += '<option value=\"' + thirdLevelCategories[i].categoryId + '\">' + thirdLevelCategories[i].categoryName + '</option>';
                }
                $('#levelThree').html(levelThreeSelect);
            } else {
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