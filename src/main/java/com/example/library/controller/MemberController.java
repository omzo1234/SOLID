package com.example.library.controller;

import com.example.library.model.Member;
import com.example.library.service.LibraryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final LibraryService libraryService;
    
    
    public MemberController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }
    
    @GetMapping
    public List<Member> getAllMembers() {
        return libraryService.getAllMembers();
    }
    
    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return libraryService.getMemberById(id);
    }
    
    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return libraryService.createMember(member);
    }
    
    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody Member member) {
        return libraryService.updateMember(id, member);
    }
    
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        libraryService.deleteMember(id);
    }
}