package it.dmsoft.flowmanager.agent.engine.core.manager;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.dmsoft.flowmanager.be.repositories.ScheduleDateRepository;
import it.dmsoft.flowmanager.common.domain.Domains.YesNo;
import it.dmsoft.flowmanager.agent.engine.core.exception.OperationException;
import it.dmsoft.flowmanager.agent.engine.core.exception.ParameterException;
import it.dmsoft.flowmanager.agent.engine.core.flow.builder.FlowBuilder;
import it.dmsoft.flowmanager.agent.engine.core.model.ExecutionFlowData;
import it.dmsoft.flowmanager.agent.engine.core.operations.params.OperationParams;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.InteractiveType;
import it.dmsoft.flowmanager.agent.engine.core.utils.Constants.TransferType;
import it.dmsoft.flowmanager.agent.engine.core.utils.FormatUtils;
import it.dmsoft.flowmanager.agent.engine.core.utils.StringUtils;
import it.dmsoft.flowmanager.agent.engine.generic.utility.logger.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public abstract class FlowManager {

	private static Logger logger = Logger.getLogger(FlowManager.class);
	
	@PersistenceContext
    protected EntityManager entityManager;
	
	protected ScheduleDateRepository scheduleDateRepository;
	
	/*
	public static void main(String[] args) throws Exception {
		ExecutionFlowData executionFlowData = new ExecutionFlowData();
		executionFlowData.setFlowDirezione(Constants.OUTBOUND);
		executionFlowData.setFlowTipFlusso(Constants.DB2);
	
		OperationParams operationParams = new OperationParams();
		
		for (Replacer replacer : Replacer.values()) {
			System.out.println(replacer.getReplaceString(executionFlowData, operationParams));
		}
	}
	*/
	
	public enum Replacer {
		DT(Constants.$DT) {
			@Override
			public String replaceString(String str, ExecutionFlowData executionFlowData, OperationParams operationParams)
					throws Exception {
				
				Pattern pattern = Pattern.compile(Constants.$DT_SEPARATOR_REGEXP);
				Matcher matcher = pattern.matcher(str);
				if (matcher.find()) {
					String replaceDtString = getReplaceDtString(str, matcher.group(1), operationParams);
					return str.replace(matcher.group(), replaceDtString);
				}
				
				return super.replaceString(str, executionFlowData, operationParams);
			}
			
			private String getReplaceDtString(String str, String formatCode, OperationParams operationParams) {
		        return FormatUtils.formatDate(operationParams.getExecutionDate(), formatCode);
			}
			
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return operationParams.getExecutionDate().toString();
			}
		},
		
		DS(Constants.$DS) {	
			@Override
			public String replaceString(String str, ExecutionFlowData executionFlowData, OperationParams operationParams)
					throws Exception {
				String ret = str;
				
				Pattern pattern = Pattern.compile(Constants.$DS_SEPARATOR_REGEXP);
				Matcher matcher = pattern.matcher(str);
				if (matcher.find()) {
					String replaceDsString;
					if(str.matches(Constants.REGEXP_WILDCARD + Constants.$DS__REGEXP + Constants.REGEXP_WILDCARD)) {
						replaceDsString = getReplaceDsString(str, matcher.group(5), operationParams);
					}
					else {
						replaceDsString = getReplaceDsString(str, matcher.group(1), operationParams);
					}
					return str.replace(matcher.group(), replaceDsString);
				}
				
				/**if(str.matches(Constants.REGEXP_WILDCARD + Constants.$DS__REGEXP + Constants.REGEXP_WILDCARD)) {					
					return getUpdatedDate(ret, operationParams);
				}**/
				
				return super.replaceString(ret, executionFlowData, operationParams);
			}
			
			private String getReplaceDsString(String str, String formatCode, OperationParams operationParams) {
				//Date dsDate = getUpdatedDate(str, operationParams);
				Date date = new Date();
				if(str.matches(Constants.REGEXP_WILDCARD + Constants.$DS__REGEXP + Constants.REGEXP_WILDCARD))
				{
					date = getUpdatedDate(str, operationParams);
				}
				else {
					date = getDateDs(operationParams);
				}
					
		        return FormatUtils.formatDate(date, formatCode);
			}
			
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return operationParams.getScheduleDate().toString();
			}
			
			protected Date getDateDs(OperationParams operationParams) {
				BigDecimal dateFrom = operationParams.getScheduleDate();
				String dateStr = dateFrom.toString();
				String year = dateStr.substring(0,4);
				String month = dateStr.substring(4,6);
				String day = dateStr.substring(6,8);
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, Integer.parseInt(year));
				calendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
				calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
				calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.AM_PM, Calendar.AM);
				Date date = new Date(calendar.getTimeInMillis());
				return date;
			}
			
			protected Date getUpdatedDate(String wildacardFileName, OperationParams operationParams) {
				Pattern pattern = Pattern.compile(Constants.$DS__REGEXP);
				Matcher matcher = pattern.matcher(wildacardFileName);
				Date finalDate = getDateDs(operationParams);
				if (matcher.find())
				{
				    BigDecimal value = new BigDecimal(matcher.group(1));
				    String sign = matcher.group(2);
				    //Date date =  FormatUtils.date(operationParams.getScheduleDate());
			    	Calendar c = Calendar.getInstance();
			    	c.setTime(finalDate);
				    if(sign.equals(Constants.MINUS)) {
				    	c.add(Calendar.DATE, value.negate().intValue());
				    }
				    else {
				    	c.add(Calendar.DATE, value.intValue());
				    }
				    
				    finalDate = new Date(c.getTimeInMillis());
				}
				return finalDate;
			}
		
		
		},
		TS(Constants.$TS) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception{
				if(Constants.OUTBOUND.equals(executionFlowData.getFlowDirezione())) {
					return FormatUtils.formatTimestamp();
				} else if (Constants.INBOUND.equals(executionFlowData.getFlowDirezione()) 
						&& (executionFlowData.getFlowRemoteFileName().contains(Constants.$TS)
							 || executionFlowData.getFlowFileName().contains(Constants.$TS))) {
					throw new ParameterException("Wildcard " + Constants.$TS + " specified for inbound transaction"); 
				}
				
				return "";
			}
		},
		D6(Constants.$D6) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return FormatUtils.date6(operationParams.getExecutionDate()).toString();
			}
		},
		H2(Constants.$H2) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return StringUtils.leftPad(FormatUtils.actualTimeBigDec2().toString(), '0', 2);
			}
		},
		H4(Constants.$H4) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return StringUtils.leftPad(FormatUtils.actualTimeBigDec4().toString(), '0', 4);
			}
		},
		H6(Constants.$H6) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				return StringUtils.leftPad(FormatUtils.actualTimeBigDec6().toString(), '0', 6);
			}
		},
		FMP(Constants.$FMP) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				Date date =  FormatUtils.date(operationParams.getScheduleDate());
				return FormatUtils.formatEndMonth(date, -1);
			}
		},
		FMS(Constants.$FMS) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				Date date =  FormatUtils.date(operationParams.getScheduleDate());
				return FormatUtils.formatEndMonth(date, +1);
			}
		},
		FMC(Constants.$FMC) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				Date date =  FormatUtils.date(operationParams.getScheduleDate());
				return FormatUtils.formatEndMonth(date, 0);
			}
		},
		IMP(Constants.$IMP) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				Date date =  FormatUtils.date(operationParams.getScheduleDate());
				return FormatUtils.formatStartMonth(date,-1);
			}
		},
		IMS(Constants.$IMS) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				Date date =  FormatUtils.date(operationParams.getScheduleDate());
				return FormatUtils.formatStartMonth(date, 1);
			}
		},
		IMC(Constants.$IMC) {
			@Override
			protected String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
				Date date =  FormatUtils.date(operationParams.getScheduleDate());
				return FormatUtils.formatStartMonth(date, 0);
			}
		};
		
		private String code;
		
		private Replacer(String code) {
			this.code = code;
		}
		
		public String getCode() {
			return this.code;
		}
		
		public String replaceString(String str, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
			return str.replace(this.getCode(), this.getReplaceString(executionFlowData, operationParams));
		}
		
		protected abstract String getReplaceString(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception;
		
		public static String replace(String source, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
			String ret = source;
			
			for (Replacer replacer : Replacer.values()) {
				ret = replacer.replaceString(ret, executionFlowData, operationParams);
			}
			
			return ret;
		}
	}
	
	public void process(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		// ELABORAZIONI COMUNI ALLE DUE DIREZIONI

		if (!YesNo.YES.equals(executionFlowData.getFlowStato())) {
			throw new OperationException("Transaction " + executionFlowData.getFlowId() + " not enabled");
		}

		operationParams.setScheduleDate(scheduleDateRepository.findAll().get(0).getData());
		
		String replaceLocal = replaceFileNamePlaceholder(executionFlowData.getFlowFileName(), executionFlowData, operationParams);
		String replaceRemote = replaceFileNamePlaceholder(executionFlowData.getFlowRemoteFileName(), executionFlowData, operationParams);
		String replaceSemaphore = replaceFileNamePlaceholder(executionFlowData.getFlowFlNameSemaforo(), executionFlowData, operationParams);
		
		if (Constants.INBOUND.contentEquals(executionFlowData.getFlowDirezione()) 
				&& StringUtils.isNullOrEmpty(executionFlowData.getFlowRemoteFileName())
				&& !Constants.THEMA_SPAZIO.equals(executionFlowData.getFlowTipoTrasferimento())) {
			replaceRemote = replaceLocal;
			replaceLocal = "";			
		}
		
		operationParams.setRemoteTrasmissionFiles(Arrays.asList(replaceRemote));
		operationParams.setTrasmissionFiles(Arrays.asList(replaceLocal));
		operationParams.setFileNames(Arrays.asList(replaceLocal));
		operationParams.setSempahoreFile(replaceSemaphore);
		
	}
	
	private String replaceFileNamePlaceholder(String filename, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		return Replacer.replace(filename, executionFlowData, operationParams);
	}

	public void send(ExecutionFlowData executionFlowData) {
		// TODO invio file tramite sftp

		// ELABORAZIONI COMUNI ALLE DUE DIREZIONI
	}

	protected void handleFailure(ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		FlowBuilder inboundFlowBuilderMail = new FlowBuilder();
		operationParams.setOutcome(Constants.KO);
		
		if (YesNo.YES.equals(executionFlowData.getFlowInviaMail())) {
			logger.info("Sending mail for outcome KO");
			inboundFlowBuilderMail.sendOutcomeMail(executionFlowData, operationParams);
			inboundFlowBuilderMail.build().execute();

		}
	}
	
	protected void completeProcess(FlowBuilder flowBuilder, ExecutionFlowData executionFlowData, OperationParams operationParams) throws Exception {
		logger.info("Start complete process" );
		operationParams.setOutcome(Constants.OK);
		
		if (YesNo.YES.equals(executionFlowData.getFlowInviaMail())) {
			logger.info("Sending mail for outcome OK");
			flowBuilder.sendOutcomeMail(executionFlowData, operationParams);
		}
		logger.info("valore campo interattivo: " +  executionFlowData.getFlowInteractiveType());
		switch (executionFlowData.getFlowInteractiveType()) {
		case "P":	
			flowBuilder.interactiveProgram(executionFlowData, operationParams);
			break;
		case "C":
			flowBuilder.interactiveCommand(executionFlowData, operationParams);
			break;
		default:
			break;
		}
		
		
		if (!StringUtils.isNullOrEmpty(executionFlowData.getFlowPgmControllo())) {
			flowBuilder.controlProgram(executionFlowData, operationParams);
		}		
		
		try {
			flowBuilder.build().execute();
		} catch (Exception e) {
			logger.error("Error on complete process " + e.getMessage());
			handleFailure(executionFlowData, operationParams);
			throw e;

		}
	}

}
