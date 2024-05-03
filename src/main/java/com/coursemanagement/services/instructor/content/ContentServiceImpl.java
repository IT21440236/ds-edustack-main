package com.coursemanagement.services.instructor.content;

import com.coursemanagement.dto.ContentDto;
import com.coursemanagement.dto.LearnerProgressDto;
import com.coursemanagement.entity.Content;
import com.coursemanagement.entity.Course;
import com.coursemanagement.entity.LearnerProgress;
import com.coursemanagement.exception.ResourceNotFoundException;
import com.coursemanagement.repository.ContentRepository;
import com.coursemanagement.repository.CourseRepository;
import com.coursemanagement.repository.LearnerProRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService{

    private final CourseRepository courseRepository;

    private final ContentRepository contentRepository;

    private final LearnerProRepository learnerProgressRepository;
    @Override
    public ContentDto addContent(ContentDto contentDto) throws IOException {
        Content content = new Content();

        content.setTitle(contentDto.getTitle());
        content.setContentType(contentDto.getContentType());
        content.setDescription(contentDto.getDescription());
        content.setStatus(contentDto.getStatus());
        content.setImg(contentDto.getImg().getBytes());

//        content.setName(contentDto.getName());
//        content.setDescription(productDto.getDescription());
//        content.setPrice(productDto.getPrice());
//        content.setImg(productDto.getImg().getBytes());

        Course course = courseRepository.findById(contentDto.getCourseId()).orElseThrow();

        content.setCourse(course);

        return contentRepository.save(content).getDto();
    }

    @Override
    public List<ContentDto> getAllContents() {
        List<Content> contents = contentRepository.findAll();

        return contents.stream().map(Content :: getDto).collect(Collectors.toList());
    }

    @Override
    public Content getOneContent(Long contentId) {
        Content oneContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content", "content id", contentId));

        return oneContent;
    }

    @Override
    public Content updateContent(ContentDto contentDto, Long contentId) {

        Content updateContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content", "content id", contentId));

        Course course = this.courseRepository.findById(contentDto.getCourseId()).orElseThrow(() -> new ResourceNotFoundException("Course", "course id", contentDto.getCourseId()));

        updateContent.setTitle(contentDto.getTitle());
        updateContent.setDescription(contentDto.getDescription());
        updateContent.setContentType(contentDto.getContentType());
        updateContent.setStatus(contentDto.getStatus());
//        updateContent.setImg(contentDto.getImg().getBytes());
        updateContent.setCourse(course);

        return this.contentRepository.save(updateContent);

    }

    @Override
    public Content updatestatusContent(String status, Long contentId) {
        Content updateContent = this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content", "content id", contentId));

        System.out.println(status);

        updateContent.setStatus(status);

        return this.contentRepository.save(updateContent);
    }

    @Override
    public LearnerProgress markContentAsComplete(LearnerProgressDto learnerProgressDto) {
        Long learnerId = learnerProgressDto.getLearnerId();
        Long contentId = learnerProgressDto.getContentId();
        LearnerProgress learnerProgress = learnerProgressRepository.findByLearnerIdAndContentId(learnerId, contentId);
        if (learnerProgress == null) {
            learnerProgress = new LearnerProgress();
            learnerProgress.setLearnerId(learnerId);
            learnerProgress.setContentId(contentId);
            learnerProgress.setCourseId(learnerProgressDto.getCourseId());
            learnerProgress.setCompleted(true);
            return learnerProgressRepository.save(learnerProgress);
        } else {
            learnerProgress.setCompleted(true);
            return learnerProgressRepository.save(learnerProgress);
        }
    }

    @Override
    public Map<Long, Double> getLearnerProgressByCourse(Long learnerId) {
        List<LearnerProgress> learnerProgressList = learnerProgressRepository.findByLearnerId(learnerId);

        // Grouping learner progress by courseId
        Map<Long, List<LearnerProgress>> progressByCourse = learnerProgressList.stream()
                .collect(Collectors.groupingBy(LearnerProgress::getCourseId));

        // Calculating completion percentage for each course
        Map<Long, Double> progressByCourseMap = new HashMap<>();
        for (Map.Entry<Long, List<LearnerProgress>> entry : progressByCourse.entrySet()) {
            Long courseId = entry.getKey();
            List<LearnerProgress> courseProgress = entry.getValue();

            // Counting completed contents
            long completedContents = courseProgress.stream()
                    .filter(LearnerProgress::isCompleted)
                    .count();

            // Total contents in the course
//            long totalContents = courseProgress.size();
            int contentCount = contentRepository.countByCourseId(courseId);

            // Calculating completion percentage
            double completionPercentage = ((double) completedContents / contentCount) * 100;

            // Putting the completion percentage in the map
            progressByCourseMap.put(courseId, completionPercentage);
        }

        return progressByCourseMap;
    }

    @Override
    public void deleteContent(Long contentId) {
        this.contentRepository.delete(this.contentRepository.findById(contentId).orElseThrow(() -> new ResourceNotFoundException("Content", "Content Id", contentId)));
    }

    @Override
    public List<LearnerProgress> getAllLearnerProgress() {
        return learnerProgressRepository.findAll();
    }
}
