package cn.mydemo.simpleblogsystem.bean;

/**
 * RegisterResult
 * 用于存储注册内容的验证信息
 * 用户名和邮箱是否存在等
 */
public class RegisterResult {

    public Boolean usernameError;

    public Boolean emailError;

    public Boolean result;

    public RegisterResult(Boolean usernameError, Boolean emailError, Boolean result) {
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.result = result;
    }

    public Boolean getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(Boolean usernameError) {
        this.usernameError = usernameError;
    }

    public Boolean getEmailError() {
        return emailError;
    }

    public void setEmailError(Boolean emailError) {
        this.emailError = emailError;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
