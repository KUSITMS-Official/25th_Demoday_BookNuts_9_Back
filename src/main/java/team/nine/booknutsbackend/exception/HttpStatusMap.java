package team.nine.booknutsbackend.exception;

import org.springframework.http.HttpStatus;

/**
 * 200 OK            요청 성공 기본 상태코드
 * 201 Created       새로운 리소스가 성공적으로 생성된 경우
 * 400 Bad Request   요청 형식이 틀렸을 경우
 * 401 Unauthorized	리소스 접근 권한이 없는 경우
 * 403 Forbidden	    해당 리소스에 접근하는 것이 허락되지 않을 경우
 * 404 Not Found	    요청한 리소스가 존재하지 않을 경우
 * 405 Method Not Allowed	요청을 처리할 메소드가 없는 경우
 * 500 Internal Server Error	서버에서 에러가 발생한 경우
 * 503 Service Unavailable	현재 이용할 수 없는 서비스
 **/

public class HttpStatusMap {
    public static HttpStatus getCode(Exception e) {
        String exception = e.getClass().getSimpleName();
        HttpStatus code;

        switch (exception) {
            case "NoSuchElementException":
            case "NotFoundEmailException":  // user 관련 커스텀 예외
                code = HttpStatus.NOT_FOUND;
                break;
            case "ExpiredJwtException":     //토큰 관련 예외
            case "MalformedJwtException":
            case "UnsupportedJwtException":
                code = HttpStatus.UNAUTHORIZED;
                break;
            default:
                code = HttpStatus.BAD_REQUEST;
                break;
        }
        return code;
    }
}