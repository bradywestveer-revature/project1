package utilities;

import java.sql.*;

public class H2Utilities {
	private static void executeStatement (String sqlStatement) {
		try (Connection connection = DriverManager.getConnection (DatabaseCredentials.H2Url, DatabaseCredentials.H2Username, DatabaseCredentials.H2Password)) {
			PreparedStatement statement = connection.prepareStatement (sqlStatement);
			
			statement.executeUpdate ();
		}
		
		catch (SQLException exception) {
			exception.printStackTrace ();
		}
	}
	
	public static void createReimbursementStatusTable () {
		executeStatement ("CREATE TABLE ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID int PRIMARY KEY NOT NULL, REIMB_STATUS VARCHAR (10) NOT NULL);");
		
		executeStatement ("INSERT INTO ERS_REIMBURSEMENT_STATUS VALUES (1, 'PENDING');");
		executeStatement ("INSERT INTO ERS_REIMBURSEMENT_STATUS VALUES (2, 'APPROVED');");
		executeStatement ("INSERT INTO ERS_REIMBURSEMENT_STATUS VALUES (3, 'DENIED');");
	}
	
	public static void createReimbursementTypeTable () {
		executeStatement ("CREATE TABLE ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID int PRIMARY KEY NOT NULL, REIMB_TYPE VARCHAR (10) NOT NULL);");
		
		executeStatement ("INSERT INTO ERS_REIMBURSEMENT_TYPE VALUES (1, 'LODGING');");
		executeStatement ("INSERT INTO ERS_REIMBURSEMENT_TYPE VALUES (2, 'TRAVEL');");
		executeStatement ("INSERT INTO ERS_REIMBURSEMENT_TYPE VALUES (3, 'FOOD');");
		executeStatement ("INSERT INTO ERS_REIMBURSEMENT_TYPE VALUES (4, 'OTHER');");
	}
	
	public static void createUserRoleTable () {
		executeStatement ("CREATE TABLE ERS_USER_ROLES (ERS_USER_ROLE_ID int PRIMARY KEY NOT NULL, USER_ROLE VARCHAR (10) NOT NULL);");
		
		executeStatement ("INSERT INTO ERS_USER_ROLES VALUES (1, 'EMPLOYEE');");
		executeStatement ("INSERT INTO ERS_USER_ROLES VALUES (2, 'MANAGER');");
	}
	
	public static void dropReimbursementStatusTable () {
		executeStatement ("DROP TABLE ERS_REIMBURSEMENT_STATUS;");
	}
	
	public static void dropReimbursementTypeTable () {
		executeStatement ("DROP TABLE ERS_REIMBURSEMENT_TYPE;");
	}
	
	public static void dropUserRoleTable () {
		executeStatement ("DROP TABLE ERS_USER_ROLES;");
	}
	
	public static void createUsersTable () {
		executeStatement ("CREATE TABLE ERS_USERS (ERS_USERS_ID serial PRIMARY KEY NOT NULL, ERS_USERNAME VARCHAR (50) UNIQUE NOT NULL, ERS_PASSWORD VARCHAR (50) NOT NULL, USER_FIRST_NAME VARCHAR (100) NOT NULL, USER_LAST_NAME VARCHAR (100) NOT NULL, USER_EMAIL VARCHAR (150) UNIQUE NOT NULL, USER_ROLE_ID int NOT NULL REFERENCES ERS_USER_ROLES (ERS_USER_ROLE_ID));");
		
		executeStatement ("INSERT INTO ERS_USERS (ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES ('manager1', 'adm1n', 'Manager', 'One', 'managerone@gmail.com', 2);");
		executeStatement ("INSERT INTO ERS_USERS (ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES ('employee1', 'p4ssw0rd', 'Employee', 'One', 'employeeone@gmail.com', 1);");
		executeStatement ("INSERT INTO ERS_USERS (ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES ('employee2', 'p4ssw0rd', 'Employee', 'Two', 'employeetwo@gmail.com', 1);");
		executeStatement ("INSERT INTO ERS_USERS (ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID) VALUES ('employee3', 'p4ssw0rd', 'Employee', 'Three', 'employeethree@gmail.com', 1);");
	}
	
	public static void dropUsersTable () {
		executeStatement ("DROP TABLE ERS_USERS");
	}
	
	public static void createRequestsTable () {
		executeStatement ("CREATE TABLE ERS_REIMBURSEMENT (REIMB_ID serial PRIMARY KEY NOT NULL, REIMB_AMOUNT float NOT NULL, REIMB_SUBMITTED TIMESTAMP NOT NULL DEFAULT now (), REIMB_RESOLVED TIMESTAMP, REIMB_DESCRIPTION VARCHAR (250), REIMB_RECEIPT bytea, REIMB_AUTHOR int NOT NULL REFERENCES ERS_USERS (ERS_USERS_ID), REIMB_RESOLVER int REFERENCES ERS_USERS (ERS_USERS_ID), REIMB_STATUS_ID int NOT NULL DEFAULT 1 REFERENCES ERS_REIMBURSEMENT_STATUS (REIMB_STATUS_ID), REIMB_TYPE_ID int NOT NULL REFERENCES ERS_REIMBURSEMENT_TYPE (REIMB_TYPE_ID));");
	}
	
	public static void dropRequestsTable () {
		executeStatement ("DROP TABLE ERS_REIMBURSEMENT");
	}
}
