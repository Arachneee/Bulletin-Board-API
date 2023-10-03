package bulletin.board.service;

import bulletin.board.dto.MemberNameRequest;
import bulletin.board.dto.MemberRequest;
import bulletin.board.dto.MemberResponse;
import bulletin.board.exceptions.DuplicatedLoginIdException;
import bulletin.board.exceptions.DuplicatedNameException;
import bulletin.board.exceptions.EntityNotFoundException;
import bulletin.board.exceptions.PasswordMismatchException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.Member;
import bulletin.board.repository.MemberRepository;
import bulletin.board.constant.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public Long createMember(MemberRequest memberRequest) {
		validateMemberRequest(memberRequest);
		Member savedMember = saveMember(memberRequest);

		return savedMember.getId();
	}

	private void validateMemberRequest(MemberRequest memberRequest) {
		validatePassword(memberRequest);
		validateLoginId(memberRequest);
		validateName(memberRequest);
	}

	private void validateName(MemberRequest memberRequest) {
		if (memberRepository.existsByName(memberRequest.getName())) {
			throw new DuplicatedNameException(ErrorCode.DUPLICATED_NAME);
		}
	}

	private void validateLoginId(MemberRequest memberRequest) {
		if (memberRepository.existsByLoginId(memberRequest.getLoginId())) {
			throw new DuplicatedLoginIdException(ErrorCode.DUPLICATED_LOGIN_ID);
		}
	}

	private void validatePassword(MemberRequest memberRequest) {
		if (!memberRequest.getPassword().equals(memberRequest.getPasswordRe())) {
			throw new PasswordMismatchException(ErrorCode.PASSWORD_NOT_SAME);
		}
	}

	@Transactional
	private Member saveMember(MemberRequest memberRequest) {
		return memberRepository.save(Member.create(memberRequest.getLoginId(), memberRequest.getPassword(), memberRequest.getName()));
	}

	@Transactional(readOnly = true)
	public MemberResponse findMember(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		return MemberResponse.of(member);
	}

	@Transactional
	public void updateName(Long id, MemberNameRequest memberNameRequest) {
		Member findmember = memberRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		findmember.changeName(memberNameRequest.getName());
	}

	@Transactional
	public void deleteMember(Long id) {
		memberRepository.deleteById(id);
	}

}
