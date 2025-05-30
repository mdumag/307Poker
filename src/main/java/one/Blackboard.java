package one;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.Duration;

/**
 * Shared data structure for the application.
 *
 * @author javiergs
 * 
 * modified by Matthew Dumag
 * 
 */
public class Blackboard {
    private static LinkedList<String> names    = new LinkedList<>();
    private static LinkedList<String> stories  = new LinkedList<>();
    private static String currentRoom;
    private static String mode;

    public static void addName(String name) {
        names.add(name);
    }

    public static void addStory(String story) {
        stories.add(story);
    }

    public static void addCurrentRoom(String name) {
        currentRoom = name;
    }

    public static void addCurrentMode(String selectedItem) {
        mode = selectedItem;
    }

    public enum Role   { MODERATOR, PLAYER, OBSERVER }
    public enum Status { OFFLINE,   ONLINE,    VOTED   }

    private static Map<String,Role>    roles    = new HashMap<>();
    private static Map<String,Status>  statuses = new HashMap<>();

    public static List<String> getNames() {
        return new ArrayList<>(names);
    }

    public static String getCurrentUser() {
        return names.isEmpty() ? null : names.getFirst();
    }

    public static void setRole(String name, Role role) {
        roles.put(name, role);
    }

    public static Role getRole(String name) {
        return roles.getOrDefault(name, Role.PLAYER);
    }

    public static void setStatus(String name, Status status) {
        statuses.put(name, status);
    }

    public static Status getStatus(String name) {
        return statuses.getOrDefault(name, Status.OFFLINE);
    }

    public static List<String> getStories() {
        return new ArrayList<>(stories);
    }

    private static class VoteRecord {
        LocalDateTime             startTime;
        Map<String,String>        votes     = new HashMap<>();
        Map<String,LocalDateTime> voteTimes = new HashMap<>();
    }
    private static Map<String,VoteRecord> voteData          = new HashMap<>();
    private static String                 currentStoryName  = null;

    public static void startVoting(String story) {
        currentStoryName = story;
        VoteRecord vr = new VoteRecord();
        vr.startTime = LocalDateTime.now();
        voteData.put(story, vr);
    }

    public static void recordVote(String story, String participant, String vote) {
        VoteRecord vr = voteData.get(story);
        if (vr != null) {
            vr.votes.put(participant, vote);
            vr.voteTimes.put(participant, LocalDateTime.now());
            setStatus(participant, Status.VOTED);
        }
    }

    public static String getCurrentStory() {
        return currentStoryName;
    }

    public static void setCurrentStory(String story) {
        currentStoryName = story;
    }

    public static String getVote(String participant) {
        VoteRecord vr = voteData.get(currentStoryName);
        return vr == null ? "" : vr.votes.getOrDefault(participant, "");
    }

    public static LocalDateTime getVoteTime(String participant) {
        VoteRecord vr = voteData.get(currentStoryName);
        return vr == null ? null : vr.voteTimes.get(participant);
    }

    public static double getAverageVote(String story) {
        VoteRecord vr = voteData.get(story);
        if (vr == null || vr.votes.isEmpty()) return 0;
        return vr.votes.values().stream()
                 .mapToDouble(v -> {
                     try { return Double.parseDouble(v); }
                     catch (Exception e) { return 0; }
                 })
                 .average().orElse(0);
    }

    public static LocalDateTime getVoteStartTime(String story) {
        VoteRecord vr = voteData.get(story);
        return vr == null ? null : vr.startTime;
    }

    public static Duration getVotingDuration(String story) {
        VoteRecord vr = voteData.get(story);
        if (vr == null || vr.voteTimes.isEmpty()) return Duration.ZERO;
        LocalDateTime last = vr.voteTimes.values().stream()
                                 .max(LocalDateTime::compareTo).orElse(vr.startTime);
        return Duration.between(vr.startTime, last);
    }

    public static Duration getAvgVotingTime(String story) {
        VoteRecord vr = voteData.get(story);
        if (vr == null || vr.voteTimes.isEmpty()) return Duration.ZERO;
        Duration total = vr.voteTimes.values().stream()
                            .map(t -> Duration.between(vr.startTime, t))
                            .reduce(Duration.ZERO, Duration::plus);
        return total.dividedBy(vr.voteTimes.size());
    }

    public static String getFastestVoter(String story) {
        VoteRecord vr = voteData.get(story);
        if (vr == null || vr.voteTimes.isEmpty()) return "";
        return vr.voteTimes.entrySet().stream()
                 .min((a,b) -> Duration.between(vr.startTime,a.getValue())
                                    .compareTo(Duration.between(vr.startTime,b.getValue())))
                 .map(Map.Entry::getKey).orElse("");
    }

    public static String getSlowestVoter(String story) {
        VoteRecord vr = voteData.get(story);
        if (vr == null || vr.voteTimes.isEmpty()) return "";
        return vr.voteTimes.entrySet().stream()
                 .max((a,b) -> Duration.between(vr.startTime,a.getValue())
                                    .compareTo(Duration.between(vr.startTime,b.getValue())))
                 .map(Map.Entry::getKey).orElse("");
    }

    public static String getEstimateValue(String story) {
        return "";
    }

    public static void renameParticipant(String oldName, String newName) {
        int idx = names.indexOf(oldName);
        if (idx >= 0) names.set(idx, newName);

        if (roles.containsKey(oldName)) {
            Role r = roles.remove(oldName);
            roles.put(newName, r);
        }
        if (statuses.containsKey(oldName)) {
            Status s = statuses.remove(oldName);
            statuses.put(newName, s);
        }
        voteData.values().forEach(vr -> {
            if (vr.votes.containsKey(oldName)) {
                String v = vr.votes.remove(oldName);
                vr.votes.put(newName, v);
            }
            if (vr.voteTimes.containsKey(oldName)) {
                LocalDateTime t = vr.voteTimes.remove(oldName);
                vr.voteTimes.put(newName, t);
            }
        });
    }

    public static void removeParticipant(String name) {
        names.remove(name);
        roles.remove(name);
        statuses.remove(name);
        voteData.values().forEach(vr -> {
            vr.votes.remove(name);
            vr.voteTimes.remove(name);
        });
    }
}
