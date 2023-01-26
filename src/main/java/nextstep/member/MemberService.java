package nextstep.member;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static nextstep.RoomEscapeApplication.getPasswordEncoder;
import static nextstep.config.Messages.*;

@Service
public class MemberService {
    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
        this.passwordEncoder = getPasswordEncoder();
    }

    public Long create(MemberRequest memberRequest) {
        if (!memberDao.findByUsername(memberRequest.getUsername()).isEmpty()){
            throw new DuplicateKeyException(ALREADY_USER.getMessage());
        }
        Member member = new Member(memberRequest.getUsername(), passwordEncoder.encode(memberRequest.getPassword()),
                memberRequest.getName(), memberRequest.getPhone());
        return memberDao.save(member);
    }

    public List<Member> findByUsername(String username) {
        return memberDao.findByUsername(username);
    }

}
