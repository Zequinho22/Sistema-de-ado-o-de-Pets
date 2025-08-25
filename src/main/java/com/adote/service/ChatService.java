package com.adote.service;

import com.adote.domain.*;
import com.adote.repo.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ChatService {
    private final ChatRepository chats; private final MessageRepository messages; private final UserRepository users;
    public ChatService(ChatRepository c, MessageRepository m, UserRepository u){ this.chats=c; this.messages=m; this.users=u; }

    public Chat createChat(List<String> participantIds){
        var chat = new Chat();
        for(String id: participantIds){ users.findById(id).ifPresent(chat.getParticipants()::add); }
        return chats.save(chat);
    }

    public Message sendMessage(String chatId, String authorId, String content){
        var chat = chats.findById(chatId).orElseThrow();
        var author = users.findById(authorId).orElseThrow();
        var msg = new Message();
        msg.setChat(chat); msg.setAuthor(author); msg.setContent(content);
        messages.save(msg);
        chat.getMessages().add(msg);
        return msg;
    }

    public List<Chat> listChats(){ return chats.findAll(); }
    public Optional<Chat> get(String id){ return chats.findById(id); }
}
