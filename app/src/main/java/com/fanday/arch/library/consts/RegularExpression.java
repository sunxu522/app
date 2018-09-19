package com.fanday.arch.library.consts;

/**
 * AiligouApp
 * com.bingru.borrowmoneyapp.library.consts
 * Created by Chris Chen on 2017/7/4 13:48.
 * Explain:正则表达式
 */

public interface RegularExpression {
    //18位身份证
    String EXPRESSION_ID_18 =  "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    String EXPRESSION_ID_15 =  "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";
    //6位验证码
    String EXPRESSION_VERIFICATION_CODE_6 = "^[0-9]{6}$";
    String EXPRESSION_VERIFICATION_CODE_5 = "^[0-9]{5}$";
    String EXPRESSION_VERIFICATION_CODE = "^[0-9]{4,6}$";
    String EXPRESSION_NUM_1_100 = "^([1-9][0-9]{0,1}|100)$";
    //电话号码
    String EXPRESSION_PHONENUMBER = "\\d{3}-\\d{8}|\\d{4}-\\{7,8}";
    //手机号码
    String EXPRESSION_MOBLIE_PHONENUMBER = "^(1(3|4|5|7|8))[0-9]{9}$";
    //固话号码
    String EXPRESSION_FIX_PHONENUMBER = "^d{4}[0-9]{7,8}$";
    //网址
    String EXPRESSION_URL = "[a-zA-z]+://[^\\s]*";
    //电子邮箱
    String EXPRESSION_EMAIL = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    //icon_login_qq
    String EXPRESSION_QQ = "[1-9][0-9]{4,}";
    //icon_login_qq
    String EXPRESSION_ZIP_CODE = "[1-9]\\d{5}(?!\\d)";
     //6到15位密码
    String EXPRESSION_PWD_6_15 = "^[0-9a-zA-Z_#]{6,15}$";
}
