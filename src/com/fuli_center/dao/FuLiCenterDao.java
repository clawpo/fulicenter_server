package com.fuli_center.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fuli_center.bean.AlbumBean;
import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.ContactBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.bean.PropertyBean;
import com.fuli_center.bean.UserBean;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.fuli_center.utils.JdbcUtils;
import com.fuli_center.utils.Utils;

/**
 * 数据访问层
 * 
 * @author yao
 */
public class FuLiCenterDao implements IFuLiCenterDao {

	@Override
	public int addCart(CartBean cart) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "insert into " + IFuLiCenterBiz.Cart.TABLE_NAME + "("
				+ IFuLiCenterBiz.Cart.GOODS_ID + ","
				+ IFuLiCenterBiz.Cart.COUNT + ","
				+ IFuLiCenterBiz.Cart.USER_NAME+ ","
				+ IFuLiCenterBiz.Cart.IS_CHECKED
				+ ")values(?,?,?,?)";
		int count =0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, cart.getGoodsId());
			statement.setInt(2, cart.getCount());
			statement.setString(3, cart.getUserName());
			statement.setBoolean(4,cart.isChecked());
			count = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(null, statement, connection);
		}
		if(count>0){
			int id=findCatLastId();
			return id;
		}
		return 0;
	}

	private int findCatLastId() {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select id from " + IFuLiCenterBiz.Cart.TABLE_NAME
			+" order by id desc limit 0,1";
		try {
			statement = connection.prepareStatement(sql);
			set = statement.executeQuery();
			if (set.next()) {
				int id=set.getInt(IFuLiCenterBiz.Collect.ID);
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(set, statement, connection);
		}
		return 0;
	}

	@Override
	public boolean deleteCart(int cartId) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "delete from " + IFuLiCenterBiz.Cart.TABLE_NAME
				+ " where " + IFuLiCenterBiz.Cart.ID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, cartId);
			int count = statement.executeUpdate();
			return count == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateCart(int cartId,int count) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "update " + IFuLiCenterBiz.Cart.TABLE_NAME + " set "
				+ IFuLiCenterBiz.Cart.COUNT + "=?" + "where "
				+ IFuLiCenterBiz.Cart.ID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, count);
			statement.setInt(2, cartId);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public CartBean[] findCarts(String userName,int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.Cart.TABLE_NAME
			+" where "+IFuLiCenterBiz.Cart.USER_NAME+"=? limit ?,?";
		CartBean[] carts = new CartBean[0];
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			while (set.next()) {
				CartBean cart = readCart(set);
				carts=Utils.add(carts, cart);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(set, statement, connection);
		}

		return carts;
	}

	/**
	 * ��ȡһ����¼
	 * 
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	/*
	 * statement.setString(1, cart.getGood().getGoodsId());
	 * statement.setString(1, cart.getGood().getGoodsId()); statement.setInt(2,
	 * cart.getCount()); statement.setBoolean(3, cart.isChecked());
	 */
	private CartBean readCart(ResultSet set) throws SQLException {
		CartBean cart = new CartBean();
		cart.setId(set.getInt(IFuLiCenterBiz.Cart.ID));
		cart.setCount(set.getInt(IFuLiCenterBiz.Cart.COUNT));
		cart.setGoodsId(set.getInt(IFuLiCenterBiz.Cart.GOODS_ID));
		cart.setUserName(set.getString(IFuLiCenterBiz.Cart.USER_NAME));
		cart.setChecked(set.getInt(IFuLiCenterBiz.Cart.IS_CHECKED)==1);
		return cart;
	}

	@Override
	public UserBean findUserByUserName(String userName) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.User.TABLE_NAME
				+ " where " + IFuLiCenterBiz.User.USER_NAME + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			set = statement.executeQuery();
			if (set.next()) {
				UserBean user = readUser(set);
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	/**
	 * ��set�ж�ȡһ����¼������UserBean���͵�ʵ��
	 * 
	 * @param set
	 *            :��ѯ�Ľ��
	 * @return
	 * @throws SQLException
	 */
	private UserBean readUser(ResultSet set) throws SQLException {
		UserBean user = new UserBean();
		int id = set.getInt(1);
		user.setId(id);

		String userName = set.getString(IFuLiCenterBiz.User.USER_NAME);
		user.setUserName(userName);

		String nick = set.getString(IFuLiCenterBiz.User.NICK);
		user.setNick(nick);

		String avatar = set.getString(IFuLiCenterBiz.User.AVATAR);
		user.setAvatar(avatar);

		String password = set.getString(IFuLiCenterBiz.User.PASSWORD);
		user.setPassword(password);

		int unreadMsgCount=set.getInt(IFuLiCenterBiz.User.UN_READ_MSG_COUNT);
		user.setUnreadMsgCount(unreadMsgCount);
		
		return user;
	}

	@Override
	public UserBean[] findUsersByUserName(String userName, int pageId,
			int pageSize) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.User.TABLE_NAME
			+" where "+IFuLiCenterBiz.User.USER_NAME+" like ? limit ?,?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, "%"+userName+"%");
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set=statement.executeQuery();
			UserBean[] users=new UserBean[0];
			while(set.next()){
				UserBean user = readUser(set);
				if(user!=null){
					users=Utils.add(users, user);
				}
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public UserBean[] findUsersByNick(String nick, int pageId, int pageSize) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.User.TABLE_NAME
			+" where "+IFuLiCenterBiz.User.NICK+" like ?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, "%"+nick+"%");
			set=statement.executeQuery();
			UserBean[] users=new UserBean[0];
			while(set.next()){
				UserBean user = readUser(set);
				users=Utils.add(users,user);
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public boolean addUser(UserBean user) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "insert into " + IFuLiCenterBiz.User.TABLE_NAME + "("
				+ IFuLiCenterBiz.User.USER_NAME + ","
				+ IFuLiCenterBiz.User.NICK + ","
				+ IFuLiCenterBiz.User.PASSWORD + ")values(?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getNick());
			statement.setString(3, user.getPassword());
			int count = statement.executeUpdate();
			return count == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean deleteUser(String userName) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+IFuLiCenterBiz.User.TABLE_NAME
			+" where "+IFuLiCenterBiz.User.USER_NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}
	
	@Override
	public boolean updateUser(UserBean user) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+IFuLiCenterBiz.User.TABLE_NAME
			+" set "+IFuLiCenterBiz.User.NICK+"=?,"
			+IFuLiCenterBiz.User.AVATAR+"=?,"
			+IFuLiCenterBiz.User.UN_READ_MSG_COUNT+"=?"
			+" where "+IFuLiCenterBiz.User.USER_NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, user.getNick());
			statement.setString(2, user.getAvatar());
			statement.setInt(3, user.getUnreadMsgCount());
			statement.setString(4, user.getUserName());
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateNick(String nick,String userName) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+IFuLiCenterBiz.User.TABLE_NAME
			+" set "+IFuLiCenterBiz.User.NICK+"=?"
			+" where "+IFuLiCenterBiz.User.USER_NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, nick);
			statement.setString(2, userName);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}
	
	@Override
	public boolean isExistsContact(String userName, String name) {
		//����1����user���в�ѯuserNameֵ��userName��id
				int myuid=findIdByUserName(userName);
				//����2����user���в�ѯuseNameֵ��name��id
				int cuid=findIdByUserName(name);
				//��ѯcontact���myuid����ѯ������user���userName
				ResultSet set=null;
				PreparedStatement statement=null;
				Connection connection = JdbcUtils.getConnection();
				String sql="select "+IFuLiCenterBiz.Contact.MYUID
					+" from "+IFuLiCenterBiz.Contact.TABLE_NAME
					+" where "+IFuLiCenterBiz.Contact.MYUID+"=? and "
					+IFuLiCenterBiz.Contact.CUID+"=?";
				try {
					statement=connection.prepareStatement(sql);
					statement.setInt(1, myuid);
					statement.setInt(2, cuid);
					set=statement.executeQuery();
					if(set.next()){
						return set.getInt(1)>0;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					JdbcUtils.closeAll(set, statement, connection);
				}
				
				return false;
	}

	/**
	 * ���userName��ֵ��ѯid
	 * @param userName
	 * @return
	 */
	private int findIdByUserName(String userName) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select "+IFuLiCenterBiz.User.ID
			+" from "+IFuLiCenterBiz.User.TABLE_NAME
			+" where "+IFuLiCenterBiz.User.USER_NAME+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			set=statement.executeQuery();
			if(set.next()){
				int id=set.getInt(1);
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return 0;
	}
	
	@Override
	public ContactBean findContactById(int myuid, int cuid) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.Contact.TABLE_NAME
			+" where "+IFuLiCenterBiz.Contact.MYUID+"=? and "
			+IFuLiCenterBiz.Contact.CUID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			set=statement.executeQuery();
			if(set.next()){
				ContactBean contact=readContact(set);
				return contact;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	/**
	 * ��set����contact���ж�ȡһ����¼
	 * @param set
	 * @return
	 * @throws SQLException 
	 */
	private ContactBean readContact(ResultSet set) throws SQLException {
		int myuid=set.getInt(IFuLiCenterBiz.Contact.MYUID);
		int cuid=set.getInt(IFuLiCenterBiz.Contact.CUID);
		ContactBean contact=new ContactBean(myuid, cuid);
		
		return contact;
	}
	
	@Override
	public ContactBean[] findContactsByUserName(String userName, int pageId,
			int pageSize) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.Contact.TABLE_NAME
			+" where "+IFuLiCenterBiz.Contact.MYUID+"=(select "
			+IFuLiCenterBiz.User.ID
			+" from "+IFuLiCenterBiz.User.TABLE_NAME
			+" where "+IFuLiCenterBiz.User.USER_NAME+"=?)";
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			set=statement.executeQuery();
			ContactBean[] contacts=new ContactBean[0];
			while(set.next()){
				ContactBean contact=readContact(set);
				contacts=Utils.add(contacts,contact);
			}
			return contacts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		
		return null;
	}

	@Override
	public UserBean[] findContactsByMyuid(int myuid, int pageId, int pageSize) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.User.TABLE_NAME
			+" where "+IFuLiCenterBiz.User.ID+" in("
			+"select "+IFuLiCenterBiz.Contact.CUID+" from "
			+IFuLiCenterBiz.Contact.TABLE_NAME
			+" where "+IFuLiCenterBiz.Contact.MYUID+"=?)"
			+" limit ?,?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set=statement.executeQuery();
			UserBean[] users=new UserBean[0];
			while(set.next()){
				UserBean user=readUser(set);
				users=Utils.add(users, user);
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public ContactBean addContact(String userName, String name) {
		boolean existsContact = isExistsContact(userName, name);
		if(existsContact){
			System.out.println("已是好友");
			return null;
		}
		int myuid=findIdByUserName(userName);
		int cuid=findIdByUserName(name);
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="insert into "+IFuLiCenterBiz.Contact.TABLE_NAME
			+"("+IFuLiCenterBiz.Contact.MYUID
			+","+IFuLiCenterBiz.Contact.CUID
			+")values(?,?)";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			int count = statement.executeUpdate();
			ContactBean contact = findContactById(myuid, cuid);
			contact.setResult("ok");
			return contact;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return null;
	}

	@Override
	public ContactBean addContact(int myuid, int cuid) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="insert into "+IFuLiCenterBiz.Contact.TABLE_NAME
			+"("+IFuLiCenterBiz.Contact.MYUID
			+","+IFuLiCenterBiz.Contact.CUID
			+")values(?,?)";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			int count = statement.executeUpdate();
			ContactBean contact = findContactById(myuid, cuid);
			contact.setResult("ok");
			return contact;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return null;
	}
	
	@Override
	public boolean deleteContact(int myuid, int cuid) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+IFuLiCenterBiz.Contact.TABLE_NAME
			+" where "+IFuLiCenterBiz.Contact.MYUID+"=? and "
			+IFuLiCenterBiz.Contact.CUID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			int count = statement.executeUpdate();
			return count==1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}


	@Override
	public BoutiqueBean[] findBoutiques() {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.Boutique.TABLE_NAME;
		try {
			statement=connection.prepareStatement(sql);
			set=statement.executeQuery();
			BoutiqueBean[] boutiques=new BoutiqueBean[0];
			while(set.next()){
				BoutiqueBean boutique=new BoutiqueBean();
				boutique.setDescription(set.getString(IFuLiCenterBiz.Boutique.DESCRIPTION));
				boutique.setId(set.getInt(IFuLiCenterBiz.Boutique.ID));
				boutique.setImageurl(set.getString(IFuLiCenterBiz.Boutique.IMAGE_URL));
				boutique.setName(set.getString(IFuLiCenterBiz.Boutique.NAME));
				boutique.setTitle(set.getString(IFuLiCenterBiz.Boutique.TITLE));
				boutiques=Utils.add(boutiques, boutique);
			}
			return boutiques;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public NewGoodBean[] findNewAndBoutiqueGoods(int catId, int pageId, int pageSize) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.NewAndBoutiqueGood.TABLE_NAME
			+" where "
			+IFuLiCenterBiz.NewAndBoutiqueGood.CAT_ID+"=?"
			+" limit ?,?";
		System.out.println("findNewAndBoutiqueGoods.sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, catId);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set=statement.executeQuery();
			NewGoodBean[] goods=new NewGoodBean[0];
			while(set.next()){
				NewGoodBean good=new NewGoodBean();
				good.setAddTime(set.getLong(IFuLiCenterBiz.NewAndBoutiqueGood.ADD_TIME));
				good.setCatId(set.getInt(IFuLiCenterBiz.NewAndBoutiqueGood.CAT_ID));
				good.setColorCode(set.getString(IFuLiCenterBiz.Property.COLOR_CODE));
				good.setColorId(set.getInt(IFuLiCenterBiz.Property.COLOR_ID));
				good.setColorName(set.getString(IFuLiCenterBiz.Property.COLOR_NAME));
				good.setColorUrl(set.getString(IFuLiCenterBiz.Property.COLOR_URL));
				good.setCurrencyPrice(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.CURRENT_PRICE));
				good.setGoodsId(set.getInt(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_ID));
				good.setGoodsBrief(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_BRIEF));
				good.setGoodsEnglishName(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_ENGLISH_NAME));
				good.setGoodsImg(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_IMG));
				good.setGoodsName(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_NAME));
				good.setGoodsThumb(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_THUMB));
				good.setId(set.getInt(IFuLiCenterBiz.NewAndBoutiqueGood.ID));
				good.setPromote(set.getInt(IFuLiCenterBiz.NewAndBoutiqueGood.IS_PROMOTE)==1?true:false);
				good.setPromotePrice(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.PROMOTE_PRICE));
				good.setRankPrice(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.RANK_PRICE));
				good.setShopPrice(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.SHOP_PRICE));
				goods=Utils.add(goods, good);
			}
			return goods;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public CategoryParentBean[] findCategoryParent() {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.CategoryParent.TABLE_NAME;
		try {
			statement=connection.prepareStatement(sql);
			set=statement.executeQuery();
			CategoryParentBean[] parents=new CategoryParentBean[0];
			while(set.next()){
				CategoryParentBean parent=new CategoryParentBean();
				parent.setId(set.getInt(IFuLiCenterBiz.CategoryParent.ID));
				parent.setImageUrl(set.getString(IFuLiCenterBiz.CategoryParent.IMAGE_URL));
				parent.setName(set.getString(IFuLiCenterBiz.CategoryParent.NAME));
				parents=Utils.add(parents, parent);
			}
			return parents;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public CategoryChildBean[] findCategoryChildren(int parentId, int pageId,
			int pageSize) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.CategoryChild.TABLE_NAME
			+" where "+IFuLiCenterBiz.CategoryChild.PARENT_ID+"=?"
			+" limit ?,?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, parentId);
			statement.setInt(2, pageId);
			statement.setInt(3,pageSize);
			set=statement.executeQuery();
			CategoryChildBean[] children=new CategoryChildBean[0];
			while(set.next()){
				CategoryChildBean child=new CategoryChildBean();
				child.setId(set.getInt(IFuLiCenterBiz.CategoryChild.ID));
				child.setImageUrl(set.getString(IFuLiCenterBiz.CategoryChild.IMAGE_URL));
				child.setName(set.getString(IFuLiCenterBiz.CategoryChild.NAME));
				child.setParentId(set.getInt(IFuLiCenterBiz.CategoryChild.PARENT_ID));
				children=Utils.add(children, child);
			}
			return children;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public GoodDetailsBean findGoodDetails(int goodsId) {
		GoodDetailsBean good=null;
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.GoodDetails.TABLE_NAME
			+" where "+IFuLiCenterBiz.GoodDetails.GOODS_ID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, goodsId);
			set=statement.executeQuery();
			if(set.next()){
				good = readGoodDetails(set);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}		
		if(good==null){
			return null;
		}
		PropertyBean[] properties=findProperties(good.getGoodsId());
		ArrayList<PropertyBean> list = Utils.array2List(properties);
		if(properties!=null){
			good.setProperties(properties);
			for (PropertyBean p : properties) {
				AlbumBean[] albums=findAlbums(good.getGoodsId());
				if(albums!=null){
//					ArrayList<AlbumBean> albumList = Utils.array2List(albums);
					p.setAlbums(albums);
				}
			}
		}
		return good;
	}

	private AlbumBean[] findAlbums(int goodsId) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.Album.TABLE_NAME
			+" where "+IFuLiCenterBiz.Album.PID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, goodsId);
			set=statement.executeQuery();
			AlbumBean[] albums=new AlbumBean[0];
			while(set.next()){
				AlbumBean album=new AlbumBean();
				album.setPid(set.getInt(IFuLiCenterBiz.Album.PID));
				album.setImgId(set.getInt(IFuLiCenterBiz.Album.IMG_ID));
				album.setImgUrl(set.getString(IFuLiCenterBiz.Album.IMG_URL));
				album.setThumbUrl(set.getString(IFuLiCenterBiz.Album.THUMB_URL));
				albums=Utils.add(albums, album);
			}
			return albums;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	/**
	 * 查询指定商品id的属性
	 * @param goodId
	 * @return
	 */
	private PropertyBean[] findProperties(int goodsId) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.Property.TABLE_NAME
			+" where "+IFuLiCenterBiz.Property.GOODS_ID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, goodsId);
			set=statement.executeQuery();
			PropertyBean[] properties=new PropertyBean[0];
			while(set.next()){
				PropertyBean p=new PropertyBean();
				p.setId(set.getInt(IFuLiCenterBiz.Property.ID));
				p.setColorCode(set.getString(IFuLiCenterBiz.Property.COLOR_CODE));
				p.setColorId(set.getInt(IFuLiCenterBiz.Property.COLOR_ID));
				p.setColorImg(set.getString(IFuLiCenterBiz.Property.COLOR_IMG));
				p.setColorName(set.getString(IFuLiCenterBiz.Property.COLOR_NAME));
				p.setColorUrl(set.getString(IFuLiCenterBiz.Property.COLOR_URL));
				properties=Utils.add(properties, p);
			}
			return properties;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public ColorBean[] findColorsByCatId(int catId) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.Color.TABLE_NAME
			+" where "+IFuLiCenterBiz.Color.CAT_ID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, catId);
			set=statement.executeQuery();
			ColorBean[] colors=new ColorBean[0];
			while(set.next()){
				ColorBean color=new ColorBean();
				color.setCatId(catId);
				color.setColorCode(set.getString(IFuLiCenterBiz.Color.COLOR_CODE));
				color.setColorId(set.getInt(IFuLiCenterBiz.Color.COLOR_ID));
				color.setColorImg(set.getString(IFuLiCenterBiz.Color.COLOR_IMG));
				color.setColorName(set.getString(IFuLiCenterBiz.Color.COLOR_NAME));
				colors=Utils.add(colors, color);
			}
			return colors;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public int addCollect(CollectBean collect) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "insert into " + IFuLiCenterBiz.Collect.TABLE_NAME + "("
				+ IFuLiCenterBiz.Collect.GOODS_ID + ","
				+ IFuLiCenterBiz.Collect.USER_NAME + ","
				+ IFuLiCenterBiz.Collect.GOODS_NAME + ","
				+ IFuLiCenterBiz.Collect.GOODS_ENGLISH_NAME+ ","
				+ IFuLiCenterBiz.Collect.GOODS_THUMB+ ","
				+ IFuLiCenterBiz.Collect.GOODS_IMG+ ","
				+ IFuLiCenterBiz.Collect.ADD_TIME
				+ ")values(?,?,?,?,?,?,?)";
		int count =0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, collect.getGoodsId());
			statement.setString(2, collect.getUserName());
			statement.setString(3, collect.getGoodsName());
			statement.setString(4, collect.getGoodsEnglishName());
			statement.setString(5, collect.getGoodsThumb());
			statement.setString(6, collect.getGoodsImg());
			statement.setLong(7, collect.getAddTime());
			count = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(null, statement, connection);
		}
		if(count==1){
			int id=findCollecLastId();
			return id;
		}
		return 0;
	}

	/**
	 * 查询刚插入衣橱的记录的id
	 * @return
	 */
	private int findCollecLastId() {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select id from " + IFuLiCenterBiz.Collect.TABLE_NAME
			+" order by id desc limit 0,1";
		try {
			statement = connection.prepareStatement(sql);
			set = statement.executeQuery();
			if (set.next()) {
				int id=set.getInt(IFuLiCenterBiz.Collect.ID);
				return id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(set, statement, connection);
		}
		return 0;
	}

	@Override
	public boolean deleteCollect(String userName,int id) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "delete from " + IFuLiCenterBiz.Collect.TABLE_NAME
			+ " where " + IFuLiCenterBiz.Collect.GOODS_ID + "=? and "
			+IFuLiCenterBiz.User.USER_NAME+"=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, userName);
			int count = statement.executeUpdate();
			return count == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}
	
	@Override
	public CollectBean[] findCollects(String userName, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.Collect.TABLE_NAME
				+ " where " + IFuLiCenterBiz.Collect.USER_NAME + "=? limit ?,?";
		CollectBean[] collects=new CollectBean[0];
		try {
			CollectBean w=new CollectBean();
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			while (set.next()) {
				w=readCollect(set);
				collects=Utils.add(collects, w);
			}
			return collects;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	/**
	 * 读取一条收藏的商品的记录
	 * @param set
	 * @return
	 */
	private CollectBean readCollect(ResultSet set) {
		CollectBean collect=new CollectBean();
		try {
			collect.setId(set.getInt(IFuLiCenterBiz.Collect.ID));
			collect.setAddTime(set.getLong(IFuLiCenterBiz.Collect.ADD_TIME));
			collect.setGoodsEnglishName(set.getString(IFuLiCenterBiz.Collect.GOODS_ENGLISH_NAME));
			collect.setGoodsId(set.getInt(IFuLiCenterBiz.Collect.GOODS_ID));
			collect.setGoodsImg(set.getString(IFuLiCenterBiz.Collect.GOODS_IMG));
			collect.setGoodsName(set.getString(IFuLiCenterBiz.Collect.GOODS_NAME));
			collect.setGoodsThumb(set.getString(IFuLiCenterBiz.Collect.GOODS_THUMB));
			collect.setUserName(set.getString(IFuLiCenterBiz.Collect.USER_NAME));
			return collect;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean isCollect(String userName, int goodsId) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select id from " + IFuLiCenterBiz.Collect.TABLE_NAME
			+" where "+IFuLiCenterBiz.Collect.USER_NAME+"=? and "
			+IFuLiCenterBiz.Collect.GOODS_ID+"=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, goodsId);
			set = statement.executeQuery();
			if (set.next()) {
				int id=set.getInt(IFuLiCenterBiz.Collect.ID);
				return id>0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.closeAll(set, statement, connection);
		}
		return false;
	}
	
	@Override
	public ArrayList<GoodDetailsBean> findGoodsDetails(int catId, int pageId, int pageSize) {
		ArrayList<GoodDetailsBean> goodDetailsList=new ArrayList<GoodDetailsBean>();
		GoodDetailsBean good=null;
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+IFuLiCenterBiz.GoodDetails.TABLE_NAME
			+" where "+IFuLiCenterBiz.GoodDetails.CAT_ID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, catId);
			set=statement.executeQuery();
			while(set.next()){
				good = readGoodDetails(set);
				goodDetailsList.add(good);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}		
		if(goodDetailsList==null){
			return null;
		}
		for (GoodDetailsBean goodDetails : goodDetailsList) {
			PropertyBean[] properties=findProperties(good.getGoodsId());
			ArrayList<PropertyBean> list = Utils.array2List(properties);
			if(properties!=null){
				goodDetails.setProperties(properties);
				for (PropertyBean p : properties) {
					AlbumBean[] albums=findAlbums(good.getGoodsId());
					if(albums!=null){
						p.setAlbums(albums);
					}
				}
			}
		}
		return goodDetailsList;
	}

	private GoodDetailsBean readGoodDetails(ResultSet set) throws SQLException {
		GoodDetailsBean good;
		good=new GoodDetailsBean();
		good.setAddTime(set.getLong(IFuLiCenterBiz.GoodDetails.ADD_TIME));
		good.setCatId(set.getInt(IFuLiCenterBiz.GoodDetails.CAT_ID));
		good.setCurrencyPrice(set.getString(IFuLiCenterBiz.GoodDetails.CURRENT_PRICE));
		good.setGoodsId(set.getInt(IFuLiCenterBiz.GoodDetails.GOODS_ID));
		good.setGoodsBrief(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_BRIEF));
		good.setGoodsEnglishName(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_ENGLISH_NAME));
		good.setGoodsImg(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_IMG));
		good.setGoodsName(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_NAME));
		good.setGoodsThumb(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_THUMB));
		good.setId(set.getInt(IFuLiCenterBiz.GoodDetails.ID));
		good.setPromote(set.getInt(IFuLiCenterBiz.GoodDetails.IS_PROMOTE)==1?true:false);
		good.setPromotePrice(set.getString(IFuLiCenterBiz.GoodDetails.PROMOTE_PRICE));
		good.setRankPrice(set.getString(IFuLiCenterBiz.GoodDetails.RANK_PRICE));
		good.setShopPrice(set.getString(IFuLiCenterBiz.GoodDetails.SHOP_PRICE));
		good.setShareUrl(set.getString(IFuLiCenterBiz.GoodDetails.SHARE_URL));
		return good;
	}
}
