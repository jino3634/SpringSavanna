package jp.co.sss.shop.controller.user;

import java.sql.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.ItemForm;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;

/**
 * 会員管理 変更機能(運用管理者、システム管理者)のコントローラクラス
 *
 * @author 澤村のぞみ
 */
@Controller
public class UserUpdateCustomerController {

	/**
	 * 会員情報
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * セッション
	 */
	@Autowired
	HttpSession session;

	/**
	 * 会員情報の変更入力画面表示処理
	 *
	 * @param model Viewとの値受渡し
	 * @param form  会員情報フォーム
	 * @return "user/update/user_update_input_admin" 会員情報 変更入力画面へ
	 **/
	@RequestMapping(path = "/user/update/input", method = RequestMethod.POST)
	public String updateInput(boolean backFlg, Model model, @ModelAttribute UserForm form) {

		// 戻るボタンかどうかを判定
		if (!backFlg) {

			// 変更対象の会員情報を取得
			User user = userRepository.getOne(form.getId());
			UserBean userBean = new UserBean();

			// Userエンティティの各フィールドの値をUserBeanにコピー
			BeanUtils.copyProperties(user, userBean);

			// 会員情報をViewに渡す
			model.addAttribute("user", userBean);

		} else {

			UserBean userBean = new UserBean();
			// 入力値を会員情報にコピー
			BeanUtils.copyProperties(form, userBean);

			// 会員情報をViewに渡す
			model.addAttribute("user", userBean);

		}
		return "user/update/user_update_input";
	}

	/**
	 * 会員新規登録の入力エラー時
	 * 
	 * @return "user/update/user_update_input_admin" 会員情報 変更入力画面へ
	 */
	@RequestMapping(path = "/user/update/input", method = RequestMethod.GET)
	public String updateInputError() {
		return "user/update/user_update_input";
	}

	/**
	 * 会員情報 変更確認処理
	 *
	 * @param model  Viewとの値受渡し
	 * @param form   会員情報フォーム
	 * @param result 入力チェック結果
	 * @return 入力値エラーあり："redirect:/user/update/input/admin" 会員情報登録画面へ
	 *         入力値エラーなし："user/update/user_update_check_admin" 会員情報 変更確認画面へ
	 */
	@RequestMapping(path = "/user/update/check", method = RequestMethod.POST)
	public String updateCheck(Model model, @Valid @ModelAttribute UserForm form, BindingResult result,
			RedirectAttributes redirectAttributes) {
		// 入力値にエラーがあった場合、会員情報 変更入力画面表示処理に戻る
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userForm", result);

			redirectAttributes.addFlashAttribute("user", form);

			return "redirect:/user/update/input";
		}

		return "user/update/user_update_check";
	}

	/**
	 * 会員情報変更完了処理
	 *
	 * @param model              Viewとの値受渡し
	 * @param form               会員情報
	 * @param redirectAttributes リダイレクト後情報保持
	 * @return "user/update/user_update_complete_admin" 会員情報 変更完了画面へ
	 */
	@RequestMapping(path = "/user/update/complete", method = RequestMethod.POST)
	public String updateComplete(Model model, @ModelAttribute UserForm form, RedirectAttributes redirectAttributes) {

		// 変更対象の会員情報を取得
		User user = userRepository.findById(form.getId()).orElse(null);

		// 会員情報の削除フラグを取得
		Integer deleteFlag = user.getDeleteFlag();
		// 会員情報の登録日付を取得
		Date insertDate = user.getInsertDate();

		// 入力値をUserエンティティの各フィールドにコピー
		BeanUtils.copyProperties(form, user);

		// 削除フラグをセット
		user.setDeleteFlag(deleteFlag);
		// 登録日付をセット
		user.setInsertDate(insertDate);

		// 会員情報を保存
		userRepository.save(user);

		// セッションからログインユーザーの情報を取得
		UserBean userBean = (UserBean) session.getAttribute("user");
		// 変更対象の会員が、ログインユーザと一致していた場合セッション情報を変更
		if (user.getId().equals(userBean.getId())) {
			// Userエンティティの各フィールドの値をUserBeanにコピー
			BeanUtils.copyProperties(form, userBean);
			// 会員情報をViewに渡す
			session.setAttribute("user", userBean);
		}

		// 会員IDをViewに渡す
		redirectAttributes.addFlashAttribute("userId", form.getId());

		return "redirect:/user/update/complete";
	}

	/**
	 * 会員情報変更完了画面表示
	 *
	 * @return "user/update/user_update_complete_admin" 会員情報 変更完了画面へ
	 */
	@RequestMapping(path = "/user/update/complete", method = RequestMethod.GET)
	public String updateCompleteRedirect() {

		return "user/update/user_update_complete";
	}

//
//	/**
//	 * 会員情報
//	 */
//	@Autowired
//	UserRepository userRepository;
//
//	/**
//	 * セッション
//	 */
//	@Autowired
//	HttpSession session;
//
//	@RequestMapping(path = "/user/update/input", method = RequestMethod.POST)
//	public String updateInput(boolean backFlg, Model model, @ModelAttribute UserForm form) {
//
////	@RequestMapping("/user/update/input/{id}")
////	public String updateInput(@PathVariable int id, Model model) {
////		model.addAttribute("item",userRepository.getOne(id));
////		return "user/user_update_input";
//	}
//	
//	@RequestMapping(path = "/user/update/complete/{id}", method = RequestMethod.POST)
//	public String updateComplete(@PathVariable int id, ItemForm form) {
//		User item =  userRepository.getOne(id);
//		item.setEmail(form.getEmail());
//		item.setName(form.getName());
//		item.setPostalCode(form.getPostalCode());
//		item.setAddress(form.getAddress);
//		item.setPhoneNumber(form.getPhoneNumber());
//		userRepository.save(item);
//		return "redirect:/items/getOne/" + item.getId();
//	}
}