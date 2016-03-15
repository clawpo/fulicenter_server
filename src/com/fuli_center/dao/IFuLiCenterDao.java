package com.fuli_center.dao;

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

public interface IFuLiCenterDao {

	/**
	 * 向购物车添加数据
	 * @param cart
	 * @return
	 */
	public int addCart(CartBean cart);
	
	/**
	 * 删除购物车中指定的数据
	 * @param cartId:
	 * @return
	 */
	public boolean deleteCart(int cartId);
	
	/**
	 * 更新购物车
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count);
	/**
	 * 更新购物车
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count,boolean isChecked);
	
	/**
	 *从购物车下载数据
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	public CartBean[] findCarts(String userName,int pageId,int pageSize);
	/**
	 * ����˺�userName����ݱ�user�в��Ҽ�¼
	 * @param userName����ѯ���������˺�
	 * @return������UserBeanʵ��
	 */
	UserBean findUserByUserName(String userName);
	
	/**
	 * ����˺���user����ģ���ѯ�����ض�����������user��¼
	 * @param userName����ѯ�������û����˺�
	 * @param pageId��ҳ�ţ���0��ʼ����
	 * @param pageSize��ÿҳ���صļ�¼��
	 * @return
	 */
	UserBean[] findUsersByUserName(String userName,int pageId,int pageSize);
	
	/**
	 * ����ǳƣ���user����ģ���ѯ�����ض�����������user��¼
	 * @param nick���ǳƣ���ѯ����
	 * @param pageId��ҳ�ţ���0��ʼ����
	 * @param pageSize��ÿҳ���صļ�¼��
	 * @return
	 */
	UserBean[] findUsersByNick(String nick,int pageId,int pageSize);
	
	/**
	 * ��user�����һ����¼��ע��ʱʹ��
	 * @param user����������
	 * @return������ɹ�����true
	 */
	boolean addUser(UserBean user);
	
	/**
	 * 删除用户，取消注册使用
	 * @param userName
	 * @return
	 */
	boolean deleteUser(String userName);
	
	/**
	 * �޸�user���еļ�¼���ֻ��У��޸��û�ͷ���ǳ�ʱʹ�á�
	 * @param user
	 * @return
	 */
	boolean updateUser(UserBean user);
	
	/**
	 * 更新昵称
	 * @param nick
	 * @return
	 */
	boolean updateNick(String nick,String userName);
	/** �����ϵ��ʱ��������֤
	 * �ж�userName��name�Ƿ�Ϊ��ϵ��
	 * @param userName
	 * @param name
	 * @return
	 */
	boolean isExistsContact(String userName,String name);
	
	/**
	 * ����myuid��cuid������ϵ�˵���Ϣ
	 * @param myuid����ǰ�û�id
	 * @param cuid����ϵ���û�id
	 * @return
	 */
	ContactBean findContactById(int myuid,int cuid);
	
	/**
	 * ��ѯ�˺���userName��������ϵ����Ϣ
	 * @param userName����ѯ���������˺�
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ���صļ�¼��
	 * @return
	 */
	ContactBean[] findContactsByUserName(String userName,int pageId,int pageSize);
	
	/**
	 * ��ѯmyuid��������ϵ����Ϣ
	 * @param myuid����ѯ��������contact���е�myuid�ֶ�
	 * @param pageId��ҳ��
	 * @param pageSize��ÿҳ�ķ��صļ�¼��
	 * @return
	 */
	UserBean[] findContactsByMyuid(int myuid,int pageId,int pageSize);
	
	/**
	 * �����ϵ�˼�¼
	 * @param userName����ǰ�û���id
	 * @param name����ϵ���û���id
	 * @return
	 */
	ContactBean addContact(String userName,String name);
	
	ContactBean addContact(int myuid,int cuid);
	
	/**
	 * ɾ����ϵ�ˣ��������֮��ĺ��ѹ�ϵ
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);
	/**
	 * 从tb_boutique表中查询所有精选的列表数据
	 * @return
	 */
	BoutiqueBean[] findBoutiques();
	
	/**
	 * 从tb_boutique_good表中查询精选的商品信息
	 * @param catId:商品小类别id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	NewGoodBean[] findNewAndBoutiqueGoods(int catId,int pageId,int pageSize);
	
	/** 查询分类中的大类型信息*/
	CategoryParentBean[] findCategoryParent();
	
	/**
	 * 查询小类别商品列表信息
	 * @param catId:小类别id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	CategoryChildBean[] findCategoryChildren(int catId,int pageId,int pageSize);
	
	/**
	 * 下载分类列表中指定id的商品详情信息
	 * @param goodsId：商品id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	GoodDetailsBean findGoodDetails(int goodsId);
	
	/**
	 * 下载catId指定的一组商品详情数据
	 * @param catId:分类的id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	ArrayList<GoodDetailsBean> findGoodsDetails(int catId,int pageId,int pageSize);
	
	/**
	 * 下载指定类别的颜色数据
	 * @param catId：小类别id
	 * @return
	 */
	ColorBean[] findColorsByCatId(int catId);
	
	/**
	 * 添加收藏
	 * @param w
	 */
	int addCollect(CollectBean collect);
	
	/**
	 * 删除收藏
	 * @param userName
	 * @param goodsId
	 * @return
	 */
	boolean deleteCollect(String userName,int id);
	
	/**
	 * 下载指定用户的收藏
	 * @param userName：用户账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	CollectBean[] findCollects(String userName,int pageId,int pageSize);

	/**
	 * 下载指定用户的收藏数量
	 * @param userName：用户账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	int findCollectCount(String userName);

	/**
	 * 获取指定用户是否收藏了指定商品
	 * @param userName
	 * @param goodsId
	 * @return
	 */
	boolean isCollect(String userName,int goodsId);
}
