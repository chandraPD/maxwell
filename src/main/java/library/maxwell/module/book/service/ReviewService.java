package library.maxwell.module.book.service;

import java.util.List;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.dto.ReviewDto;
import library.maxwell.module.book.entity.ReviewEntity;

public interface ReviewService {
	List<ReviewEntity> getAll(Integer id);
	ReviewEntity post(UserPrincipal userPrincipal,Integer id,ReviewDto Dto);
	ReviewEntity update(UserPrincipal userPrincipal,Integer id,ReviewDto Dto);
	ReviewEntity Update2(UserPrincipal userPrincipal,Integer id);
	Boolean get(UserPrincipal userPrincipal,Integer id);
	Double findRate(Integer id);
}
