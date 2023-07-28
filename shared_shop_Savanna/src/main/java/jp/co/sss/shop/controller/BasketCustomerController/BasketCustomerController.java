package jp.co.sss.shop.controller.BasketCustomerController;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.RemoveCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
/**
 * 買い物籠管理 追加,削除機能のコントローラクラス
 *
 * @author 高橋裕多
 */
@Controller
public class BasketCustomerController {
	@Autowired
	ItemRepository Repository;
	@Autowired
	HttpSession session;
	@RequestMapping(path ="/basket/list", method = RequestMethod.GET)
	public String basketList() {

		return "/basket/list";
	}
	@RequestMapping(path ="/basket/add", method = RequestMethod.POST)
	public  String addItem( Integer id ,RedirectAttributes attr ){
		//IDで検索し、情報をitemに格納
		Item item = Repository.getOne(id);
		//BasketListにsession情報格納
		@SuppressWarnings("unchecked")
		List<BasketBean> BasketList = (List<BasketBean>) session.getAttribute("BasketList");
		//買い物かごがあるか確認
		if (BasketList==null) {
			//買い物かご生成
			BasketList =new ArrayList<>();
		}
		//同じ商品を探すためのflag設定
		boolean itemFound = false;
		//同じ商品があるか検索
		for(int i=0;i<BasketList.size();i++) {
			//リストのi番目の中のid格納
			Integer cartId = BasketList.get(i).getId();
			//同じ商品があった場合
			if (id == cartId){
				//在庫よりも注文数を超えていた場合
				if (BasketList.get(i).getStock() <= BasketList.get(i).getOrderNum()) {
					//エラーフラグ設定
					attr.addFlashAttribute("Name", BasketList.get(i).getName());
					attr.addFlashAttribute("Error", 1); 
					return "redirect:/basket/list";
				}else{
					//同じ商品の個数を＋1
					BasketList.get(i).setOrderNum(BasketList.get(i).getOrderNum()+ 1);
				}
				//見つかった場合フラグを変更
				itemFound = true;
				break;
			}
		}
		//同じ商品が見つからなかった場合
		if (!itemFound) {
			
			BasketBean basketBean =new BasketBean();
			basketBean.setId(item.getId());
			//在庫をチェック
			Item itemCheck = Repository.getOne(id);
			//注文数が在庫を上回っていないか
			if(itemCheck.getStock()==0) {
				attr.addFlashAttribute("name",itemCheck.getName()); 
				attr.addFlashAttribute("Error", 2); 
				return "redirect:/basket/list";
			}
			basketBean.setName(item.getName());
			basketBean.setStock(item.getStock());
			basketBean.setOrderNum(1);
			BasketList.add(basketBean);
		}

		session.setAttribute("BasketList", BasketList);
		return "redirect:/basket/list";
	}
	@RequestMapping(path ="/basket/delete", method = RequestMethod.POST)
	public  String deleteItem ( Integer id ){
		@SuppressWarnings("unchecked")
		List<BasketBean> BasketList = (List<BasketBean>) session.getAttribute("BasketList");
		for(int i=0;i<BasketList.size();i++) {
			Integer cartId = BasketList.get(i).getId();
	
			if (id == cartId ) {
				BasketList.get(i).setOrderNum(BasketList.get(i).getOrderNum()- 1);
				if (BasketList.get(i).getOrderNum() == 0 ) {
					BasketList.remove(i);	
				}
			}
		}
		session.setAttribute("BasketList", BasketList);
		return "redirect:/basket/list";
	}
	@RequestMapping(path ="/basket/allDelete", method = RequestMethod.POST)
	public  String deleteAll( HttpSession session ) {
		session.removeAttribute("BasketList");
		return "redirect:/basket/list ";
	}
}
