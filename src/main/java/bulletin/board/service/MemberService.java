package bulletin.board.service;

import bulletin.board.dto.MemberResponse;
import bulletin.board.exceptions.DuplicatedLoginIdException;
import bulletin.board.exceptions.DuplicatedNameException;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.PasswordMismatchException;
import bulletin.board.dto.MemberRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.Member;
import bulletin.board.repository.MemberRepository;
import bulletin.board.web.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;

	public Long save(MemberRequest memberRequest) {
		validateMemberRequest(memberRequest);
		Member savedMember = memberRepository.save(Member.create(memberRequest.getLoginId(), memberRequest.getPassword(), memberRequest.getName()));
		return savedMember.getId();
	}

	private void validateMemberRequest(MemberRequest memberRequest) {
		if (!memberRequest.getPassword().equals(memberRequest.getPasswordRe())) {
			throw new PasswordMismatchException(ErrorCode.PASSWORD_NOT_SAME);
		}
		if (memberRepository.findByLoginId(memberRequest.getLoginId()).isPresent()) {
			throw new DuplicatedLoginIdException(ErrorCode.DUPLICATED_LOGIN_ID);
		}
		if (memberRepository.findByName(memberRequest.getName()).isPresent()) {
			throw new DuplicatedNameException(ErrorCode.DUPLICATED_NAME);
		}
	}

	public MemberResponse findMember(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		return MemberResponse.of(member);
	}
}
