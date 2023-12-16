package tn.uma.boutiti.bouzidi.ing.projet.dto;
import java.time.LocalDate;
import java.util.List;

public class TaskDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    private List<Long> labelIds; 
    private Long projectId;

   
    public TaskDTO(String title, String description, LocalDate dueDate, boolean completed, List<Long> labelIds) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
        this.labelIds = labelIds;
    }

  
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<Long> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
    }
    public Long getProjectId() {
        return projectId;
    }

}
