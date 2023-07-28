package jp.co.sss.shop.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.repository.UserRepository;

/**
 * 会員管理 表示機能(一般会員)のコントローラクラス
 *
 * @author 山本
 */
@Controller
public class UserShowCustomerController {
	/**
	 * 会員情報
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * 会員詳細表示処理
	 *
	 * @param model   Viewとの値受渡し
	 * @param form    会員情報フォーム
	 * @param session セッション情報
	 * @return "/user/detail/user_detail" 会員詳細表示画面へ
	 */
	@RequestMapping(path = "/user/detail")
	public String showUser(Model model, HttpSession session) {
		// 表示対象の会員情報を取得
		Integer userId= ((UserBean)session.getAttribute("user")).getId();
		User user = userRepository.findById(userId).orElse(null);

		UserBean userBean = new UserBean();

		// Userエンティティの各フィールドの値をUserBeanにコピー
		BeanUtils.copyProperties(user, userBean);

		// 会員情報をViewに渡す
		model.addAttribute("user", userBean);

		return "/user/detail/user_detail";
	}
}
	


