package com.orive.security.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/notices")
@Tag(name = "Notice")
@CrossOrigin(origins = "*")
public class NoticeController {

    private static final Logger logger = Logger.getLogger(NoticeController.class.getName());

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/")
    public ResponseEntity<List<Notice>> getAllNotices() {
        List<Notice> notices = noticeService.getAllNotices();
        return ResponseEntity.ok(notices);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable Long noticeId) {
        Optional<Notice> notice = noticeService.getNoticeById(noticeId);
        return notice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
        Notice createdNotice = noticeService.createNotice(notice);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotice);
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<Notice> updateNotice(@PathVariable Long noticeId, @RequestBody Notice noticeDetails) {
        Notice updatedNotice = noticeService.updateNotice(noticeId, noticeDetails);
        if (updatedNotice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedNotice);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.noContent().build();
    }
}

