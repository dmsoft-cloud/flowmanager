package it.dmsoft.flowmanager.be.common;

import com.fasterxml.jackson.annotation.JsonValue;

public class BaseEntity {
	
	public static final String Y = "S";
	
	public static final String N = "N";
	
	
	public enum YesNo implements CodeEnum {
		
		YES(Y),
		NO(N);
		
		private String code;
		
		private YesNo(String code) {
			this.code = code;
		}
		
		@JsonValue
		public String getCode() {
			return this.code;
		}
		
		public static YesNo getYesNo(String code) {
			return CodeEnum.getCodeEnum(YesNo.class, code);
		}
		
		
	}
	
    public enum Type implements CodeEnum {
		ORIGIN("D"),
		FILESYSTEM("I");
		
		private String code;
		
		private Type(String code) {
			this.code = code;
		}
		
		@JsonValue
		public String getCode() {
			return this.code;
		}
		
		public static Type getType(String code) {
			return CodeEnum.getCodeEnum(Type.class, code);
		}
	}
	
    public enum Direction implements CodeEnum {
		INBOUND("I"),
		OUTBOUND("O");
		
		private String code;
		
		private Direction(String code) {
			this.code = code;
		}
		
		@JsonValue
		public String getCode() {
			return this.code;
		}
		
		public static Direction getDirection(String code) {
			return CodeEnum.getCodeEnum(Direction.class, code);
		}
	}
    
    public enum Locale implements CodeEnum {
 		IT("IT"),
 		US("US");
 		
 		private String code;
 		
 		private Locale(String code) {
 			this.code = code;
 		}
 		
 		public String getCode() {
 			return this.code;
 		}
 		
 		public static Locale getLocale(String code) {
 			return CodeEnum.getCodeEnum(Locale.class, code);
 		}
 	}
    
    public enum FileFormat implements CodeEnum {
 		CSV("*CSV"),
 		FIXED("*FIXED");
 		
 		private String code;
 		
 		private FileFormat(String code) {
 			this.code = code;
 		}
 		
 		@JsonValue
 		public String getCode() {
 			return this.code;
 		}
 		
 		public static FileFormat getFileFormat(String code) {
 			return CodeEnum.getCodeEnum(FileFormat.class, code);
 		}
 	}
    
    public enum DbType implements CodeEnum {
 		DB2_ISERIES("DB2"),
 		MSSQLSERVER("MSSQL");
 		
 		private String code;
 		
 		private DbType(String code) {
 			this.code = code;
 		}
 		
 		@JsonValue
 		public String getCode() {
 			return this.code;
 		}
 		
 		public static DbType getDbType(String code) {
 			return CodeEnum.getCodeEnum(DbType.class, code);
 		}
 	}
    
    public enum ConnectionType implements CodeEnum {
 		SFTP("S"),
 		FTP("F"),
 		SCP("C"),
 		SMTP("M"),
 		IMAP("I");
 		
 		private String code;
 		
 		private ConnectionType(String code) {
 			this.code = code;
 		}
 		
 		@JsonValue
 		public String getCode() {
 			return this.code;
 		}
 		
 		public static ConnectionType getConnectionType(String code) {
 			return CodeEnum.getCodeEnum(ConnectionType.class, code);
 		}
 	}
    
}
