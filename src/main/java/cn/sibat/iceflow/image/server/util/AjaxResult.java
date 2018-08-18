package cn.sibat.iceflow.image.server.util;

/**
 * 封装返回前台的数据
 *
 * @author BF
 */
public class AjaxResult {
    private Integer status;
    private Object data;
    private String message;

    private AjaxResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        if (data == null) {
            data = "";
        }
        this.data = data;
    }

    /**
     * 不设置就是默认成功了
     *
     * @return
     */
    public static AjaxResult okResponse() {
        return new AjaxResult(200, "success", null);
    }

    public static AjaxResult okResponse(String message) {
        return new AjaxResult(200, message, null);
    }

    /**
     * 只设置返回数据
     *
     * @param data 返回数据
     * @return
     */
    public static AjaxResult okResponse(Object data) {
        return new AjaxResult(200, "success", data);
    }


    /**
     * 若有数据返回，则message必须修改
     *
     * @param message 返回信息
     * @param data    返回数据
     * @return
     */
    public static AjaxResult okResponse(String message, Object data) {
        return new AjaxResult(200, message, data);
    }

    /**
     * 出错相应类型，提示错误信息
     *
     * @param message 错误信息
     * @return
     */
    public static AjaxResult errorResponse(String message) {
        return new AjaxResult(500, message, null);
    }

    /**
     * 找不到信息
     *
     * @param message 提示信息
     * @return
     */
    public static AjaxResult notData(String message) {
        return new AjaxResult(404, message, null);
    }

    /**
     * 自定义返回类型
     *
     * @param status  状态码
     * @param message 返回信息
     * @param data    返回数据
     * @return
     */
    public static AjaxResult customResponse(int status, String message, Object data) {
        return new AjaxResult(status, message, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getdata() {
        return data;
    }

    public void setdata(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AjaxResult{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
