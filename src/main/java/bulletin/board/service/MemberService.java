package bulletin.board.service;

import bulletin.board.dto.MemberResponse;
import bulletin.board.exceptions.AuthorityException;
import bulletin.board.exceptions.DuplicatedLoginIdException;
import bulletin.board.exceptions.DuplicatedNameException;
import bulletin.board.exceptions.EmptyStringException;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.PasswordMismatchException;
import bulletin.board.dto.MemberRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import bulletin.board.domain.Member;
import bulletin.board.repository.MemberRepository;
import bulletin.board.web.error.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public Long save(MemberRequest memberRequest) {
		validateMemberRequest(memberRequest);
		Member savedMember = memberRepository.save(Member.create(memberRequest.getLoginId(), memberRequest.getPassword(), memberRequest.getName()));
		return savedMember.getId();
	}

	public MemberResponse findMember(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		return MemberResponse.of(member);
	}

	@Transactional
	public void updateName(Member member, Long id, String newName) {
		validateAuthority(member, id);
		validateEmptyString(newName);

		Member findmember = memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		findmember.changeName(newName);
	}

	@Transactional
	public void delete(Member member, Long id) {
		validateAuthority(member, id);
		memberRepository.deleteById(id);
	}

	private static void validateEmptyString(String newName) {
		if (!StringUtils.hasText(newName)) {
			throw new EmptyStringException(ErrorCode.INVALID_INPUT);
		}
	}

	private static void validateAuthority(Member member, Long id) {
		if (!member.getId().equals(id)) {
			throw new AuthorityException(ErrorCode.INVALID_AUTHORITY);
		}
	}

	private void validateMemberRequest(MemberRequest memberRequest) {
		if (!memberRequest.getPassword().equals(memberRequest.getPasswordRe())) {
			throw new PasswordMismatchException(ErrorCode.PASSWORD_NOT_SAME);
		}

		memberRepository.findByLoginId(memberRequest.getLoginId())
			.orElseThrow(() -> new DuplicatedLoginIdException(ErrorCode.DUPLICATED_LOGIN_ID));

		memberRepository.findByName(memberRequest.getName())
			.orElseThrow(() -> new DuplicatedNameException(ErrorCode.DUPLICATED_NAME));
	}

}
