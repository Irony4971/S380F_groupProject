package groupProject.dao;

import groupProject.model.LectureNoteUser;
import groupProject.model.UserRole;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureNoteUserService implements UserDetailsService {
    @Resource
    LectureNoteUserRepository lectureNoteUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        LectureNoteUser lectureNoteUser = lectureNoteUserRepo.findById(username).orElse(null);
        if (lectureNoteUser == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : lectureNoteUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new User(lectureNoteUser.getUsername(), lectureNoteUser.getPassword(), authorities);
    }
}
