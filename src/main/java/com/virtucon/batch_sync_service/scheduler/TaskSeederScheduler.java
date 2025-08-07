package com.virtucon.batch_sync_service.scheduler;

import com.virtucon.batch_sync_service.entity.FileEntity;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
import com.virtucon.batch_sync_service.repository.FileRepository;
import com.virtucon.batch_sync_service.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Component
public class TaskSeederScheduler {

    private static final Logger log = LoggerFactory.getLogger(TaskSeederScheduler.class);
    
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;
    private final Random random = new Random();
    
    private final String[] owners = {"john.doe", "jane.smith", "admin", "system", "user123", "analyst", "developer"};
    private final String[] urlPrefixes = {"https://storage.example.com/audio/", "https://cdn.example.com/files/", "https://bucket.example.com/recordings/"};

    public TaskSeederScheduler(FileRepository fileRepository, TaskRepository taskRepository) {
        this.fileRepository = fileRepository;
        this.taskRepository = taskRepository;
    }

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void seedRandomTask() {
        try {
            String uniqueUrl = generateUniqueUrl();
            
            FileEntity file = new FileEntity(uniqueUrl);
            file = fileRepository.save(file);
            
            TaskType randomTaskType = getRandomTaskType();
            TaskStatus randomTaskStatus = getRandomTaskStatus();
            String randomOwner = getRandomOwner();
            
            TaskEntity task = new TaskEntity(file, randomTaskType, randomTaskStatus, randomOwner);
            
            task = taskRepository.save(task);
            
            log.info("Created new task: id={}, file_url={}, type={}, status={}, owner={}", 
                    task.getId(), file.getUrl(), randomTaskType, randomTaskStatus, randomOwner);
                    
        } catch (Exception e) {
            log.error("Error creating scheduled task and file", e);
        }
    }
    
    private String generateUniqueUrl() {
        String prefix = urlPrefixes[random.nextInt(urlPrefixes.length)];
        String filename = UUID.randomUUID().toString();
        String extension = getRandomFileExtension();
        return prefix + filename + extension;
    }
    
    private String getRandomFileExtension() {
        String[] extensions = {".wav", ".mp3", ".mp4", ".flac", ".m4a"};
        return extensions[random.nextInt(extensions.length)];
    }
    
    private TaskType getRandomTaskType() {
        TaskType[] types = TaskType.values();
        return types[random.nextInt(types.length)];
    }
    
    private TaskStatus getRandomTaskStatus() {
        TaskStatus[] statuses = TaskStatus.values();
        return statuses[random.nextInt(statuses.length)];
    }
    
    private String getRandomOwner() {
        return owners[random.nextInt(owners.length)];
    }
}