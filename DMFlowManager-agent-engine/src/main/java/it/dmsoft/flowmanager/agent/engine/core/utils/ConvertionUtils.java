package it.dmsoft.flowmanager.agent.engine.core.utils;



public class ConvertionUtils {

	public enum EndOfLine {
		CRLF("*CRLF"){
			@Override
			public String getEndLine() {
				return "\r\n";
			}

			@Override
			public byte[] getEndLineEbcdic() {
				byte[] endLineByteEbcdic = {(0x0D),(0x25)};
				return endLineByteEbcdic;
			}
			
		},
		
		CR("*CR"){
			@Override
			public String getEndLine() {
				return "\r";
			}
			
			@Override
			public byte[] getEndLineEbcdic() {
				byte[] endLineByteEbcdic = {(0x0D)};
				return endLineByteEbcdic;
			}
		},
		LF("*LF"){
			@Override
			public String getEndLine() {
				return "\n";
			}
			
			@Override
			public byte[] getEndLineEbcdic() {
				byte[] endLineByteEbcdic = {(0x25)};
				return endLineByteEbcdic;
			}
		},
		LFCR("*LFCR"){
			@Override
			public String getEndLine() {
				return "\n\r";
			}
			
			@Override
			public byte[] getEndLineEbcdic() {
				byte[] endLineByteEbcdic = {(0x25),(0x0D)};
				return endLineByteEbcdic;
			}
		};
		
		private String code;
		
		private EndOfLine(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
		
	    // Metodo per cercare un enum a partire dal valore stringa
	    public static EndOfLine fromString(String endOfLine) {
	        for (EndOfLine eolType : EndOfLine.values()) {
	            if (eolType.code.equalsIgnoreCase(endOfLine)) {
	                return eolType;
	            }
	        }
	        // Se non viene trovato alcun valore corrispondente, restituisce null
	        return null;
	    }
		
		public abstract String getEndLine();
		
		public abstract byte[] getEndLineEbcdic();
	}
	
	
	
	
	public enum RmvBlank {
		NONE("*NONE"){
			@Override
			public String executeRmvBlank(String st, int size) {
				return StringUtils.rightPad(st, ' ', size);
			}
			
		},
		LEADING("*LEADING"){
			@Override
			public String executeRmvBlank(String st, int size) {
				String str = st.replaceFirst("^\\s+", "");
				if(StringUtils.isNullOrEmpty(str)) return " ";
				else return StringUtils.rightPad(str, ' ', size);
			}
			
		},
		TRAILING("*TRAILING"){
			@Override
			public String executeRmvBlank(String st, int size) {
				String str = st.replaceFirst("\\s+$", "");
				if(StringUtils.isNullOrEmpty(str)) return " ";
				else return str;
			}
		},
		BOTH("*BOTH"){
			@Override
			public String executeRmvBlank(String st, int size) {
				String str = st.trim();
				if(StringUtils.isNullOrEmpty(str)) return " ";
				else return str;
			}
		};
		
		private String code;
		
		private RmvBlank(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
		
	    // Metodo per cercare un enum a partire dal valore stringa
	    public static RmvBlank fromString(String rmvBlank) {
	        for (RmvBlank rbType : RmvBlank.values()) {
	            if (rbType.code.equalsIgnoreCase(rmvBlank)) {
	                return rbType;
	            }
	        }
	        // Se non viene trovato alcun valore corrispondente, restituisce null
	        return null;
	    }
		
		public abstract String executeRmvBlank(String st, int size);
	}
	
	
	public enum FieldFillType {
		NONE("*NONE"){
			@Override
			public String executeFieldFill(String st, int size) {
				return st;
			}
			
		},
		ZERO("*ZERO"){
			@Override
			public String executeFieldFill(String st, int size) {
				return StringUtils.leftPad(st, '0', size);
			}
			
		},
		BLANK("*BLANK"){
			@Override
			public String executeFieldFill(String st, int size) {
				return StringUtils.leftPad(st, ' ', size);
			}
		};
		
		private String code;
		
		private FieldFillType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
		
	    // Metodo per cercare un enum a partire dal valore stringa
	    public static FieldFillType fromString(String fieldFillType) {
	        for (FieldFillType ffType : FieldFillType.values()) {
	            if (ffType.code.equalsIgnoreCase(fieldFillType)) {
	                return ffType;
	            }
	        }
	        // Se non viene trovato alcun valore corrispondente, restituisce null
	        return null;
	    }
		
		public abstract String executeFieldFill(String st, int size);
	}
	
	
}
