package com.adote.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToMany
    @JoinTable(name = "chat_users",
        joinColumns = @JoinColumn(name="chat_id"),
        inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> participants = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    private Instant createdAt = Instant.now();

    public String getId() { return id; }
    public Set<User> getParticipants() { return participants; }
    public List<Message> getMessages() { return messages; }
}
