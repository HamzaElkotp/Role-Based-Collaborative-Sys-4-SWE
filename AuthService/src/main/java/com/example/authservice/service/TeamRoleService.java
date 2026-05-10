package com.example.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.example.authservice.dto.MembershipDTO;
import com.example.authservice.entity.Memberships;
import com.example.authservice.repository.MembershipsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamRoleService {

    @Autowired
    private MembershipsRepository membershipRepository;

    public List<String> getPermissionsByRole(Long roleId) {
        return new ArrayList<>(List.of("READ_PRIVILEGE", "WRITE_PRIVILEGE"));
    }

    public String getUserRoleInProject(Long projectId, Long userId) {
        Memberships m = membershipRepository.findByProjectIdAndUserId(projectId, userId);
        if (m != null) {
            return m.getRole();
        }
        return null;
    }

//    @CacheEvict(value = "memberships", allEntries = true)
//    public void removeMember(Long projectId, Long userId) {
//        Memberships m = membershipRepository.findByProjectIdAndUserId(projectId, userId);
//        if (m != null) {
//            membershipRepository.delete(m);
//        }
//    }

//    @CacheEvict(value = "memberships", allEntries = true)
//    public MembershipDTO updateMemberRole(Long projectId, Long userId, Long newRoleId) {
//        Memberships m = membershipRepository.findByProjectIdAndUserId(projectId, userId);
//        if (m != null) {
//            m.setRole_id(newRoleId);
//            return convertToDTO(membershipRepository.save(m));
//        }
//        return null;
//    }

//    @Cacheable(value = "memberships", key = "#projectId")
    public List<MembershipDTO> getMembersByProject(Long projectId) {
        return membershipRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//    @CacheEvict(value = "memberships", allEntries = true)
    public MembershipDTO addMember(Long projectId, MembershipDTO dto) {
        Memberships membership = new Memberships();
        membership.setProjectId(projectId);
        membership.setUserId(dto.getUser_id());
        membership.setRole(dto.getRole());
        return convertToDTO(membershipRepository.save(membership));
    }

    private MembershipDTO convertToDTO(Memberships m) {
        MembershipDTO dto = new MembershipDTO();
        dto.setId(m.getId());
        dto.setProject_id(m.getProjectId());
        dto.setUser_id(m.getUserId());
        dto.setRole(m.getRole());
        dto.setJoined_at(m.getJoined_at());
        return dto;
    }
}