package framework.enums;

public enum ErrorCodeEnum {
    SUCCESS(0, "成功"),

    // ========== 客户端错误段 ==========
    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "账号未登录"),
    FORBIDDEN(403, "没有该操作权限"),
    NOT_FOUND(404, "请求未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不正确"),
    LOCKED(423, "请求失败，请稍后重试"), // 并发请求，不允许
    TOO_MANY_REQUESTS(429, "请求过于频繁，请稍后重试"),

    // ========== 服务端错误段 ==========
    INTERNAL_SERVER_ERROR(500, "系统异常"),

    // ========== 自定义错误段 ==========
    REPEATED_REQUESTS(900, "重复请求，请稍后重试"), // 重复请求
    DEMO_DENY(901, "演示模式，禁止写操作"),

    UNKNOWN(999, "未知错误");

    private final int code;
    private final String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
