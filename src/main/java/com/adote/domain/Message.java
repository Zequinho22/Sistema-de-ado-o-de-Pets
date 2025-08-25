package com.adote.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "messages")
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne @JoinColumn(name="chat_id")
    private Chat chat;

    @ManyToOne @JoinColumn(name="author_id")
    private User author;

    @Column(nullable = false) private String content;
    private Instant sentAt = Instant.now();

    public String getId() { return id; }
    public Chat getChat() { return chat; }
    public void setChat(Chat chat) { this.chat = chat; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant getSentAt() { return sentAt; }
}
