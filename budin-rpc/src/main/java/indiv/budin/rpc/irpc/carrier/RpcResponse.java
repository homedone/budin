package indiv.budin.rpc.irpc.carrier;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class RpcResponse implements Serializable {
    private String messageId;
    private Integer code;
    private boolean status;
    private String message;

    private String messageVersion;
    private Object data;

    public RpcResponse(ResponseResult result, Object data) {
        this.message = result.getMessage();
        this.data = data;
        this.code = result.getCode();
        this.status = result.isSuccess();
    }

    public static RpcResponse success(String messageId,String messageVersion, Object data) {
        return success(null,messageId,messageVersion,data);
    }

    public static RpcResponse success(ResponseResult responseResult,String messageId,String messageVersion, Object data) {
        RpcResponse response;
        if (responseResult==null){
            response=new RpcResponse(CommonResponse.SUCCESS, data);
        }else{
            response=new RpcResponse(responseResult, data);
        }
        response.setMessageId(messageId);
        response.setMessageVersion(messageVersion);
        return response;
    }
    public static RpcResponse fail(){
        return fail(null);
    }
    public static RpcResponse fail(ResponseResult result) {
        if (result==null){
            return new RpcResponse(CommonResponse.FAIL, null);
        }
        return new RpcResponse(result, null);
    }
}
