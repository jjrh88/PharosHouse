package mabel_tech.com.scanovate_demo;

import mabel_tech.com.scanovate_demo.model.CloseResponse;

public interface ScanovateHandler {

    void onSuccess(CloseResponse response, int code, String uuidDevice);
    void onFailure(CloseResponse response);
}
