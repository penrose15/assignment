package com.assignment.global.sse;

import com.assignment.global.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.assignment.global.exception.errortype.GlobalErrorCode.IO_EXCEPTION;

@Slf4j
@Repository
public class SseEmitterRepository {
    private static final Long TIMEOUT = 20 * 1000L;
    private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>(); // sseEmitter 객체 저장용

    public SseEmitter subscribe(String id) {
        SseEmitter emitter = new SseEmitter(TIMEOUT);
        sseEmitters.put(id, emitter);
        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            sseEmitters.remove(id);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            sseEmitters.remove(id);
            emitter.complete();
        });

        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("chatResponse")
                    .data("connected"));
        } catch (IOException e) {
            log.error("sse send error ", e);
            throw new GlobalException(IO_EXCEPTION);
        }
        return emitter;
    }

    public void sendChatResponse(String id, String message) {
        SseEmitter emitter = sseEmitters.get(id);
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                            .name("message")
                    .data(message));
        } catch (IOException e) {
            log.error("sse send error ", e);
            throw new GlobalException(IO_EXCEPTION);
        }
    }
}
