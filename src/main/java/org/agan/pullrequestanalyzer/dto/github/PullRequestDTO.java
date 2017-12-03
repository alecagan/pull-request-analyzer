package org.agan.pullrequestanalyzer.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PullRequestDTO {
    private Integer id;
    private String url;
    private String htmlUrl;
    private String diffUrl;
    private String patchUrl;
    private String issueUrl;
    private String commitsUrl;
    private String reviewCommentsUrl;
    private String reviewCommentUrl;
    private String commentsUrl;
    private String statusesUrl;
    private Integer number;
    private String state;
    private String title;
    private String body;
    private UserDTO assignee;
    private MilestoneDTO milestone;
    private Boolean locked;
    private String createdAt;
    private String updatedAt;
    private String closedAt;
    private String mergedAt;
    private CommitDTO head;
    private CommitDTO base;
    private List<String> links;
    private UserDTO User;

    public PullRequestDTO()
    {
        links = new ArrayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    public String getIssueUrl() {
        return issueUrl;
    }

    public void setIssueUrl(String issueUrl) {
        this.issueUrl = issueUrl;
    }

    public String getCommitsUrl() {
        return commitsUrl;
    }

    public void setCommitsUrl(String commitsUrl) {
        this.commitsUrl = commitsUrl;
    }

    public String getReviewCommentsUrl() {
        return reviewCommentsUrl;
    }

    public void setReviewCommentsUrl(String reviewCommentsUrl) {
        this.reviewCommentsUrl = reviewCommentsUrl;
    }

    public String getReviewCommentUrl() {
        return reviewCommentUrl;
    }

    public void setReviewCommentUrl(String reviewCommentUrl) {
        this.reviewCommentUrl = reviewCommentUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public String getStatusesUrl() {
        return statusesUrl;
    }

    public void setStatusesUrl(String statusesUrl) {
        this.statusesUrl = statusesUrl;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserDTO getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDTO assignee) {
        this.assignee = assignee;
    }

    public MilestoneDTO getMilestone() {
        return milestone;
    }

    public void setMilestone(MilestoneDTO milestone) {
        this.milestone = milestone;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getMergedAt() {
        return mergedAt;
    }

    public void setMergedAt(String mergedAt) {
        this.mergedAt = mergedAt;
    }

    public CommitDTO getHead() {
        return head;
    }

    public void setHead(CommitDTO head) {
        this.head = head;
    }

    public CommitDTO getBase() {
        return base;
    }

    public void setBase(CommitDTO base) {
        this.base = base;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    @JsonProperty("_links")
    public void setLinks(LinksDTO linkObject)
    {
        links.add(linkObject.getComments());
        links.add(linkObject.getCommits());
        links.add(linkObject.getHtml());
        links.add(linkObject.getIssue());
        links.add(linkObject.getReview_comment());
        links.add(linkObject.getReview_comments());
        links.add(linkObject.getSelf());
        links.add(linkObject.getStatuses());
    }

    public UserDTO getUser() {
        return User;
    }

    public void setUser(UserDTO user) {
        User = user;
    }
}
