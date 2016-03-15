package com.fuli_center.biz;

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

public interface IFuLiCenterBiz {

	class Cart{
		public static final String TABLE_NAME="tb_cart";
		public static final String ID="id";
		public static final String GOODS_ID="goods_id";
		public static final String GOODS_THUMB="goodsThumb";
		public static final String USER_NAME="userName";
		/**购物车中该商品的件数*/
		public static final String COUNT="count";
		/**商品是否已被选中*/
		public static final String IS_CHECKED="isChecked";
	}
	
	class Good{
		public static final String TABLE_NAME="good";
		public static final String CURRENCY_PRICE="currencyPrice";
		public static final String ENGLISH_NAME="englishName";
		public static final String THUMB_URL="thumbUrl";
		public static final String GOODS_ID="goods_id";
	}
	class Boutique{
		public static final String TABLE_NAME="tb_boutique";
		public static final String ID="id";
		public static final String CAT_ID="catId";
		public static final String TITLE="title";
		public static final String DESCRIPTION="description";
		public static final String NAME="name";
		public static final String IMAGE_URL="imageurl";
	}
	
	class NewAndBoutiqueGood extends GoodDetails{
		public static final String TABLE_NAME="tb_new_boutique_good";
		public static final String CAT_ID="cat_id";
		/** 颜色id*/
		public static final String COLOR_ID="color_id";
		/** 颜色名*/
		public static final String COLOR_NAME="color_name";
		/** 颜色代码*/
		public static final String COLOR_CODE="color_code";
		/** 导购链接*/
		public static final String COLOR_URL="color_url";
	}
	
	class CategoryParent{
		public static final String TABLE_NAME="tb_category_parent";
		public static final String ID="id";
		public static final String NAME="name";
		public static final String IMAGE_URL="imageurl";
	}
	
	class CategoryChild extends CategoryParent{
		public static final String TABLE_NAME="tb_category_child";
		public static final String PARENT_ID="parent_id";
		public static final String CAT_ID="catId";
	}
	
	class GoodDetails{
		public static final String TABLE_NAME="tb_good_details";
		public static final String ID="id";
		/** 商品id*/
		public static final String GOODS_ID="goods_id";
		/** 所属类别的id*/
		public static final String CAT_ID="cat_id";
		/** 商品的中文名称*/
		public static final String GOODS_NAME="goods_name";
		/** 商品的英文名称*/
		public static final String GOODS_ENGLISH_NAME="goods_english_name";
		/** 商品简介*/
		public static final String GOODS_BRIEF="goods_brief";
		/** 商品原始价格*/
		public static final String SHOP_PRICE="shop_price";
		/** 商品的RMB价格 */
		public static final String CURRENT_PRICE="currency_price";
		/** 商品折扣价格 */
		public static final String PROMOTE_PRICE="promote_price";
		/** 人民币折扣价格*/
		public static final String RANK_PRICE="rank_price";
		/**是否折扣*/
		public static final String IS_PROMOTE="is_promote";
		/** 商品缩略图地址*/
		public static final String GOODS_THUMB="goods_thumb";
		/** 商品图片地址*/
		public static final String GOODS_IMG="goods_img";
		/** 分享地址*/
		public static final String ADD_TIME="add_time";
		/** 分享地址*/
		public static final String SHARE_URL="share_url";
	}
	
	class Property{
		public static final String TABLE_NAME="tb_property";
		public static final String ID="id";
		public static final String GOODS_ID="goods_id";
		public static final String COLOR_ID="colorid";
		public static final String COLOR_NAME="colorname";
		public static final String COLOR_CODE="colorcode";
		public static final String COLOR_IMG="colorimg";
		public static final String COLOR_URL="colorurl";
	}
	
	class Album{
		public static final String TABLE_NAME="tb_album";
		public static final String ID="id";
		public static final String PID="pid";
		public static final String IMG_ID="img_id";
		public static final String IMG_URL="img_url";
		public static final String THUMB_URL="thumb_url";
		public static final String IMG_DESC="img_desc";
	}

	class Color{
		public static final String TABLE_NAME="tb_color";
		public static final String COLOR_ID="colorid";
		public static final String CAT_ID="cat_id";
		public static final String COLOR_NAME="colorname";
		public static final String COLOR_CODE="colorcode";
		public static final String COLOR_IMG="colorimg";
	}
	
	class Collect{
		public static final String TABLE_NAME="tb_collect";
		/** 商品id*/
		public static final String ID="id";
		
		public static final String GOODS_ID="goods_id";
		
		public static final String USER_NAME="userName";
		
		/** 商品的中文名称*/
		public static final String GOODS_NAME="goodsName";
		/** 商品的英文名称*/
		public static final String GOODS_ENGLISH_NAME="goodsEnglishName";
		public static final String GOODS_THUMB="goodsThumb";
		public static final String GOODS_IMG="goodsImg";
		public static final String ADD_TIME="addTime";
		
		public static final String COUNT = "count";
	}
	
	public static final String AVATAR_PATH = "F:/0-Android/project/FuLiCenter/";
	
	/**
	 * 页号
	 */
	public static final String PAGE_ID="page_id";
	
	/**
	 * 每页加载的数据
	 */
	public static final String PAGE_SIZE="page_size";
	
	/**
	 * User
	 * @author yao
	 */
	public static class User{
		public static final String TABLE_NAME="tb_user";
		public static final String ID="id";
		public static final String USER_NAME="userName";
		public static final String NICK="nick";
		public static final String AVATAR="avatar";
		public static final String PASSWORD="password";
		public static final String HEADER="heard";
		public static final String UN_READ_MSG_COUNT="unreadMsgCount";
	}
	
	public static class Contact extends User{
		public static final String TABLE_NAME="tb_contact";
		public static final String NAME="name";
		public static final String MYUID="myuid";
		public static final String CUID="cuid";
		public static final String IS_GET_MY_LOCATION="isGetMyLocation";
		public static final String IS_SHOW_MY_LOCATION="isShowMyLocation";
		
	}
	
	
	/**
	 * 添加商品至购物车
	 * @param cart
	 * @return
	 */
	public int addCart(CartBean cart);
	
	/**
	 * 删除购物车中指定id的商品
	 * @param cartId:所选商品的id
	 * @return
	 */
	public boolean deleteCart(int cartId);
	
	/**
	 * 修改购物车中的商品信息
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count);

	/**
	 * 修改购物车中的商品信息
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count,boolean isChecked);
	
	/**
	 * 查找购物车中的一组数据
	 * @param userName:用户账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	public CartBean[] findCarts(String userName,int pageId,int pageSize);
	
	/**
	 * 注册
	 * @param user用户信息 
	 * @return
	 */
	boolean register(UserBean user) throws Exception;
	
	/**
	 * 取消注册
	 * @param userName:账号
	 * @return
	 * @throws Exception
	 */
	boolean unRegister(String userName);
	
	UserBean login(String userName,String password) throws Exception;
	
	/**
	 * 查找登陆的用户
	 * @param userName
	 * @return
	 */
	UserBean findUserByUserName(String userName);
	
	/**
	 * 查找账号中包含userName的一组用户
	 * @param userName�用户账号
	 * @return
	 */
	UserBean[] findUsersByUserName(String userName,int pageId,int pageSize);
	
	/**
	 * 查找昵称中包含nick的一组用户
	 * @param nick：用户昵称
	 * @param pageId
	 * @param pageSie
	 * @return
	 */
	UserBean[] findUsersByNick(String nick,int pageId,int pageSize);
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	boolean updateUser(UserBean user);
	
	/**
	 * 更新昵称
	 * @param nick
	 * @return
	 */
	boolean upldateNick(String nick,String userName);
	
	/**
	 * 查找好友
	 * @param myuid：我的账号
	 * @param cuid：好友账号
	 * @return
	 */
	ContactBean findContactById(int myuid,int cuid);
	
	/**
	 * 查找指定账号的好友
	 * @param userName：指定的账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	ContactBean[] findContactsByUserName(String userName,int pageId,int pageSize);
	
	/**
	 * 查找指定id的一组好友
	 * @param myuid:登陆者的id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	UserBean[] findContactListByMyuid(int myuid,int pageId,int pageSize);
	
	/**
	 * 添加好友
	 * @param userName:当前用户的账号
	 * @param name：好友账号
	 * @return
	 */
	ContactBean addContact(String userName,String name);
	
	/**
	 * 删除好友
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);
	
	/**
	 * 从tb_new_good表中查询所有的新品的商品详情数据
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	NewGoodBean[] findNewAndBoutiqueGoods(int catId,int pageId,int pageSize);
	
	/**
	 * 从tb_boutique表中查询所有精选的列表数据
	 * @return
	 */
	BoutiqueBean[] findBoutiques();
	
	/** 查询分类中的大类型信息*/
	CategoryParentBean[] findCategoryParent();
	
	/**
	 * 查询小类别商品列表信息
	 * @param catId:小类别id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	CategoryChildBean[] findCategoryChildren(int parentId,int pageId,int pageSize);
	
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
