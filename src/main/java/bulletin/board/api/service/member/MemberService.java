package bulletin.board.api.service.member;

import bulletin.board.api.controller.member.request.MemberNameRequest;
import bulletin.board.api.controller.member.request.MemberRequest;
import bulletin.board.api.service.member.response.MemberResponse;
import bulletin.board.exceptions.DuplicatedLoginIdException;
import bulletin.board.exceptions.DuplicatedNameException;
import bulletin.board.exceptions.EntityNotFoundException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bulletin.board.domain.member.Member;
import bulletin.board.domain.member.MemberRepository;
import bulletin.board.exceptions.constant.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Long create(final MemberRequest memberRequest) {
		validateRequest(memberRequest);
		final Member savedMember = save(memberRequest);

		return savedMember.getId();
	}

	private void validateRequest(final MemberRequest memberRequest) {
		validateLoginId(memberRequest.getLoginId());
		validateName(memberRequest.getName());
	}

	public void validateLoginId(final String loginId) {
		if (memberRepository.existsByLoginId(loginId)) {
			throw new DuplicatedLoginIdException(ErrorCode.DUPLICATED_LOGIN_ID);
		}
	}

	public void validateName(final String name) {
		if (memberRepository.existsByName(name)) {
			throw new DuplicatedNameException(ErrorCode.DUPLICATED_NAME);
		}
	}

	private Member save(final MemberRequest memberRequest) {
		return memberRepository.save(createMember(memberRequest));
	}

	private Member createMember(final MemberRequest memberRequest) {
		String encodedPassword = passwordEncoder.encode(memberRequest.getPassword());

		return Member.create(memberRequest.getLoginId(), encodedPassword, memberRequest.getName());
	}

	@Transactional(readOnly = true)
	public MemberResponse findById(final Long id) {
		final Member member = memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		return MemberResponse.from(member);
	}

	@PreAuthorize("@memberChecker.isSelf(#id) or hasAuthority('ADMIN')")
	@Transactional
	public void updateName(final Long id, final MemberNameRequest memberNameRequest) {
		final Member findmember = memberRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

		findmember.changeName(memberNameRequest.getName());
	}

	@PreAuthorize("@memberChecker.isSelf(#id) or hasAuthority('ADMIN')")
	@Transactional
	public void delete(final Long id) {
		memberRepository.deleteById(id);
	}

}
