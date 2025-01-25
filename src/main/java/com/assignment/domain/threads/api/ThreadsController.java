package com.assignment.domain.threads.api;

import com.assignment.domain.threads.service.ThreadsService;
import com.assignment.domain.users.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/thread")
@RequiredArgsConstructor
public class ThreadsController {
    private final ThreadsService threadsService;

    @DeleteMapping("/{thread-id}")
    public void delete(@LoginUser String userId,
                       @PathVariable(value = "thread-id") Long threadId) {
        threadsService.deleteByThreadId(threadId);
    }
}
