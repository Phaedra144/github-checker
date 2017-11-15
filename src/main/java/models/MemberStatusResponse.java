package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class MemberStatusResponse {

    String httpStatus;
    String message;
    String state;
    String ghHandle;

    public MemberStatusResponse() {
    }

    public MemberStatusResponse(String httpStatus, String ghHandle) {
        this.httpStatus = httpStatus;
        this.ghHandle = ghHandle;
    }

    @Override
    public String toString() {
        return "MemberStatusResponse{" +
                "httpStatus='" + httpStatus + '\'' +
                ", state='" + state + '\'' +
                ", ghHandle='" + ghHandle + '\'' +
                '}';
    }
}
