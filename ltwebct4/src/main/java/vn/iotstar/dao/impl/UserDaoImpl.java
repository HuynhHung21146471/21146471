package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.configs.DBConnectMySQL;
import vn.iotstar.dao.IUserDao;
import vn.iotstar.models.UserModel;

public class UserDaoImpl extends DBConnectMySQL implements IUserDao {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public List<UserModel> findAll() {

		String sql = "select * from ltwebct4.user";

		List<UserModel> list = new ArrayList<>(); // Tạo 1 list để truyền dữ liệu

		try {
			conn = super.getDatabaseConnection(); // kết nối database
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next() /* Next từng DÒNG tới cuối bảng */) {

				list.add(new UserModel(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("email"), rs.getString("fullname"), rs.getString("images"))); // Add vào
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public UserModel findById(int id) {
			
		String sql = "SELECT * FROM ltwebct4.user WHERE id = ? ";
		try {
			conn = super.getDatabaseConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
				
		while (rs.next()) {
			
			UserModel user = new UserModel();
		
			user.setId(rs.getInt("id"));
			user.setEmail(rs.getString("email"));
			user.setUsername(rs.getString("username"));
			user.setFullname(rs.getString("password"));
			user.setPassword(rs.getString("images"));
			user.setPassword(rs.getString("fullname"));
			
		return user;}
		} catch (Exception e) {
			e.printStackTrace();}
		
		return null;
	}

	@Override
	public void insert(UserModel user) {
		
		String sql = "INSERT INTO ltwebct4.user (id, username, email, password, images, fullname) VALUES (?, ?, ?, ?, ?, ?)"; 
	
		try {
			conn = super.getDatabaseConnection(); //kết nối database
			
			ps = conn.prepareStatement(sql);//ném câu sql vào cho thực thi
			
			ps.setInt(1, user.getId());
			ps.setString(2, user.getUsername()); 
			ps.setString(3, user.getEmail()); 
			ps.setString(4, user.getPassword());
			ps.setString(5,user.getImages());
			ps.setString(6, user.getFullname());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();	
		
		}
	}

	public static void main(String[] args) {
	
		UserDaoImpl userDao = new UserDaoImpl();
	
		//userDao.insert(new UserModel (3,"abcd", "abcd@gmail.com", "1234","","abcdef"));
		
				
		List<UserModel> list = userDao.findAll();
	
		for (UserModel user: list) {
				System.out.println(user);
		}
		
		System.out.println(userDao.findById(2));
		
	}
}
