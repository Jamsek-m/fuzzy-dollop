package mJamsek;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DB {
	
	public static DB_Projekt getProjekt(String id){
		String sql = "SELECT *, DATE_FORMAT(PROJ_LAST_UPDATE,'%a, %d.%m.%Y') as LAST_UPDATE  FROM projekti WHERE ID_PROJ= ?";
		DatabaseConn conn = new DatabaseConn();
		try {
			PreparedStatement stmt = conn.connect().prepareStatement(sql);
			stmt.setInt(1, Integer.parseInt(id));
			ResultSet rez = stmt.executeQuery();
			DB_Projekt data = null;
			if(rez.next()){
				data = new DB_Projekt(
					rez.getString("ID_PROJ")
					,rez.getString("PROJ_NAME")
					,rez.getString("PROJ_DESC")
					,rez.getString("PROJ_DEPL_TYPE")
					,rez.getString("PROJ_LINK")
					,rez.getString("PROJ_ABOUT")
					,rez.getString("PROJ_VERSION")
					,rez.getString("PROJ_LAST_UPDATE")
					,rez.getString("PROJ_REPO_LINK")
					,rez.getString("PROJ_IMG")
					,rez.getString("ACTIVE")
					,rez.getString("LAST_UPDATE")
				);
			}
			return data;
		} catch(SQLException e){
			e.printStackTrace();
			return null;
		} finally {
			conn.disconnect();
		}
	}
	
	public static ArrayList<DB_Projekti> getProjects(){
		String sql = "SELECT id_PROJ as id, PROJ_name as name, PROJ_DESC as desk FROM projekti WHERE ACTIVE='A'";
		DatabaseConn mysqlConnect = new DatabaseConn();
		
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rez = statement.executeQuery();
			ArrayList<DB_Projekti> podatki = new ArrayList<DB_Projekti>();
			while(rez.next()){
			   DB_Projekti k = new DB_Projekti(rez.getString("id"), rez.getString("name"), rez.getString("desk"));
			   podatki.add(k);
			}
			return podatki;
	    
		} catch (SQLException e) {
		    e.printStackTrace();
		    return null;
		} finally {
		    mysqlConnect.disconnect();
		}
	}
	
}
