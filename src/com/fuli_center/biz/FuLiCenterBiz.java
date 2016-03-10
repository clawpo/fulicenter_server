package com.fuli_center.biz;

import java.io.File;
import java.util.ArrayList;

import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.ContactBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.bean.UserBean;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.dao.FuLiCenterDao;
import com.fuli_center.dao.IFuLiCenterDao;

public class FuLiCenterBiz implements IFuLiCenterBiz{

	IFuLiCenterDao dao;//������ݷ��ʲ�����ñ���
	private ContactBean[] findContactsByUserName;
	
	/**
	 * ҵ���߼���Ĺ�����
	 */
	public FuLiCenterBiz() {
		dao=new FuLiCenterDao();
	}
	
	@Override
	public boolean register(UserBean user) throws Exception {
		if(dao.findUserByUserName(user.getUserName())!=null){
			throw new Exception("���û��Ѵ���");
		}
		boolean isSuccess = dao.addUser(user);
		return isSuccess;
	}
	
	@Override
	public boolean unRegister(String userName){
		//获取删除用户的头像文件的路径
		String path=AVATAR_PATH+"user_avatar/"+userName+".jpg";
		boolean isDelete = dao.deleteUser(userName);
		if(isDelete){//若数据表中的记录删除成功
			File file=new File(path);
			if(file.exists()){//删除头像文件
				file.delete();
			}
		}
		return isDelete;
	}

	@Override
	public UserBean login(String userName, String password) throws Exception {
		UserBean user = dao.findUserByUserName(userName);
		if(user==null){
			throw new Exception("����ע��");
		}
		if(!user.getPassword().equals(password)){
			throw new Exception("�������");
		}
		return user;
	}

	@Override
	public UserBean findUserByUserName(String userName) {
		return dao.findUserByUserName(userName);
	}

	@Override
	public UserBean[] findUsersByUserName(String userName, int pageId,
			int pageSize) {
		UserBean[] users = findUsersByUserName(userName, pageId, pageSize);
		return users;
	}

	@Override
	public UserBean[] findUsersByNick(String nick, int pageId, int pageSize) {
		UserBean[] users = dao.findUsersByNick(nick, pageId, pageSize);
		return users;
	}

	@Override
	public boolean updateUser(UserBean user) {
		boolean isSuccess = dao.updateUser(user);
		return isSuccess;
	}

	@Override
	public ContactBean findContactById(int myuid, int cuid) {
		ContactBean contact = dao.findContactById(myuid, cuid);
		return contact;
	}

	@Override
	public ContactBean[] findContactsByUserName(String userName, int pageId,
			int pageSize) {
		ContactBean[] contacts = dao.findContactsByUserName(userName, pageId, pageSize);
		return contacts;
	}

	@Override
	public UserBean[] findContactListByMyuid(int myuid, int pageId, int pageSize) {
		UserBean[] users = dao.findContactsByMyuid(myuid, pageId, pageSize);
		return users;
	}

	@Override
	public ContactBean addContact(String userName, String name) {
		ContactBean contact = dao.addContact(userName, name);
		if(dao.findContactById(contact.getCuid(), contact.getMyuid())==null){
			dao.addContact(contact.getCuid(),contact.getMyuid());
		}
		return contact;
	}

	@Override
	public boolean deleteContact(int myuid, int cuid) {
		boolean isSuccess = dao.deleteContact(myuid, cuid);
		return isSuccess;
	}

	@Override
	public int addCart(CartBean cart) {
		return dao.addCart(cart);
	}

	@Override
	public boolean deleteCart(int cartId) {
		return dao.deleteCart(cartId);
	}

	@Override
	public boolean updateCart(int cartId,int count) {
		return dao.updateCart(cartId,count);
	}
	
	@Override
	public boolean updateCart(int cartId,int count,boolean isChecked) {
		return dao.updateCart(cartId,count,isChecked);
	}
	
	@Override
	public CartBean[] findCarts(String userName,int pageId, int pageSize) {
		return dao.findCarts(userName,pageId, pageSize);
	}
	
	@Override
	public NewGoodBean[] findNewAndBoutiqueGoods(int catId,int pageId, int pageSize) {
		return dao.findNewAndBoutiqueGoods(catId,pageId, pageSize);
	}

	@Override
	public CategoryParentBean[] findCategoryParent() {
		return dao.findCategoryParent();
	}

	@Override
	public CategoryChildBean[] findCategoryChildren(int parentId, int pageId,
			int pageSize) {
		return dao.findCategoryChildren(parentId, pageId, pageSize);
	}

	@Override
	public GoodDetailsBean findGoodDetails(int goodsId) {
		return dao.findGoodDetails(goodsId);
	}

	@Override
	public ColorBean[] findColorsByCatId(int catId) {
		return dao.findColorsByCatId(catId);
	}

	@Override
	public int addCollect(CollectBean collect) {
		
		return dao.addCollect(collect);
	}

	@Override
	public boolean  deleteCollect(String userName,int id) {
		return dao.deleteCollect(userName,id);
	}
	
	@Override
	public CollectBean[] findCollects(String userName, int pageId, int pageSize) {
		return dao.findCollects(userName, pageId, pageSize);
	}
	
	@Override
	public boolean isCollect(String userName, int goodsId) {
		return dao.isCollect(userName, goodsId);
	}

	@Override
	public BoutiqueBean[] findBoutiques() {
		return dao.findBoutiques();
	}
	
	@Override
	public ArrayList<GoodDetailsBean> findGoodsDetails(int catId, int pageId, int pageSize) {
		return dao.findGoodsDetails(catId, pageId, pageSize);
	}
	
	@Override
	public boolean upldateNick(String nick,String userName) {
		return dao.updateNick(nick, userName);
	}
}
