package mutsa.session5.global.apipayload.exception;

import mutsa.session5.global.apipayload.code.BaseErrorCode;

public class MemberException extends ProjectException {
    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
