package com.gitee.easyopen.server.api.param;

import com.gitee.easyopen.doc.DataType;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UploadParam {

    @ApiDocField(description = "商品名称", required = true, example = "iphoneX")
    @NotEmpty(message = "商品名称不能为空")
    @Length(min = 3, max = 20, message = "{goods.name.length}=3,20")
    private String goods_name;

    // 这里定义上传的文件，属性名称对应客户端上传的name
    @ApiDocField(description = "头像图片", required = true, dataType = DataType.FILE)
    @NotNull(message = "请上传头像图片")
    private MultipartFile headImg;

    @ApiDocField(description = "身份证图片", required = true, dataType = DataType.FILE)
    @NotNull(message = "请上传身份证图片")
    private MultipartFile idcardImg;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public MultipartFile getHeadImg() {
        return headImg;
    }

    public void setHeadImg(MultipartFile headImg) {
        this.headImg = headImg;
    }

    public MultipartFile getIdcardImg() {
        return idcardImg;
    }

    public void setIdcardImg(MultipartFile idcardImg) {
        this.idcardImg = idcardImg;
    }

}
