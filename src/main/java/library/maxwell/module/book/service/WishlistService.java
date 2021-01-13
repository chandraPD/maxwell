package library.maxwell.module.book.service;

import java.util.List;

import library.maxwell.config.security.auth.UserPrincipal;
import library.maxwell.module.book.entity.WishlistEntity;

public interface WishlistService {
	WishlistEntity post(UserPrincipal userPrincipal, Integer id);

	WishlistEntity update(UserPrincipal userPrincipal, Integer id);

	WishlistEntity update2(UserPrincipal userPrincipal, Integer id);

	Boolean get(UserPrincipal userPrincipal, Integer id);

	List<WishlistEntity> getAll(UserPrincipal userPrincipal);
}
