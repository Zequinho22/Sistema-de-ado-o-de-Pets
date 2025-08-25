package com.adote.controller;

import com.adote.dto.ChatDTOs.*;
import com.adote.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/chats")
public class ChatsController {
    private final ChatService chats;
    public ChatsController(ChatService chats){ this.chats = chats; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateChatRequest req){
        return ResponseEntity.status(201).body(chats.createChat(req.participantUserIds()));
    }

    @PostMapping("/messages")
    public ResponseEntity<?> send(@RequestBody SendMessageRequest req){
        return ResponseEntity.status(201).body(chats.sendMessage(req.chatId(), "TODO_AUTHOR_ID", req.content()));
    }

    @GetMapping
    public ResponseEntity<?> list(){ return ResponseEntity.ok(chats.listChats()); }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id){ return ResponseEntity.of(chats.get(id)); }
}
