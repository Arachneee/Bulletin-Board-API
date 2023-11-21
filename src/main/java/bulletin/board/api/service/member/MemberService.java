package bulletin.board.api.service.member;

import bulletin.board.api.controller.member.request.MemberNameRequest;
import bulletin.board.api.controller.member.request.MemberRequest;
import bulletin.board.api.service.member.response.MemberResponse;
import bulletin.board.exceptions.DuplicatedLoginIdException;
import bulletin.board.exceptions.DuplicatedNameException;
import bulletin.board.exceptions.EntityNotFoundException;

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

	@Transactional
	public Long createMember(MemberRequest memberRequest) {
		validateMemberRequest(memberRequest);
		Member savedMember = saveMember(memberRequest);

		return savedMember.getId();
	}

	private void validateMemberRequest(MemberRequest memberRequest) {
		validateLoginId(memberRequest.getLoginId());
		validateName(memberRequest.getName());
	}

	public void validateName(String name) {
		if (memberRepository.existsByName(name)) {
			throw new DuplicatedNameException(ErrorCode.DUPLICATED_NAME);
		}
	}

	public void validateLoginId(String loginId) {
		if (memberRepository.existsByLoginId(loginId)) {
			throw new DuplicatedLoginIdException(ErrorCode.DUPLICATED_LOGIN_ID);
		}
	}

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
