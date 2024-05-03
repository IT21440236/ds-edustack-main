package com.coursemanagement.services.instructor.content;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.LearnerProgressDto;
import com.coursemanagement.entity.Content;
import com.coursemanagement.entity.LearnerProgress;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ContentService {

    ContentDto addContent(ContentDto contentDto) throws IOException;

    List<ContentDto> getAllContents();

    Content getOneContent(Long taskId);

    Content updateContent(ContentDto contentDto, Long contentId);

    Content updatestatusContent(String status, Long contentId);

    public LearnerProgress markContentAsComplete(LearnerProgressDto learnerProgressDto);

    public Map<Long, Double> getLearnerProgressByCourse(Long learnerId);

    void deleteContent(Long contentId);

    List<LearnerProgress> getAllLearnerProgress();

}
