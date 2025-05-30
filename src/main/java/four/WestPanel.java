package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import one.Blackboard;

/**
 * Panel that contains the left side of the dashboard.
 * It contains the username, start button, players, timer, invite a teammate and copy URL.
 *
 * @author javiergs
 * 
 * modified by Matthew Dumag
 * Participant list with status, role, and vote information
 * Moderator options to rename, set role, and remove participants
 * Voting results summary
 * export to CSV functionality
 */

public class WestPanel extends JPanel {
    private DefaultListModel<Participant> model;
    private JList<Participant>            participantList;
    private Participant                   currentUser;

    public WestPanel() {
        setBackground(new Color(255, 204, 204));
        setLayout(new GridLayout(8, 1, 5, 5));

        add(new JLabel("Javier"));
        add(new JButton("Start"));
        add(new JLabel("00:00:00"));
        add(new JLabel("Invite a teammate"));
        add(new JTextField("https://app.planitpoker.com"));
        add(new JButton("Copy URL"));
		add(new JLabel("Players:"));

        model = new DefaultListModel<>();
        Participant javier = new Participant("Javier", Participant.Role.MODERATOR);
        Participant matt   = new Participant("Matt",   Participant.Role.PLAYER);
        Participant davis  = new Participant("Davis",  Participant.Role.PLAYER);
        Participant luca   = new Participant("Luca",   Participant.Role.PLAYER);

        model.addElement(javier);
        model.addElement(matt);
        model.addElement(davis);
        model.addElement(luca);
        currentUser = javier;

        participantList = new JList<>(model);
        participantList.setCellRenderer((JList<? extends Participant> list,
                                         Participant v, int idx,
                                         boolean isSelected, boolean cellHasFocus) -> {
            String netIcon  = "●";
            String voteIcon = v.getVote().isEmpty() ? "" : " ✔";
            JLabel lbl = new JLabel(
                String.format("%s %s [%s]%s", netIcon, v.getName(), v.getRole(), voteIcon)
            );
            if (isSelected) {
                lbl.setOpaque(true);
                lbl.setBackground(list.getSelectionBackground());
            }
            return lbl;
        });

        JScrollPane scroll = new JScrollPane(participantList);
        scroll.setPreferredSize(new Dimension(0, 150));
        add(scroll);

        JPopupMenu popup = buildContextMenu();
        participantList.addMouseListener(new MouseAdapter() {
            private void showPopup(MouseEvent ev) {
                if (ev.isPopupTrigger()) {
                    int idx = participantList.locationToIndex(ev.getPoint());
                    participantList.setSelectedIndex(idx);
                    if (currentUser.getRole() == Participant.Role.MODERATOR) {
                        popup.show(participantList, ev.getX(), ev.getY());
                    }
                }
            }
            @Override public void mousePressed(MouseEvent ev)  { showPopup(ev); }
            @Override public void mouseReleased(MouseEvent ev) { showPopup(ev); }
        });

        JButton export = new JButton("Export CSV");
        export.addActionListener(e -> exportCSV());
        add(export);
    }

    private JPopupMenu buildContextMenu() {
        JPopupMenu popup = new JPopupMenu();

        JMenuItem rename = new JMenuItem("Rename participant…");
        rename.addActionListener(e -> {
            Participant p = participantList.getSelectedValue();
            String nm = JOptionPane.showInputDialog(this, "Enter new name:", p.getName());
            if (nm != null && !nm.trim().isEmpty()) {
                p.setName(nm.trim());
                participantList.repaint();
            }
        });
        popup.add(rename);
        popup.addSeparator();

        for (Participant.Role r : Participant.Role.values()) {
            JMenuItem roleItem = new JMenuItem("Set role: " + r);
            roleItem.addActionListener(e -> {
                Participant p = participantList.getSelectedValue();
                p.setRole(r);
                participantList.repaint();
            });
            popup.add(roleItem);
        }
        popup.addSeparator();

        JMenuItem remove = new JMenuItem("Remove participant");
        remove.addActionListener(e -> {
            Participant p = participantList.getSelectedValue();
            model.removeElement(p);
        });
        popup.add(remove);

        return popup;
    }

    public void markVoted(String name, String vote) {
        for (Enumeration<Participant> e = model.elements(); e.hasMoreElements();) {
            Participant p = e.nextElement();
            if (p.getName().equals(name)) {
                p.setVote(vote);
                p.setStatus(Participant.Status.VOTED);
                p.setTimestamp(new SimpleDateFormat("mm:ss").format(new java.util.Date()));
                break;
            }
        }
        participantList.repaint();
        if (allVoted()) showResultsSummary();
    }

    private boolean allVoted() {
        for (int i = 0; i < model.getSize(); i++) {
            if (model.get(i).getStatus() != Participant.Status.VOTED) {
                return false;
            }
        }
        return true;
    }

    private void showResultsSummary() {
        int count = model.getSize();
        double sum = 0;
        Map<String,Integer> breakdown = new TreeMap<>();
        for (Enumeration<Participant> e = model.elements(); e.hasMoreElements();) {
            Participant p = e.nextElement();
            String v = p.getVote();
            try { sum += Double.parseDouble(v); } catch (Exception ignored) {}
            breakdown.put(v, breakdown.getOrDefault(v, 0) + 1);
        }
        double avg = count > 0 ? sum / count : 0;

        StringBuilder sb = new StringBuilder();
        sb.append("Number of votes: ").append(count).append("\n");
        sb.append("Average vote: ").append(String.format("%.2f", avg)).append("\n\n");
        sb.append("Vote breakdown:\n");
        breakdown.forEach((k,v) -> sb.append(k).append(": ").append(v).append("\n"));

        JOptionPane.showMessageDialog(this, sb.toString(),
            "Voting Results", JOptionPane.INFORMATION_MESSAGE);
    }

	private void exportCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save results as CSV");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".csv")) {
                f = new File(f.getParentFile(), f.getName() + ".csv");
            }
            String placeholder = "Put your stories text here. Each line contains new story.";
            try (FileWriter w = new FileWriter(f)) {
                String nl = System.lineSeparator();
                w.write("sep=;" + nl);
                w.write("Story Title;Average;Estimate;Vote Started;Voting Duration;Avg Voting Time;Fastest;Slowest;" + nl);
                List<String> rawStories = Blackboard.getStories();
                for (String raw : rawStories) {
                    String[] lines = raw.split("\\R");
                    for (String storyLine : lines) {
                        String story = storyLine.trim();
                        if (story.isEmpty()) continue;
                        if (story.startsWith(placeholder)) {
                            story = story.substring(placeholder.length()).replaceFirst("^\\W+", "");
                        }
                        double avg = Blackboard.getAverageVote(story);
                        String est = Blackboard.getEstimateValue(story);
                        LocalDateTime st = Blackboard.getVoteStartTime(story);
                        Duration dur = Blackboard.getVotingDuration(story);
                        Duration avt = Blackboard.getAvgVotingTime(story);
                        String fst = Blackboard.getFastestVoter(story);
                        String slw = Blackboard.getSlowestVoter(story);

                        String stStr = (st == null ? "" : st.format(DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a")));
                        String durStr = formatDuration(dur);
                        String avtStr = formatDuration(avt);

                        w.write(String.format("%s;%.2f;%s;%s;%s;%s;%s;%s;%n",
                            story, avg, est, stStr, durStr, avtStr, fst, slw));
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String formatDuration(Duration d) {
        long secs = d.getSeconds(), abs = Math.abs(secs);
        String tmp = String.format("%02d:%02d:%02d", abs/3600, (abs%3600)/60, abs%60);
        return secs < 0 ? "-" + tmp : tmp;
    }

    @SuppressWarnings("unused")
    public static class Participant {
        public enum Role   { MODERATOR, PLAYER, OBSERVER }
        public enum Status { OFFLINE,   ONLINE,   VOTED    }

        private String name;
        private Role   role;
        private Status status = Status.ONLINE;
        private String vote   = "";
        private String timestamp = "00:00:00";

        public Participant(String n, Role r) {
            name = n; role = r;
        }

        public String getName()            { return name;      }
        public void   setName(String n)    { name = n;         }
        public Role   getRole()            { return role;      }
        public void   setRole(Role r)      { role = r;         }
        public Status getStatus()          { return status;    }
        public void   setStatus(Status s)  { status = s;       }
        public String getVote()            { return vote;      }
        public void   setVote(String v)    { vote = v;         }
        public String getTimestamp()       { return timestamp; }
        public void   setTimestamp(String t){ timestamp = t;   }
    }
}
