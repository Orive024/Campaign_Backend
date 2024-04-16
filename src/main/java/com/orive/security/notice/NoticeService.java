package com.orive.security.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NoticeService {

    private static final Logger logger = Logger.getLogger(NoticeService.class.getName());

    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public Optional<Notice> getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId);
    }

    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    public Notice updateNotice(Long noticeId, Notice noticeDetails) {
        Notice notice = noticeRepository.findById(noticeId).orElse(null);
        if (notice == null) {
            logger.log(Level.WARNING, "Notice not found with id: " + noticeId);
            return null;
        }

        notice.setTitle(noticeDetails.getTitle());
        notice.setDate(noticeDetails.getDate());
        notice.setDescription(noticeDetails.getDescription());

        return noticeRepository.save(notice);
    }

    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
        logger.log(Level.INFO, "Notice deleted with id: " + noticeId);
    }
}

