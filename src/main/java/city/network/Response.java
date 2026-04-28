package city.network;

import java.io.Serializable;

public class Response implements Serializable {
    public String message;
    public Object data;

    public Response(String message) {
        this.message = message;
    }

    public Response (String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }


}
